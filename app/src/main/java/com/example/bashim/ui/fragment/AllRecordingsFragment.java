package com.example.bashim.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bashim.R;
import com.example.bashim.adapter.AllRecordingsAdapter;
import com.example.bashim.model.MyListAllRecordings;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_all_recordings)
public class AllRecordingsFragment extends Fragment {
    @ViewById(R.id.all_recordings_recyclerview)
    RecyclerView recyclerView;

    @AfterViews
    public void initExpensesRecylerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        AllRecordingsAdapter recordingsAdapter = new AllRecordingsAdapter(getRecordings());
        recyclerView.setAdapter(recordingsAdapter);
    }

    private List<MyListAllRecordings> getRecordings() {
        List<MyListAllRecordings> myListAllRecordings = new ArrayList<>();
        myListAllRecordings.add(new MyListAllRecordings("На собеседовании: \n" +
                "- почему ушли с прошлого места работы? \n" +
                "- пришлось уйти. Не обладаю достаточной гибкостью. \n" +
                "- в смысле? Медленно реагируете когда меняется ситуация? \n" +
                "- нет. У меня не получается, когда лижу жопу, преданно заглядывать в глаза."));
        myListAllRecordings.add(new MyListAllRecordings("Частенько угрожаю маме найти ее преподавателя по научному атеизму и все рассказать."));
        myListAllRecordings.add(new MyListAllRecordings("xxx: Есть только одна профессия, которую никогда не заменят роботы. Это профессия человека, который нажимает на кнопку включения робота."));
        myListAllRecordings.add(new MyListAllRecordings("Cloth"));
        myListAllRecordings.add(new MyListAllRecordings("Weapon"));
        return myListAllRecordings;
    }
}
