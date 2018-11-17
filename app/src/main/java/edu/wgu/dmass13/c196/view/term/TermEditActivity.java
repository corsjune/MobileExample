package edu.wgu.dmass13.c196.view.term;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import edu.wgu.dmass13.c196.view.term.components.MyRecyclerViewAdapter;
import edu.wgu.dmass13.c196.viewmodel.term.TermEditViewModel;
import edu.wgu.dmass13.c196.viewmodel.term.TermListViewModel;

public class TermEditActivity extends BaseActivity implements MyRecyclerViewAdapter.ItemClickListener {

    public static final String CURRENT_TERM = "edu.wgu.dmass13.c196.CurrentTerm";
    private TermEditViewModel _TermEditViewModel;
    private EditText mEditTermTitle;
    private EditText mEditTermStartDate;
    private EditText mEditTermEndDate;
    MyRecyclerViewAdapter adapter;

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

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
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


        // data to populate the RecyclerView with
        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48"};

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
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

