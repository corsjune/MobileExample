package edu.wgu.dmass13.c196.view.mentor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.model.entity.Mentor;

public class MentorEditActivity extends AppCompatActivity {

    public static final String CURRENT_MENTOR = "edu.wgu.dmass13.c196.CurrentMentor";
    private EditText mEditWordView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mentor);
        mEditWordView = findViewById(R.id.edit_word);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveMentor();
            }
        });


    }

    public void SaveMentor()
    {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditWordView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {

            Mentor newMentor = new Mentor();
            newMentor.Name = mEditWordView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putSerializable(CURRENT_MENTOR, (Serializable) newMentor);
            replyIntent.putExtras(bundle);

            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
