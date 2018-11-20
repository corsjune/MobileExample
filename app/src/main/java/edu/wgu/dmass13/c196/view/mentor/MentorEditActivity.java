package edu.wgu.dmass13.c196.view.mentor;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.model.entity.Mentor;
import edu.wgu.dmass13.c196.view.BaseActivity;
import edu.wgu.dmass13.c196.viewmodel.mentor.MentorEditViewModel;

public class MentorEditActivity extends BaseActivity {

    public static final String CURRENT_MENTOR = "edu.wgu.dmass13.c196.CurrentMentor";
    private MentorEditViewModel _MentorEditViewModel;
    private EditText _mentorName;
    private EditText _mentorPhone;
    private EditText _mentorEmail;

    @Override
    protected String getHelpInfo()
    {
        final String type = "mentor";
        return "Complete the fields for this " + type + " and click save. ";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mentor_edit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        _MentorEditViewModel = ViewModelProviders.of(this).get(MentorEditViewModel.class);

        if (_MentorEditViewModel.getMentor() == null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            Mentor mentor = (bundle == null ? null : (Mentor) bundle.getSerializable(MentorEditActivity.CURRENT_MENTOR));

            if (mentor != null) {
                _MentorEditViewModel.setMentor(mentor);
            } else {
                _MentorEditViewModel.setMentor(new Mentor());
            }
        }

        PopulateUI();
    }


    public void PopulateUI() {

        Mentor mentor = _MentorEditViewModel.getMentor();

        _mentorEmail = findViewById(R.id.mentor_edit_email);
        _mentorName = findViewById(R.id.mentor_edit_name);
        _mentorPhone = findViewById(R.id.mentor_edit_phone);

        _mentorEmail.setText(mentor.Email);
        _mentorName.setText(mentor.Name);
        _mentorPhone.setText(mentor.PhoneNumber);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveMentor();
            }
        });
    }

    public void SaveMentor() {
        Intent replyIntent = new Intent();


        if (TextUtils.isEmpty(_mentorName.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {

           /* Term newTerm = new Term();
            newTerm.Name = mEditWordView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putSerializable(CURRENT_TERM, (Serializable) newTerm);
            replyIntent.putExtras(bundle);
*/

            Mentor mentor = _MentorEditViewModel.getMentor();

            mentor.Email = GetStringValueFromEditText(_mentorEmail);
            mentor.Name = GetStringValueFromEditText(_mentorName);
            mentor.PhoneNumber = GetStringValueFromEditText(_mentorPhone);

            _MentorEditViewModel.save();

            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }


}
