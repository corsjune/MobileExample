package edu.wgu.dmass13.c196.view.term.components;

import android.content.Context;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.model.entity.Course;
import edu.wgu.dmass13.c196.model.entity.TermCourse;

public class TermCourseListAdapter extends RecyclerView.Adapter<TermCourseListAdapter.CourseViewHolder> {


    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, Course course, Boolean ClickState);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private final LayoutInflater _inflater;
    private List<Course> _course; // Cached copy of words
    private LongSparseArray<Boolean> _selectedCourses;


    public TermCourseListAdapter(Context context) {
        _inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = _inflater.inflate(R.layout.activity_recycler_grid_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if (_course != null) {
            Course current = _course.get(position);

            if (_selectedCourses.get(current.CourseID) != null) {
                holder._courseItemView.setChecked(true);
            } else {
                holder._courseItemView.setChecked(false);
            }

            holder._courseItemView.setText(current.CourseTitle);
        } else {
            // Covers the case of data not being ready yet.
            holder._courseItemView.setText("No Assessment");
        }
    }

    public void setAllCourses(List<Course> course) {
        _course = course;
        notifyDataSetChanged();
    }
/*
    public void setSelectedCourses(List<TermCourse> course) {
        _selectedCourses = new LongSparseArray<TermCourse>();

        for (TermCourse temp: course)
        {
            _selectedCourses.append(temp.CourseID, temp);
        }
    }
    */

    public void setSelectedCourses(LongSparseArray<Boolean> courseCheckState) {
        _selectedCourses = courseCheckState;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (_course != null)
            return _course.size();
        else return 0;
    }


    public class CourseViewHolder extends RecyclerView.ViewHolder implements CheckBox.OnCheckedChangeListener {
        private final CheckBox _courseItemView;

        private CourseViewHolder(final View itemView) {
            super(itemView);
            _courseItemView = itemView.findViewById(R.id.ckGridItem);
            _courseItemView.setOnCheckedChangeListener(null); //not sure if needed, but some examples on SO said to do this
            _courseItemView.setOnCheckedChangeListener(this);
        }

        public Course getItemAtPosition(int position) {
            return _course.get(position);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            // Triggers click upwards to the adapter on click
            if (listener != null) {
                int position = getAdapterPosition();

                Course x = getItemAtPosition(position);

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, x, _courseItemView.isChecked());
                }
            }
        }
    }
}
