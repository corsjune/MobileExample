package edu.wgu.dmass13.c196.view.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.model.entity.Mentor;

public class MentorListAdapter extends RecyclerView.Adapter<MentorListAdapter.MentorViewHolder> {


    // Define listener member variable
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, Mentor mentor);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private final LayoutInflater _inflater;
    private List<Mentor> _mentors; // Cached copy of words

    public MentorListAdapter(Context context) {
        _inflater = LayoutInflater.from(context);
    }

    @Override
    public MentorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = _inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new MentorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MentorViewHolder holder, int position) {
        if (_mentors != null) {
            Mentor current = _mentors.get(position);
            holder._mentorItemView.setText(current.Name);
        } else {
            // Covers the case of data not being ready yet.
            holder._mentorItemView.setText("No Mentor");
        }
    }

    public void setMentor(List<Mentor> mentor) {
        _mentors = mentor;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (_mentors != null)
            return _mentors.size();
        else return 0;
    }


    public class MentorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView _mentorItemView;

        private MentorViewHolder(final View itemView) {
            super(itemView);
            _mentorItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Triggers click upwards to the adapter on click
            if (listener != null) {
                int position = getAdapterPosition();

                Mentor  x = _mentors.get(position);

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, x);
                }
            }
        }
    }
}
