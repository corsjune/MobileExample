package edu.wgu.dmass13.c196.view.course.components;

import android.content.Context;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.model.entity.Assessment;

public class CourseAssessmentListAdapter extends RecyclerView.Adapter<CourseAssessmentListAdapter.CourseAssessmentViewHolder> {


    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, Assessment assessment, Boolean ClickState);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private final LayoutInflater _inflater;
    private List<Assessment> _assessment; // Cached copy of words
    private LongSparseArray<Boolean> _selectedAssessments;


    public CourseAssessmentListAdapter(Context context) {
        _inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseAssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = _inflater.inflate(R.layout.activity_recycler_grid_item, parent, false);
        return new CourseAssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseAssessmentViewHolder holder, int position) {
        if (_assessment != null) {
            Assessment current = _assessment.get(position);

            if (_selectedAssessments.get(current.AssessmentID) != null) {
                holder._assessmentItemView.setChecked(true);
            } else {
                holder._assessmentItemView.setChecked(false);
            }

            holder._assessmentItemView.setText(current.Name);
        } else {
            // Covers the case of data not being ready yet.
            holder._assessmentItemView.setText("No Assessment");
        }
    }

    public void setAllAssessments(List<Assessment> assessment) {
        _assessment = assessment;
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

    public void setSelectedAssessments(LongSparseArray<Boolean> assessmentCheckState) {
        _selectedAssessments = assessmentCheckState;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (_assessment != null)
            return _assessment.size();
        else return 0;
    }


    public class CourseAssessmentViewHolder extends RecyclerView.ViewHolder implements CheckBox.OnCheckedChangeListener {
        private final CheckBox _assessmentItemView;

        private CourseAssessmentViewHolder(final View itemView) {
            super(itemView);
            _assessmentItemView = itemView.findViewById(R.id.ckGridItem);
            _assessmentItemView.setOnCheckedChangeListener(null); //not sure if needed, but some examples on SO said to do this
            _assessmentItemView.setOnCheckedChangeListener(this);
        }

        public Assessment getItemAtPosition(int position) {
            return _assessment.get(position);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            // Triggers click upwards to the adapter on click
            if (listener != null) {
                int position = getAdapterPosition();

                Assessment x = getItemAtPosition(position);

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, x, _assessmentItemView.isChecked());
                }
            }
        }
    }
}
