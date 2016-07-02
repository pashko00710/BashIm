package com.example.bashim.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.example.bashim.ui.fragment.AllRecordingsFragment;
import com.example.bashim.ui.fragment.AllRecordingsFragment_;
import com.example.bashim.ui.fragment.LikedRecordingsFragment;
import com.example.bashim.ui.fragment.LikedRecordingsFragment_;

import java.util.HashMap;
import java.util.Map;

public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private Map<Integer, String> mFragmentTags;
    FragmentManager mFragmentManager;
    Context mContext;

    public PageAdapter(FragmentManager fm, int NumOfTabs, Context context) {
        super(fm);
        mFragmentManager = fm;
        this.mNumOfTabs = NumOfTabs;
        mContext = context;
        mFragmentTags = new HashMap <Integer, String>();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AllRecordingsFragment allRecordingsFragment = new AllRecordingsFragment_();
                return allRecordingsFragment;
            case 1:
                LikedRecordingsFragment likedFragment = new LikedRecordingsFragment_();
                return likedFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        Log.e("lal", String.valueOf(position));
        if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            String tag = fragment.getTag();
            mFragmentTags.put(position, tag);
        }
        return object;
    }

}
