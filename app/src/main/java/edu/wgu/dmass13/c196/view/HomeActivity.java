package edu.wgu.dmass13.c196.view;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
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


        // Get a support ActionBar corresponding to this toolbar
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
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
