package com.example.bashim.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.bashim.ui.fragment.AllRecordingsFragment;
import com.example.bashim.ui.fragment.LikedRecordingsFragment;

public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AllRecordingsFragment allRecordingsFragment = new AllRecordingsFragment();
                return allRecordingsFragment;
            case 1:
                LikedRecordingsFragment likedFragment = new LikedRecordingsFragment();
                return likedFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
