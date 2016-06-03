package com.example.bashim.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;

import com.example.bashim.R;
import com.example.bashim.adapter.AllRecordingsAdapter;
import com.example.bashim.database.model.Recordings;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.fragment_all_recordings)
public class AllRecordingsFragment extends Fragment {
    @ViewById(R.id.all_recordings_recyclerview)
    RecyclerView recyclerView;
    @ViewById(R.id.allrecordings_item_imageview)
    ImageButton imageButton;

    @InstanceState
    int quantityQuotes = 7;

    boolean liked = false;

    @AfterViews
    public void initExpensesRecylerView() {
//        imageButton.setImageResource(R.drawable.ic_star_outline_grey600_24dp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(Recordings.getAllRecordings(quantityQuotes).isEmpty()) {
            getRecordings();
        }
    }

    @Click(R.id.allrecordings_item_imageview)
    public void likeRecord() {
        Log.d("here", "here");
        if(!liked) {
            imageButton.setImageResource(R.drawable.ic_star);
            liked = true;
        } else {
            imageButton.setImageResource(R.drawable.ic_star_outline_grey600_24dp);
            liked = false;
        }
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
                        return Recordings.getAllRecordings(quantityQuotes);
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

    private void getRecordings() {
        Recordings recordings = new Recordings();
        recordings.setHtml("xxx: Моя жизнь похожа на хождение слепого, " + "\n" +
                "глухого и немного туповатого человека по полю с граблями.");
        recordings.insert();
    }
}
