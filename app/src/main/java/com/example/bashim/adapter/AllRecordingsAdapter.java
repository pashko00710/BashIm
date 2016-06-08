package com.example.bashim.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bashim.R;
import com.example.bashim.database.model.Recordings;

import java.util.List;

public class AllRecordingsAdapter extends RecyclerView.Adapter<AllRecordingsAdapter.ViewHolder> {

    private List<Recordings> mDataset;
    private Recordings recordings;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView text;
        public ImageButton imageButton;
        public boolean liked = false;
        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.item_textview_description);
            imageButton = (ImageButton) v.findViewById(R.id.allrecordings_item_imagebutton_favorites);
            imageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            long position = (long) v.getTag();
            Toast.makeText(v.getContext(),Long.toString(position),Toast.LENGTH_SHORT).show();
            if(!liked) {
                imageButton.setImageResource(R.drawable.ic_star);
                liked = true;
            } else {
                imageButton.setImageResource(R.drawable.ic_star_outline_grey600_24dp);
                liked = false;
            }
            favoritesAdd(position);
        }
        public void favoritesAdd(long position) {
            recordings.setId(position);
            recordings.setFavorites(true);
            recordings.save();
        }

    }

    public AllRecordingsAdapter(List<Recordings> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public AllRecordingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_recordings_item, parent, false);
//        v.setOnClickListener(mOnClickListener);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        recordings = mDataset.get(position);
        holder.text.setText(mDataset.get(position).getHtml());
        holder.imageButton.setImageResource(R.drawable.ic_star_outline_grey600_24dp);
        holder.imageButton.setTag(mDataset.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
