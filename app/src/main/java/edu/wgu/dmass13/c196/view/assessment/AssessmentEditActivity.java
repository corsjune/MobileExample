package edu.wgu.dmass13.c196.view.assessment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.Serializable;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.globals.Enums;
import edu.wgu.dmass13.c196.globals.Helpers;
import edu.wgu.dmass13.c196.model.entity.Assessment;
import edu.wgu.dmass13.c196.model.entity.Term;
import edu.wgu.dmass13.c196.notification.C196Receiver;
import edu.wgu.dmass13.c196.view.BaseActivity;
import edu.wgu.dmass13.c196.viewmodel.assessment.AssessmentEditViewModel;

public class AssessmentEditActivity extends BaseActivity {

    public static final String CURRENT_ASSESSMENT = "edu.wgu.dmass13.c196.CurrentAssessment";
    private AssessmentEditViewModel _AssessmentEditViewModel;
    private EditText _assessmentName;
    private EditText _assessmentDate;
    private EditText _goalDate;
    private RadioGroup _assessmentType;
    private CheckBox _goalDateAlert;

    /*
    assessment_edit_type
    assessment_edit_name
            assessment_edit_date
    rdAssessment
            rdPerformance
       */

    @Override
    protected int getContentView() {
        return R.layout.activity_assessment_edit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _AssessmentEditViewModel = ViewModelProviders.of(this).get(AssessmentEditViewModel.class);

        if (_AssessmentEditViewModel.getAssessment() == null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            Assessment assessment = (bundle == null ? null : (Assessment) bundle.getSerializable(AssessmentEditActivity.CURRENT_ASSESSMENT));

            if (assessment != null) {
                _AssessmentEditViewModel.setAssessment(assessment);
            } else {
                _AssessmentEditViewModel.setAssessment(new Assessment());
            }
        }

        PopulateUI();
    }


    public void PopulateUI() {

        //https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext

        Assessment assessment = _AssessmentEditViewModel.getAssessment();


        _assessmentName = findViewById(R.id.assessment_edit_name);
        _assessmentName.setText(assessment.Name != null ? assessment.Name : null);

        _assessmentDate = findViewById(R.id.assessment_edit_date);
        _assessmentDate.setText(Helpers.ConvertDateToString(assessment.AssessmentDate));
        _assessmentDate.setOnClickListener(new myDatePicker(_assessmentDate, this));

        _goalDate = findViewById(R.id.assessment_goal_date);
        _goalDate.setText(Helpers.ConvertDateToString(assessment.GoalDate));
        _goalDate.setOnClickListener(new myDatePicker(_goalDate, this));

        _goalDateAlert = findViewById(R.id.ckGoalAlert);
        _goalDateAlert.setChecked(assessment.GoalAlert);


        _assessmentType = findViewById(R.id.assessment_edit_type);

        //todo refactor to a switch
        if (assessment.AssessmentType == Enums.AssessmentType.Objective) {
            _assessmentType.check(R.id.rdObjective);
        }

        if (assessment.AssessmentType == Enums.AssessmentType.Performance) {
            _assessmentType.check(R.id.rdPerformance);
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveAssessment();
            }
        });
    }

    public void CreateNotification(Assessment x) {
        String message = "You had a goal set for Assessment " + x.Name + " at " + Helpers.ConvertDateToString(x.GoalDate);

        Intent intent = new Intent(AssessmentEditActivity.this, C196Receiver.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(C196Receiver.C196RECEIVER_INTENT, (Serializable) message);
        intent.putExtras(bundle);

        PendingIntent sender = PendingIntent.getBroadcast(AssessmentEditActivity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, sender);

    }

    public void SaveAssessment() {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(_assessmentName.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {

           /* Term newTerm = new Term();
            newTerm.Name = mEditWordView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putSerializable(CURRENT_TERM, (Serializable) newTerm);
            replyIntent.putExtras(bundle);
*/

            Assessment currentAssessment = _AssessmentEditViewModel.getAssessment();
            currentAssessment.Name = GetStringValueFromEditText(_assessmentName);
            currentAssessment.AssessmentDate = GetDateValueFromEditText(_assessmentDate);
            currentAssessment.GoalDate = GetDateValueFromEditText(_goalDate);
            currentAssessment.GoalAlert = _goalDateAlert.isChecked();


            int rb = _assessmentType.getCheckedRadioButtonId();

            if (rb == R.id.rdObjective) {
                currentAssessment.AssessmentType = Enums.AssessmentType.Objective;
            }

            if (rb == R.id.rdPerformance) {
                currentAssessment.AssessmentType = Enums.AssessmentType.Performance;
            }

            //_AssessmentEditViewModel.getAssessment().EndDate = GetDateValueFromEditText(mEditTermEndDate);
            _AssessmentEditViewModel.save();
            CreateNotification(currentAssessment);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }

    private void dpAction() {
        /*
        LocalDate localDate = dp.getValue();
        Date date=java.sql.Date.valueOf(localDate);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        Long mill=calendar.getTimeInMillis();
        millis.setText(mill.toString());
        */
    }

}
