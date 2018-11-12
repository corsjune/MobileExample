package edu.wgu.dmass13.c196.view.course;

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
import edu.wgu.dmass13.c196.model.entity.Course;
import edu.wgu.dmass13.c196.view.BaseActivity;
import edu.wgu.dmass13.c196.view.course.components.CourseListAdapter;
import edu.wgu.dmass13.c196.viewmodel.course.CourseListViewModel;

public class CourseListActivity extends BaseActivity {

    //private AppDatabase database;
    private CourseListViewModel _CourseListViewModel;

    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        final CourseListAdapter adapter = new CourseListAdapter(this);

        adapter.setOnItemClickListener(new CourseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Course course) {
                EditCourse(course);
            }
        });

        _CourseListViewModel = ViewModelProviders.of(this).get(CourseListViewModel.class);

        _CourseListViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable final List<Course> courses) {
                // Update the cached copy of the words in the adapter.
                adapter.setCourse(courses);
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
                final Course course = ((CourseListAdapter.CourseViewHolder) viewHolder).getItemAtPosition(viewHolder.getAdapterPosition());
                final String describer = course.CourseTitle;

                new AlertDialog.Builder(CourseListActivity.this)
                        .setTitle("Confirm Delete")
                        .setMessage("Do you really want to delete " + describer + " ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteCourse(course);
                                Toast.makeText(CourseListActivity.this, describer + " was deleted!", Toast.LENGTH_SHORT).show();
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
                EditCourse(null);
            }
        });
    }

    public void EditCourse(Course course) {
        Intent intent = new Intent(CourseListActivity.this, CourseEditActivity.class);

        if (course != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(CourseEditActivity.CURRENT_COURSE, (Serializable) course);
            intent.putExtras(bundle);
        }

        startActivityForResult(intent, Enums.ActivityActionTypes.Course_Edit);
    }

    public void deleteCourse(Course course) {
        _CourseListViewModel.deleteCourse(course.CourseID);
    }


}
