package edu.wgu.dmass13.c196.view.term;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.util.LongSparseArray;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.globals.Helpers;
import edu.wgu.dmass13.c196.model.entity.Course;
import edu.wgu.dmass13.c196.model.entity.Term;
import edu.wgu.dmass13.c196.model.entity.TermCourse;
import edu.wgu.dmass13.c196.view.BaseActivity;
import edu.wgu.dmass13.c196.view.term.components.TermCourseListAdapter;
import edu.wgu.dmass13.c196.viewmodel.term.TermEditViewModel;

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
    protected String getHelpInfo()
    {
        final String type = "term";
        return "Complete the fields for this " + type + " and click save. Click on any relevant courses that should be associated with this " + type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _TermEditViewModel = ViewModelProviders.of(this).get(TermEditViewModel.class);

        if (_TermEditViewModel.getTerm() == null) {

            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            //get any passed in term from the intent
            Term term = (bundle == null ? null : (Term) bundle.getSerializable(TermEditActivity.CURRENT_TERM));
            //if term was passed in , set the viewmodel
            if (term != null) {
                _TermEditViewModel.setTerm(term);
            } else {
                _TermEditViewModel.setTerm(new Term());
            }

        }

        PopulateUI();

    }


    public void PopulateUI() {

        //https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext

        Term term = _TermEditViewModel.getTerm();

        mEditTermTitle = findViewById(R.id.term_edit_title);
        mEditTermTitle.setText(term.Title);

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



        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        final TermCourseListAdapter adapter = new TermCourseListAdapter(this);
        adapter.setOnItemClickListener(new TermCourseListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, Course course, Boolean ClickState) {

                LongSparseArray<Boolean> lsa = _TermEditViewModel.getCourseCheckState();
                lsa.put(course.CourseID, ClickState);
            }
        });


        int numberOfColumns = 2;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        adapter.setSelectedCourses(_TermEditViewModel.getCourseCheckState());

        _TermEditViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable final List<Course> courses) {
                // Update the cached copy of the words in the adapter.
                adapter.setAllCourses(courses);

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
            Term term = _TermEditViewModel.getTerm();
            LongSparseArray<Boolean> lsa = _TermEditViewModel.getCourseCheckState();

            term.Title = mEditTermTitle.getText().toString();
            term.StartDate = GetDateValueFromEditText(mEditTermStartDate);
            term.EndDate = GetDateValueFromEditText(mEditTermEndDate);

            term.SelectedCourses = new ArrayList<TermCourse>();


            for (int i = 0; i < lsa.size(); i++) {

                long key = lsa.keyAt(i);

                if (lsa.get(key, false)) {
                    TermCourse newTC = new TermCourse();
                    newTC.CourseID = lsa.keyAt(i);
                    newTC.TermID = term.TermID;
                    term.SelectedCourses.add(newTC);
                }
            }

            _TermEditViewModel.save();

            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }


}

