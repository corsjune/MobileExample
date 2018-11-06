package edu.wgu.dmass13.c196.view;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toolbar;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.globals.Enums;
import edu.wgu.dmass13.c196.view.assessment.AssessmentListActivity;
import edu.wgu.dmass13.c196.view.course.CourseListActivity;
import edu.wgu.dmass13.c196.view.mentor.MentorListActivity;
import edu.wgu.dmass13.c196.view.term.TermListActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the toolbar view inside the activity layout
        android.support.v7.widget.Toolbar toolbar = ( android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
         android.support.v7.app.ActionBar ab = getSupportActionBar();
        // Enable the Up button
          ab.setDisplayHomeAsUpEnabled(true);
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void cmdMentors_OnClick(View view) {
        Intent intent = new Intent(HomeActivity.this, MentorListActivity.class);
        startActivityForResult(intent, Enums.ActivityActionTypes.Mentor_List);
    }

    public void cmdAssessment_OnClick(View view) {
        Intent intent = new Intent(HomeActivity.this, AssessmentListActivity.class);
        startActivityForResult(intent, Enums.ActivityActionTypes.Assessment_List);
    }

    public void cmdCourse_OnClick(View view) {
        Intent intent = new Intent(HomeActivity.this, CourseListActivity.class);
        startActivityForResult(intent, Enums.ActivityActionTypes.Course_List);
    }

    public void cmdTerm_OnClick(View view) {
        Intent intent = new Intent(HomeActivity.this, TermListActivity.class);
        startActivityForResult(intent, Enums.ActivityActionTypes.Term_List);
    }
}
