package edu.wgu.dmass13.c196.view.course;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.io.Serializable;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.globals.Enums;
import edu.wgu.dmass13.c196.globals.Helpers;
import edu.wgu.dmass13.c196.model.entity.Assessment;
import edu.wgu.dmass13.c196.model.entity.Course;
import edu.wgu.dmass13.c196.view.BaseActivity;
import edu.wgu.dmass13.c196.viewmodel.course.CourseEditViewModel;
import edu.wgu.dmass13.c196.viewmodel.course.CourseListViewModel;

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


    @Override
    protected int getContentView() {
        return R.layout.activity_course_edit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _CourseEditViewModel = ViewModelProviders.of(this).get(CourseEditViewModel.class);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Course course = (bundle == null ? null : (Course) bundle.getSerializable(CourseEditActivity.CURRENT_COURSE));

        if (course != null) {
            _CourseEditViewModel.setCourse(course);
        }

        PopulateUI();

    }


    public void PopulateUI() {

        Course course = _CourseEditViewModel.getCourse();

        _title = findViewById(R.id.course_edit_title);
        _title.setText(course.CourseTitle != null ? course.CourseTitle : null);

        _status = findViewById(R.id.course_edit_status);
        _status.setText(course.Status != null ? course.Status : null);

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
        _notes.setText(course.Notes != null ? course.Notes : null);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveCourse();
            }
        });
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

            _CourseEditViewModel.save();

            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }

}
