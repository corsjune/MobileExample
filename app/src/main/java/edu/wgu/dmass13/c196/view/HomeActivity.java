package edu.wgu.dmass13.c196.view;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.globals.Enums;
import edu.wgu.dmass13.c196.view.assessment.AssessmentListActivity;
import edu.wgu.dmass13.c196.view.course.CourseListActivity;
import edu.wgu.dmass13.c196.view.mentor.MentorListActivity;
import edu.wgu.dmass13.c196.view.term.TermListActivity;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
