package com.example.bashim.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bashim.R;
import com.example.bashim.adapter.LikedRecordingsAdapter;
import com.example.bashim.database.model.Recordings;
import com.example.bashim.util.ConstantManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.fragment_liked)
public class LikedRecordingsFragment extends Fragment {
    @ViewById(R.id.pager)
    ViewPager pager;
    @ViewById(R.id.liked_recordings_recyclerview)
    RecyclerView recyclerView;
    @ViewById(R.id.favorite_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InstanceState
    int quantityRecordings = Integer.parseInt(ConstantManager.RECORDINGS_LIMIT);

    @AfterViews
    public void ready() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onStart() {
        loadRecordings();
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRecordings();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorAccent);
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
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                recyclerView.setAdapter(new LikedRecordingsAdapter(data));
            }
            @Override
            public void onLoaderReset(Loader<List<Recordings>> loader) {
//                ​loader = null;
            }
        });
    }


}
