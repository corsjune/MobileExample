package edu.wgu.dmass13.c196.view.assessment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.globals.Enums;
import edu.wgu.dmass13.c196.globals.Helpers;
import edu.wgu.dmass13.c196.model.entity.Assessment;
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
    protected String getHelpInfo()
    {
        final String type = "assessment";
        return "Complete the fields for this " + type + " and click save. Check any alerts that you desire to be created for this assessment." ;
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
        _assessmentName.setText(assessment.Name);

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


            if (currentAssessment.GoalAlert) {
                String Message = "You had a goal set for Assessment " + currentAssessment.Name + " at " + Helpers.ConvertDateToString(currentAssessment.GoalDate);
                CreateNotification(Message, currentAssessment.GoalDate);
            }
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }


}
