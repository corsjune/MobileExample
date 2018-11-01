package edu.wgu.dmass13.c196.view.course;

import android.os.Bundle;
import android.app.Activity;

import edu.wgu.dmass13.c196.R;

public class CourseListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
      //  getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
