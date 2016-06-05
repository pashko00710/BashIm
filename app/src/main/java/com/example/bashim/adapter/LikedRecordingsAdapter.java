package com.example.bashim.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bashim.R;
import com.example.bashim.database.model.Recordings;

import java.util.List;

public class LikedRecordingsAdapter extends RecyclerView.Adapter<LikedRecordingsAdapter.ViewHolder>  {
    private List<Recordings> mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.item_textview_description_liked);
        }

    }

    public LikedRecordingsAdapter(List<Recordings> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public LikedRecordingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_liked_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(mDataset.get(position).getHtml());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
