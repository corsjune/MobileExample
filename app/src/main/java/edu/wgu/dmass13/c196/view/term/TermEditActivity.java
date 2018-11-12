package edu.wgu.dmass13.c196.view.term;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.globals.Helpers;
import edu.wgu.dmass13.c196.model.entity.Term;
import edu.wgu.dmass13.c196.view.BaseActivity;
import edu.wgu.dmass13.c196.viewmodel.term.TermEditViewModel;
import edu.wgu.dmass13.c196.viewmodel.term.TermListViewModel;

public class TermEditActivity extends BaseActivity {

    public static final String CURRENT_TERM = "edu.wgu.dmass13.c196.CurrentTerm";
    private TermEditViewModel _TermEditViewModel;
    private EditText mEditTermTitle;
    private EditText mEditTermStartDate;
    private EditText mEditTermEndDate;

    @Override
    protected int getContentView() {
        return R.layout.activity_term_edit;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _TermEditViewModel = ViewModelProviders.of(this).get(TermEditViewModel.class);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //get any passed in term from the intent
        Term term = (bundle == null ? null : (Term) bundle.getSerializable(TermEditActivity.CURRENT_TERM));
        //if term was passed in , set the viewmodel
        if (term != null) {
            _TermEditViewModel.setTerm(term);
        }

        PopulateUI();

    }

    public void PopulateUI() {

        //https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext

        Term term = _TermEditViewModel.getTerm();

        mEditTermTitle = findViewById(R.id.term_edit_title);
        mEditTermTitle.setText(term.Title != null ? term.Title : null);

        mEditTermStartDate = findViewById(R.id.term_edit_StartDate);
        mEditTermStartDate.setText(Helpers.ConvertDateToString(term.StartDate));
        mEditTermStartDate.setOnClickListener(new myDatePicker(mEditTermStartDate, this));

        mEditTermEndDate = findViewById(R.id.term_edit_EndDate);
        mEditTermEndDate.setText(Helpers.ConvertDateToString(term.EndDate));
        mEditTermEndDate.setOnClickListener(new myDatePicker(mEditTermEndDate, this));

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveTerm();
            }
        });
    }

    public void SaveTerm() {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditTermTitle.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {

           /* Term newTerm = new Term();
            newTerm.Name = mEditWordView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putSerializable(CURRENT_TERM, (Serializable) newTerm);
            replyIntent.putExtras(bundle);
*/

            _TermEditViewModel.getTerm().Title = mEditTermTitle.getText().toString();
            _TermEditViewModel.getTerm().StartDate = GetDateValueFromEditText(mEditTermStartDate);
            _TermEditViewModel.getTerm().EndDate = GetDateValueFromEditText(mEditTermEndDate);
            _TermEditViewModel.save();

            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }


}

