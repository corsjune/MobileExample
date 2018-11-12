package edu.wgu.dmass13.c196.view.course;

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
import edu.wgu.dmass13.c196.model.entity.Course;
import edu.wgu.dmass13.c196.view.BaseActivity;
import edu.wgu.dmass13.c196.viewmodel.course.CourseEditViewModel;
import edu.wgu.dmass13.c196.viewmodel.course.CourseListViewModel;

public class CourseEditActivity extends BaseActivity {

    public static final String CURRENT_COURSE = "edu.wgu.dmass13.c196.CurrentCourse";
    private CourseEditViewModel _CourseEditViewModel;
    private EditText mEditWordView;

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


        mEditWordView = findViewById(R.id.edit_word);
        mEditWordView.setText(_CourseEditViewModel.getCourse().CourseTitle);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveCourse();
            }
        });


    }

    public void SaveCourse() {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditWordView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {

           /* Course newCourse = new Course();
            newCourse.Name = mEditWordView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putSerializable(CURRENT_COURSE, (Serializable) newCourse);
            replyIntent.putExtras(bundle);
*/

            _CourseEditViewModel.getCourse().CourseTitle = mEditWordView.getText().toString();
            _CourseEditViewModel.save();

            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
