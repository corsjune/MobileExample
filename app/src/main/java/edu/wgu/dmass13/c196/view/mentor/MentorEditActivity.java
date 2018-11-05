package edu.wgu.dmass13.c196.view.mentor;

import android.app.ActionBar;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.model.entity.Mentor;
import edu.wgu.dmass13.c196.viewmodel.mentor.MentorEditViewModel;

public class MentorEditActivity extends AppCompatActivity {

    public static final String CURRENT_MENTOR = "edu.wgu.dmass13.c196.CurrentMentor";
    private MentorEditViewModel _MentorEditViewModel;
    private EditText mEditWordView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_edit);


        // Get a support ActionBar corresponding to this toolbar
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        _MentorEditViewModel = ViewModelProviders.of(this).get(MentorEditViewModel.class);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Mentor mentor = (bundle == null ? null : (Mentor) bundle.getSerializable(MentorEditActivity.CURRENT_MENTOR));

        if (mentor != null) {
            _MentorEditViewModel.setMentor(mentor);
        }


        mEditWordView = findViewById(R.id.edit_word);
        mEditWordView.setText(_MentorEditViewModel.getMentor().Name);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveMentor();
            }
        });


    }

    public void SaveMentor() {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditWordView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {

           /* Mentor newMentor = new Mentor();
            newMentor.Name = mEditWordView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putSerializable(CURRENT_MENTOR, (Serializable) newMentor);
            replyIntent.putExtras(bundle);
*/

            _MentorEditViewModel.getMentor().Name = mEditWordView.getText().toString();
            _MentorEditViewModel.save();

            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
