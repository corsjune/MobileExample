package edu.wgu.dmass13.c196.view.assessment;

import android.os.Bundle;
import android.app.Activity;

import edu.wgu.dmass13.c196.R;

public class Assessment_Edit extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment__edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
