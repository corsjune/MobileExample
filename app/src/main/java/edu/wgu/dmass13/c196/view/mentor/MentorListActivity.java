package edu.wgu.dmass13.c196.view.mentor;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.globals.Enums;
import edu.wgu.dmass13.c196.model.database.AppDatabase;
import edu.wgu.dmass13.c196.model.entity.Mentor;
import edu.wgu.dmass13.c196.view.mentor.components.MentorListAdapter;
import edu.wgu.dmass13.c196.viewmodel.MentorListViewModel;

public class MentorListActivity extends AppCompatActivity {

    private AppDatabase database;
    private MentorListViewModel _MentorListViewModel;

    public static final int NEW_MENTOR_ACTIVITY_REQUEST_CODE = 1;

    private void confirmDialogDemo() {

        //http://www.apnatutorials.com/android/android-alert-confirm-prompt-dialog.php?categoryId=2&subCategoryId=34&myPath=android/android-alert-confirm-prompt-dialog.php

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm dialog demo !");
        builder.setMessage("You are about to this record. Do you really want to proceed ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You've choosen to delete all records", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getDatabase(getApplicationContext());

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        final MentorListAdapter adapter = new MentorListAdapter(this);

        adapter.setOnItemClickListener(new MentorListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Mentor mentor) {
                EditMentor(mentor);
            }
        });

        _MentorListViewModel = ViewModelProviders.of(this).get(MentorListViewModel.class);

        _MentorListViewModel.getAllMentors().observe(this, new Observer<List<Mentor>>() {
            @Override
            public void onChanged(@Nullable final List<Mentor> mentors) {
                // Update the cached copy of the words in the adapter.
                adapter.setMentor(mentors);
            }

        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int i = viewHolder.getAdapterPosition();
                confirmDialogDemo();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditMentor(null);
            }
        });
    }

    public void EditMentor(Mentor mentor) {
        Intent intent = new Intent(MentorListActivity.this, MentorEditActivity.class);

        if (mentor != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(MentorEditActivity.CURRENT_MENTOR, (Serializable) mentor);
            intent.putExtras(bundle);
        }

        startActivityForResult(intent, Enums.ActivityActionTypes.Mentor_Edit);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_MENTOR_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Bundle bundle = data.getExtras();
            Mentor mentor = (Mentor) bundle.getSerializable(MentorEditActivity.CURRENT_MENTOR);

            _MentorListViewModel.insert(mentor);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
