package edu.wgu.dmass13.c196.view.term.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.model.entity.Term;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermViewHolder> {


    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, Term term);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private final LayoutInflater _inflater;
    private List<Term> _term; // Cached copy of words

    public TermListAdapter(Context context) {
        _inflater = LayoutInflater.from(context);
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = _inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {
        if (_term != null) {
            Term current = _term.get(position);
            holder._termItemView.setText(current.Title);
        } else {
            // Covers the case of data not being ready yet.
            holder._termItemView.setText("No Assessment");
        }
    }

    public void setTerm(List<Term> term) {
        _term = term;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (_term != null)
            return _term.size();
        else return 0;
    }


    public class TermViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView _termItemView;

        private TermViewHolder(final View itemView) {
            super(itemView);
            _termItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        public Term getItemAtPosition(int position) {
            return _term.get(position);
        }

        @Override
        public void onClick(View view) {
            // Triggers click upwards to the adapter on click
            if (listener != null) {
                int position = getAdapterPosition();

                Term x = getItemAtPosition(position);

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, x);
                }
            }
        }
    }
}
