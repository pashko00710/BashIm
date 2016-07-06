package com.example.bashim.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bashim.R;
import com.example.bashim.database.model.Recordings;

import java.util.List;

public class LikedRecordingsAdapter extends RecyclerView.Adapter<LikedRecordingsAdapter.ViewHolder>  {
    private List<Recordings> mDataset;
    private Recordings recordings;
    private int lastPosition = -1;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView text;
        public ImageButton imageButtonRemoved;
        public CardView cardView;
        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view_liked);
            text = (TextView) v.findViewById(R.id.item_textview_description_liked);
            imageButtonRemoved = (ImageButton) v.findViewById(R.id.liked_item_imagebutton_delete);
            imageButtonRemoved.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            long position = (long) v.getTag();
            recordings.setId(position);
            recordings.setFavorites(false);
            recordings.delete();
//            recordings.removeFavorites(position);
        }
    }

    public LikedRecordingsAdapter(List<Recordings> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public LikedRecordingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_liked_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            view.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        recordings = mDataset.get(position);
        holder.text.setText(mDataset.get(position).getHtml());
        holder.imageButtonRemoved.setTag(mDataset.get(position).getId());
        setAnimation(holder.cardView, position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
