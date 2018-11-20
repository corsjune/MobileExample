package edu.wgu.dmass13.c196.view.course;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.globals.Enums;
import edu.wgu.dmass13.c196.globals.Helpers;
import edu.wgu.dmass13.c196.model.entity.Assessment;
import edu.wgu.dmass13.c196.model.entity.Course;
import edu.wgu.dmass13.c196.model.entity.CourseAssessment;
import edu.wgu.dmass13.c196.model.entity.CourseMentor;
import edu.wgu.dmass13.c196.model.entity.Mentor;
import edu.wgu.dmass13.c196.view.BaseActivity;
import edu.wgu.dmass13.c196.view.course.components.CourseAssessmentListAdapter;
import edu.wgu.dmass13.c196.view.course.components.CourseMentorListAdapter;
import edu.wgu.dmass13.c196.viewmodel.course.CourseEditViewModel;

public class CourseEditActivity extends BaseActivity {

    public static final String CURRENT_COURSE = "edu.wgu.dmass13.c196.CurrentCourse";
    private CourseEditViewModel _CourseEditViewModel;
    private EditText _title;
    private EditText _status;
    private EditText _startDate;
    private CheckBox _startDateAlert;
    private EditText _endDate;
    private CheckBox _endDateAlert;
    private EditText _notes;


    // private static final int MENU_LOGOUT = MENU.FIRST + 4;

    @Override
    protected String getHelpInfo()
    {
        final String type = "course";
        return "Complete the fields for this " + type + " and click save. Check any alerts that you desire to be created for this course. Click the share button in the toolbar to share notes. Click on any relevant mentors or assessments that should be associated with this " + type;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_course_edit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _CourseEditViewModel = ViewModelProviders.of(this).get(CourseEditViewModel.class);

        if (_CourseEditViewModel.getCourse() == null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            Course course = (bundle == null ? null : (Course) bundle.getSerializable(CourseEditActivity.CURRENT_COURSE));

            if (course != null) {
                _CourseEditViewModel.setCourse(course);
            } else {
                _CourseEditViewModel.setCourse(new Course());
            }
        }
        PopulateUI();

    }


