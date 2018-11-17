package edu.wgu.dmass13.c196.view.course.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.model.entity.Course;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {


    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, Course assessment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private final LayoutInflater _inflater;
    private List<Course> _course; // Cached copy of words

    public CourseListAdapter(Context context) {
        _inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = _inflater.inflate(R.layout.activity_recycler_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if (_course != null) {
            Course current = _course.get(position);
            holder._courseItemView.setText(current.CourseTitle);
        } else {
            // Covers the case of data not being ready yet.
            holder._courseItemView.setText("No Assessment");
        }
    }

    public void setCourse(List<Course> course) {
        _course = course;
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


    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView _courseItemView;

        private CourseViewHolder(final View itemView) {
            super(itemView);
            _courseItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        public Course getItemAtPosition(int position) {
            return _course.get(position);
        }

        @Override
        public void onClick(View view) {
            // Triggers click upwards to the adapter on click
            if (listener != null) {
                int position = getAdapterPosition();

                Course x = getItemAtPosition(position);

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, x);
                }
            }
        }
    }
}
