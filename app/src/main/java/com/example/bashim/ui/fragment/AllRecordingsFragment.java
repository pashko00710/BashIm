package com.example.bashim.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bashim.R;
import com.example.bashim.adapter.AllRecordingsAdapter;
import com.example.bashim.database.model.Recordings;
import com.example.bashim.interfaces.FragmentLifecycle;
import com.example.bashim.rest.RestService;
import com.example.bashim.rest.model.BashImModel;
import com.example.bashim.util.ConstantManager;
import com.example.bashim.util.NetworkStatusChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

@EFragment(R.layout.fragment_all_recordings)
public class AllRecordingsFragment extends Fragment implements FragmentLifecycle {
    @ViewById(R.id.pager)
    ViewPager pager;
    @ViewById(R.id.all_recordings_recyclerview)
    RecyclerView recyclerView;
    @ViewById(R.id.allrecordings_item_imagebutton_favorites)
    ImageButton imageButton;
    @ViewById(R.id.all_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InstanceState
    int quantityRecordings = Integer.parseInt(ConstantManager.RECORDINGS_LIMIT);

    private String name;
    private Recordings recordings;

    @AfterViews
    public void initExpensesRecylerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAllRecordingsRest(recyclerView);
    }


    @Override
    public void onResume() {
//        loadRecordings();
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

    @Override
    public void onStart() {
        loadRecordings();
        super.onStart();
    }


    private void loadRecordings() {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Recordings>>() {
            @Override
            public Loader<List<Recordings>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Recordings>> loader = new AsyncTaskLoader<List<Recordings>>(getActivity()) {
                    @Override
                    public List<Recordings> loadInBackground() {
                        return Recordings.getAllRecordings(quantityRecordings);
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
                recyclerView.setAdapter(new AllRecordingsAdapter(data, getContext()));
            }
            @Override
            public void onLoaderReset(Loader<List<Recordings>> loader) {
//                ​loader = null;
            }
        });
    }

    @Background
    public void getAllRecordingsRest(View v) {
        if (!NetworkStatusChecker.isNetworkAvailable(getContext())) {
            Snackbar.make(v, R.string.internet_is_not_found, Snackbar.LENGTH_LONG).show();
            return;
        }
        RestService restService = new RestService();
        ArrayList<BashImModel> bashImModel;
        try {
            bashImModel = restService.getRecordings();
        } catch (RetrofitError e) {
            Snackbar.make(v, R.string.internet_is_not_found, Snackbar.LENGTH_LONG).show();
            return;
        }

        for (BashImModel recording : bashImModel) {
            recordings = new Recordings();
            name = recording.getElementPureHtml();
            recordings.setFavorites(false);
            recordings.setHtml(name);
            if (!recordings.exists()) {
                recordings.insert();
            } else {
                break;
            }
        }
    }

    @Override
    public void onPauseFragment(Context context) {
//        setUserVisibleHint(true);
        Toast.makeText(context, "onPauseFragment():" + "FragmentAll", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResumeFragment(Context context) {
//        setUserVisibleHint(true);
//        loadRecordings();
        Toast.makeText(context, "onResumeFragment():" + "FragmentAll", Toast.LENGTH_SHORT).show();
    }

    //закомментить onStart и расскоментить setUserVisibleHint для автоматической подгрузки записей
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadRecordings();
        }
    }

}