    public void PopulateUI() {

        Course course = _CourseEditViewModel.getCourse();

        _title = findViewById(R.id.course_edit_title);
        _title.setText(course.CourseTitle);

        _status = findViewById(R.id.course_edit_status);
        _status.setText(course.Status);

        _startDate = findViewById(R.id.course_edit_startdate);
        _startDate.setText(Helpers.ConvertDateToString(course.StartDate));
        _startDate.setOnClickListener(new myDatePicker(_startDate, this));

        _startDateAlert = findViewById(R.id.course_edit_startdatealert);
        _startDateAlert.setChecked(course.StartDateAlert);

        _endDate = findViewById(R.id.course_edit_enddate);
        _endDate.setText(Helpers.ConvertDateToString(course.EndDate));
        _endDate.setOnClickListener(new myDatePicker(_endDate, this));

        _endDateAlert = findViewById(R.id.course_edit_enddatealert);
        _endDateAlert.setChecked(course.EndDateAlert);

        _notes = findViewById(R.id.course_edit_notes);
        _notes.setText(course.Notes);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveCourse();
            }
        });

        SetupCourseMentorRecycleView();
        SetupCourseAssessmentRecycleView();
    }

    public void SetupCourseMentorRecycleView() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvCourseMentors);
        final CourseMentorListAdapter adapter = new CourseMentorListAdapter(this);
        adapter.setOnItemClickListener(new CourseMentorListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, Mentor mentor, Boolean ClickState) {
                LongSparseArray<Boolean> lsa = _CourseEditViewModel.getMentorCheckState();
                lsa.put(mentor.MentorID, ClickState);
            }
        });


        int numberOfColumns = 2;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        adapter.setSelectedMentors(_CourseEditViewModel.getMentorCheckState());

        _CourseEditViewModel.getAllMentors().observe(this, new Observer<List<Mentor>>() {
            @Override
            public void onChanged(@Nullable final List<Mentor> mentors) {
                // Update the cached copy of the words in the adapter.
                adapter.setAllMentors(mentors);

            }
        });
    }

    public void SetupCourseAssessmentRecycleView() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvCourseAssessments);
        final CourseAssessmentListAdapter adapter = new CourseAssessmentListAdapter(this);
        adapter.setOnItemClickListener(new CourseAssessmentListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, Assessment assessment, Boolean ClickState) {
                LongSparseArray<Boolean> lsa = _CourseEditViewModel.getAssessmentCheckState();
                lsa.put(assessment.AssessmentID, ClickState);
            }
        });


        int numberOfColumns = 2;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        adapter.setSelectedAssessments(_CourseEditViewModel.getAssessmentCheckState());

        _CourseEditViewModel.getAllAssessments().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(@Nullable final List<Assessment> assessments) {
                // Update the cached copy of the words in the adapter.
                adapter.setAllAssessments(assessments);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case Enums.MenuValues.SHARE:

                String noteValue = GetStringValueFromEditText(_notes);

                if (noteValue == null || noteValue.isEmpty()) {
                    new AlertDialog.Builder(CourseEditActivity.this)
                            .setTitle("WGU 196")
                            .setMessage("You do not have any notes to send for this course!")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            })
                            .show();
                } else {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, noteValue);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                break;
        }
        return false;
    }


    @Override
    public void AddMenuItems(Menu menu) {
        menu.add(0, Enums.MenuValues.INFO, Menu.NONE, getString(R.string.menu_action_info)).setIcon(android.R.drawable.ic_dialog_info).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, Enums.MenuValues.SHARE, Menu.NONE, getString(R.string.menu_action_share)).setIcon(android.R.drawable.ic_menu_share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    public void SaveCourse() {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(_title.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            Course course = _CourseEditViewModel.getCourse();

            course.CourseTitle = GetStringValueFromEditText(_title);
            course.Status = GetStringValueFromEditText(_status);
            course.StartDate = GetDateValueFromEditText(_startDate);
            course.StartDateAlert = _startDateAlert.isChecked();
            course.EndDate = GetDateValueFromEditText(_endDate);
            course.EndDateAlert = _endDateAlert.isChecked();
            course.Notes = GetStringValueFromEditText(_notes);

            course.assignedAssignments = new ArrayList<CourseAssessment>();
            LongSparseArray<Boolean> lsaAssignments = _CourseEditViewModel.getAssessmentCheckState();

            for (int i = 0; i < lsaAssignments.size(); i++) {

                long key = lsaAssignments.keyAt(i);

                if (lsaAssignments.get(key, false)) {
                    CourseAssessment newAs = new CourseAssessment();
                    newAs.AssessmentID = lsaAssignments.keyAt(i);
                    newAs.CourseID = course.CourseID;
                    course.assignedAssignments.add(newAs);
                }
            }

            course.assignedMentors = new ArrayList<CourseMentor>();
            LongSparseArray<Boolean> lsaMentors = _CourseEditViewModel.getMentorCheckState();

            for (int i = 0; i < lsaMentors.size(); i++) {

                long key = lsaMentors.keyAt(i);

                if (lsaMentors.get(key, false)) {
                    CourseMentor newMent = new CourseMentor();
                    newMent.MentorID = lsaMentors.keyAt(i);
                    newMent.CourseID = course.CourseID;
                    course.assignedMentors.add(newMent);
                }
            }

            _CourseEditViewModel.save();

            if (course.StartDateAlert) {
                String Message = "You had a goal set for the course start date of  " + course.CourseTitle + " at " + Helpers.ConvertDateToString(course.StartDate);
                CreateNotification(Message, course.StartDate);
            }

            if (course.EndDateAlert) {
                String Message = "You had a goal set for the course end date of " + course.CourseTitle + " at " + Helpers.ConvertDateToString(course.EndDate);
                CreateNotification(Message, course.EndDate);
            }


            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }

}
