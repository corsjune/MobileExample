package edu.wgu.dmass13.c196.view.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.model.entity.Mentor;

public class MentorListAdapter extends RecyclerView.Adapter<MentorListAdapter.MentorViewHolder> {

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
            holder.mentorItemView.setText(current.Name);
        } else {
            // Covers the case of data not being ready yet.
            holder.mentorItemView.setText("No Mentor");
        }
    }

    void setMentor(List<Mentor> mentor) {
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

    class MentorViewHolder extends RecyclerView.ViewHolder {
        private final TextView mentorItemView;

        private MentorViewHolder(View itemView) {
            super(itemView);
            mentorItemView = itemView.findViewById(R.id.textView);
        }
    }
}
