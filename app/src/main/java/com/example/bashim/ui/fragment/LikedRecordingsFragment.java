package com.example.bashim.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.bashim.R;
import com.example.bashim.adapter.LikedRecordingsAdapter;
import com.example.bashim.database.model.Recordings;
import com.example.bashim.interfaces.FragmentLifecycle;
import com.example.bashim.interfaces.ItemCLick;
import com.example.bashim.util.ConstantManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.fragment_liked)
public class LikedRecordingsFragment extends Fragment implements FragmentLifecycle {
    @ViewById(R.id.pager)
    ViewPager pager;
    @ViewById(R.id.liked_recordings_recyclerview)
    RecyclerView recyclerView;
    @InstanceState
    int quantityRecordings = Integer.parseInt(ConstantManager.RECORDINGS_LIMIT);
    boolean visible = false;
    LikedRecordingsAdapter likedRecordingsAdapter;


    @AfterViews
    public void ready() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void loadRecordings() {
        getLoaderManager().restartLoader(1, null, new LoaderManager.LoaderCallbacks<List<Recordings>>() {
            @Override
            public Loader<List<Recordings>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Recordings>> loader = new AsyncTaskLoader<List<Recordings>>(getActivity()) {
                    @Override
                    public List<Recordings> loadInBackground() {
                        return Recordings.getAllFavoriteRecordings(quantityRecordings);
                    }
                };
                loader.forceLoad();
                return loader;
            }
            @Override
            public void onLoadFinished(Loader<List<Recordings>> loader, List<Recordings> data) {
                LikedRecordingsAdapter adapter = (LikedRecordingsAdapter) recyclerView.getAdapter();
                if(adapter == null) {
                    likedRecordingsAdapter = new LikedRecordingsAdapter(data, getContext(), new ItemCLick() {
                        @Override
                        public void onItemClick(int position) {
                            likedRecordingsAdapter.removeItem(position);
                        }

                    });
                    recyclerView.setAdapter(likedRecordingsAdapter);
                } else {
                    adapter.refresh(data);
                }
            }
            @Override
            public void onLoaderReset(Loader<List<Recordings>> loader) {
//                ​loader = null;
            }
        });
    }



    @Override
    public void onPauseFragment(Context context) {
//        loadRecordings();
//        setUserVisibleHint(true);
        Toast.makeText(context, "onPauseFragment():" + "FragmentLiked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResumeFragment(Context context) {
//        loadRecordings();
//        setUserVisibleHint(true);
        Toast.makeText(context, "onResumeFragment():" + "FragmentLiked", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadRecordings();
        }
    }


}
