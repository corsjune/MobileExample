package edu.wgu.dmass13.c196.view.assessment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.model.entity.Assessment;
import edu.wgu.dmass13.c196.viewmodel.assessment.AssessmentEditViewModel;

public class AssessmentEditActivity extends AppCompatActivity {

    public static final String CURRENT_ASSESSMENT = "edu.wgu.dmass13.c196.CurrentAssessment";
    private AssessmentEditViewModel _AssessmentEditViewModel;
    private EditText mEditWordView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);

        _AssessmentEditViewModel = ViewModelProviders.of(this).get(AssessmentEditViewModel.class);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Assessment assessment = (bundle == null ? null : (Assessment) bundle.getSerializable(AssessmentEditActivity.CURRENT_ASSESSMENT));

        if (assessment != null) {
            _AssessmentEditViewModel.setAssessment(assessment);
        }


        mEditWordView = findViewById(R.id.edit_word);
        mEditWordView.setText(_AssessmentEditViewModel.getAssessment().Name);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveAssessment();
            }
        });


    }

    public void SaveAssessment() {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditWordView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {

           /* Assessment newAssessment = new Assessment();
            newAssessment.Name = mEditWordView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putSerializable(CURRENT_ASSESSMENT, (Serializable) newAssessment);
            replyIntent.putExtras(bundle);
*/

            _AssessmentEditViewModel.getAssessment().Name = mEditWordView.getText().toString();
            _AssessmentEditViewModel.save();

            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
