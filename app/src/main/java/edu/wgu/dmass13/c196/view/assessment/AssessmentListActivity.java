package edu.wgu.dmass13.c196.view.assessment;

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
import edu.wgu.dmass13.c196.model.entity.Assessment;
import edu.wgu.dmass13.c196.view.BaseActivity;
import edu.wgu.dmass13.c196.view.assessment.components.AssessmentListAdapter;
import edu.wgu.dmass13.c196.viewmodel.assessment.AssessmentListViewModel;

public class AssessmentListActivity extends BaseActivity {

    //private AppDatabase database;
    private AssessmentListViewModel _AssessmentListViewModel;

    @Override
    protected String getHelpInfo()
    {
        final String type = "assessment";
        return "Click on a " + type + " to edit. Swipe left or right to delete. Click on the plus button to create a new " + type;
    }

    public static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_recycler_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        final AssessmentListAdapter adapter = new AssessmentListAdapter(this);

        adapter.setOnItemClickListener(new AssessmentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Assessment assessment) {
                EditAssessment(assessment);
            }
        });

        _AssessmentListViewModel = ViewModelProviders.of(this).get(AssessmentListViewModel.class);

        _AssessmentListViewModel.getAllAssessments().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(@Nullable final List<Assessment> assessments) {
                // Update the cached copy of the words in the adapter.
                adapter.setAssessment(assessments);
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
                final Assessment assessment = ((AssessmentListAdapter.AssessmentViewHolder) viewHolder).getItemAtPosition(viewHolder.getAdapterPosition());
                final String describer = assessment.Name;

                new AlertDialog.Builder(AssessmentListActivity.this)
                        .setTitle("Confirm Delete")
                        .setMessage("Do you really want to delete " + describer + " ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteAssessment(assessment);
                                Toast.makeText(AssessmentListActivity.this, describer + " was deleted!", Toast.LENGTH_SHORT).show();
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
                EditAssessment(null);
            }
        });
    }

    public void EditAssessment(Assessment assessment) {
        Intent intent = new Intent(AssessmentListActivity.this, AssessmentEditActivity.class);

        if (assessment != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(AssessmentEditActivity.CURRENT_ASSESSMENT, (Serializable) assessment);
            intent.putExtras(bundle);
        }

        startActivityForResult(intent, Enums.ActivityActionTypes.Assessment_Edit);
    }

    public void deleteAssessment(Assessment assessment) {
        _AssessmentListViewModel.deleteAssessment(assessment.AssessmentID);
    }


}
