package edu.wgu.dmass13.c196.view.term;

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
import edu.wgu.dmass13.c196.model.entity.Term;
import edu.wgu.dmass13.c196.view.BaseActivity;
import edu.wgu.dmass13.c196.view.term.components.TermListAdapter;
import edu.wgu.dmass13.c196.viewmodel.term.TermListViewModel;

public class TermListActivity extends BaseActivity {

    //private AppDatabase database;
    private TermListViewModel _TermListViewModel;

    public static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_recycler_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TermListAdapter adapter = new TermListAdapter(this);

        adapter.setOnItemClickListener(new TermListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Term term) {
                EditTerm(term);
            }
        });

        _TermListViewModel = ViewModelProviders.of(this).get(TermListViewModel.class);

        _TermListViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable final List<Term> terms) {
                // Update the cached copy of the words in the adapter.
                adapter.setTerm(terms);
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
                final Term term = ((TermListAdapter.TermViewHolder) viewHolder).getItemAtPosition(viewHolder.getAdapterPosition());
                final String describer = term.Title;

                new AlertDialog.Builder(TermListActivity.this)
                        .setTitle("Confirm Delete")
                        .setMessage("Do you really want to delete " + describer + " ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteTerm(term);
                                Toast.makeText(TermListActivity.this, describer + " was deleted!", Toast.LENGTH_SHORT).show();
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
                EditTerm(null);
            }
        });
    }

    public void EditTerm(Term term) {
        Intent intent = new Intent(TermListActivity.this, TermEditActivity.class);

        if (term != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(TermEditActivity.CURRENT_TERM, (Serializable) term);
            intent.putExtras(bundle);
        }

        startActivityForResult(intent, Enums.ActivityActionTypes.Term_Edit);
    }

    public void deleteTerm(Term term) {
        _TermListViewModel.deleteTerm(term.TermID);
    }


}
