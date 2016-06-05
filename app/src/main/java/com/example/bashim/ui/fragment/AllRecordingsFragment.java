package com.example.bashim.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.bashim.R;
import com.example.bashim.adapter.AllRecordingsAdapter;
import com.example.bashim.database.model.Recordings;
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
public class AllRecordingsFragment extends Fragment {
    @ViewById(R.id.all_recordings_recyclerview)
    RecyclerView recyclerView;
    @ViewById(R.id.allrecordings_item_imagebutton_favorites)
    ImageButton imageButton;

    @InstanceState
    int quantityRecordings = Integer.parseInt(ConstantManager.RECORDINGS_LIMIT);

    private String name;

    @AfterViews
    public void initExpensesRecylerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAllRecordingsRest(recyclerView);
    }


    @Override
    public void onResume() {
        super.onResume();
        loadRecordings();
    }

    private void loadRecordings() {
        getLoaderManager().restartLoader(1, null, new LoaderManager.LoaderCallbacks<List<Recordings>>() {
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
                recyclerView.setAdapter(new AllRecordingsAdapter(data));
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
            Snackbar.make(v, "Internet if not found", Snackbar.LENGTH_LONG).show();
            return;
        }
        RestService restService = new RestService();
        ArrayList<BashImModel> bashImModel;
        try {
            bashImModel = restService.getRecordings();
        } catch (RetrofitError e) {
            Snackbar.make(v, "Internet if not found", Snackbar.LENGTH_LONG).show();
            return;
        }

        for (BashImModel recording : bashImModel) {
            Recordings recordings = new Recordings();
            name = recording.getElementPureHtml();
            recordings.setHtml(name);
            if (!recordings.exists()) {
                recordings.insert();
            } else {
                break;
            }
        }
    }
}
