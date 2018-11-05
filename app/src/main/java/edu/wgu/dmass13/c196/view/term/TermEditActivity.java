package edu.wgu.dmass13.c196.view.term;

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
import edu.wgu.dmass13.c196.model.entity.Term;
import edu.wgu.dmass13.c196.viewmodel.term.TermEditViewModel;
import edu.wgu.dmass13.c196.viewmodel.term.TermListViewModel;

public class TermEditActivity extends AppCompatActivity {

    public static final String CURRENT_TERM = "edu.wgu.dmass13.c196.CurrentTerm";
    private TermEditViewModel _TermEditViewModel;
    private EditText mEditWordView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);

        _TermEditViewModel = ViewModelProviders.of(this).get(TermEditViewModel.class);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Term term = (bundle == null ? null : (Term) bundle.getSerializable(TermEditActivity.CURRENT_TERM));

        if (term != null) {
            _TermEditViewModel.setTerm(term);
        }


        mEditWordView = findViewById(R.id.edit_word);
        mEditWordView.setText(_TermEditViewModel.getTerm().Title);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveTerm();
            }
        });


    }

    public void SaveTerm() {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditWordView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {

           /* Term newTerm = new Term();
            newTerm.Name = mEditWordView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putSerializable(CURRENT_TERM, (Serializable) newTerm);
            replyIntent.putExtras(bundle);
*/

            _TermEditViewModel.getTerm().Title = mEditWordView.getText().toString();
            _TermEditViewModel.save();

            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
