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
import edu.wgu.dmass13.c196.model.entity.Mentor;

public class CourseMentorListAdapter extends RecyclerView.Adapter<CourseMentorListAdapter.CourseMentorViewHolder> {


    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, Mentor mentor, Boolean ClickState);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private final LayoutInflater _inflater;
    private List<Mentor> _mentor; // Cached copy of words
    private LongSparseArray<Boolean> _selectedMentors;


    public CourseMentorListAdapter(Context context) {
        _inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseMentorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = _inflater.inflate(R.layout.activity_recycler_grid_item, parent, false);
        return new CourseMentorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseMentorViewHolder holder, int position) {
        if (_mentor != null) {
            Mentor current = _mentor.get(position);

            if (_selectedMentors.get(current.MentorID) != null) {
                holder._mentorItemView.setChecked(true);
            } else {
                holder._mentorItemView.setChecked(false);
            }

            holder._mentorItemView.setText(current.Name);
        } else {
            // Covers the case of data not being ready yet.
            holder._mentorItemView.setText("No Assessment");
        }
    }

    public void setAllMentors(List<Mentor> mentor) {
        _mentor = mentor;
        notifyDataSetChanged();
    }
/*
    public void setSelectedMentors(List<TermMentor> mentor) {
        _selectedMentors = new LongSparseArray<TermMentor>();

        for (TermMentor temp: mentor)
        {
            _selectedMentors.append(temp.MentorID, temp);
        }
    }
    */

    public void setSelectedMentors(LongSparseArray<Boolean> mentorCheckState) {
        _selectedMentors = mentorCheckState;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (_mentor != null)
            return _mentor.size();
        else return 0;
    }


    public class CourseMentorViewHolder extends RecyclerView.ViewHolder implements CheckBox.OnCheckedChangeListener {
        private final CheckBox _mentorItemView;

        private CourseMentorViewHolder(final View itemView) {
            super(itemView);
            _mentorItemView = itemView.findViewById(R.id.ckGridItem);
            _mentorItemView.setOnCheckedChangeListener(null); //not sure if needed, but some examples on SO said to do this
            _mentorItemView.setOnCheckedChangeListener(this);
        }

        public Mentor getItemAtPosition(int position) {
            return _mentor.get(position);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            // Triggers click upwards to the adapter on click
            if (listener != null) {
                int position = getAdapterPosition();

                Mentor x = getItemAtPosition(position);

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, x, _mentorItemView.isChecked());
                }
            }
        }
    }
}
