package edu.wgu.dmass13.c196.view.course.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.model.entity.Assessment;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.AssessmentViewHolder> {


    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, Assessment assessment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private final LayoutInflater _inflater;
    private List<Assessment> _assessments; // Cached copy of words

    public CourseListAdapter(Context context) {
        _inflater = LayoutInflater.from(context);
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = _inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
        if (_assessments != null) {
            Assessment current = _assessments.get(position);
            holder._assessmentItemView.setText(current.Name);
        } else {
            // Covers the case of data not being ready yet.
            holder._assessmentItemView.setText("No Assessment");
        }
    }

    public void setAssessment(List<Assessment> assessment) {
        _assessments = assessment;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (_assessments != null)
            return _assessments.size();
        else return 0;
    }


    public class AssessmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView _assessmentItemView;

        private AssessmentViewHolder(final View itemView) {
            super(itemView);
            _assessmentItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Triggers click upwards to the adapter on click
            if (listener != null) {
                int position = getAdapterPosition();

                Assessment x = _assessments.get(position);

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, x);
                }
            }
        }
    }
}
