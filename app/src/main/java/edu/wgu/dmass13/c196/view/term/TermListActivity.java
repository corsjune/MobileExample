package edu.wgu.dmass13.c196.view.term;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.globals.Enums;
import edu.wgu.dmass13.c196.view.sample.MentorEditActivity;

public class TermListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edu.wgu.dmass13.c196.view.term.TermListActivity.this, TermEditActivity.class);
                startActivityForResult(intent, Enums.ActivityActionTypes.Term_Edit);
            }
        });
    }

}
