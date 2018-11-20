package edu.wgu.dmass13.c196.view.mentor;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.globals.Enums;
import edu.wgu.dmass13.c196.model.entity.Mentor;
import edu.wgu.dmass13.c196.view.BaseActivity;
import edu.wgu.dmass13.c196.view.mentor.components.MentorListAdapter;
import edu.wgu.dmass13.c196.viewmodel.mentor.MentorListViewModel;

public class MentorListActivity extends BaseActivity {

    //private AppDatabase database;
    private MentorListViewModel _MentorListViewModel;

    public static final int NEW_MENTOR_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected String getHelpInfo()
    {
        final String type = "mentor";
        return "Click on a " + type + " to edit. Swipe left or right to delete. Click on the plus button to create a new " + type;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recycler_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
                final Mentor mentor = ((MentorListAdapter.MentorViewHolder) viewHolder).getItemAtPosition(viewHolder.getAdapterPosition());
                final String describer = mentor.Name;

                new AlertDialog.Builder(MentorListActivity.this)
                        .setTitle("Confirm Delete")
                        .setMessage("Do you really want to delete " + describer + " ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteMentor(mentor);
                                Toast.makeText(MentorListActivity.this, describer + " was deleted!", Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();
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

    public void deleteMentor(Mentor mentor) {
        _MentorListViewModel.deleteMentor(mentor.MentorID);
    }


}
