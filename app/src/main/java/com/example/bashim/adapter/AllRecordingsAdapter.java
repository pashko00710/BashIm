package com.example.bashim.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bashim.R;
import com.example.bashim.model.MyListAllRecordings;

import java.util.List;

public class AllRecordingsAdapter extends RecyclerView.Adapter<AllRecordingsAdapter.ViewHolder> {

    private List<MyListAllRecordings> mDataset;

public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView description;
    public ViewHolder(View v) {
        super(v);
        description = (TextView) v.findViewById(R.id.item_textview_description);
    }
}

    public AllRecordingsAdapter(List<MyListAllRecordings> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public AllRecordingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_recordings_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.description.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
