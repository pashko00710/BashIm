package com.example.bashim.interfaces;

import android.content.Context;

public interface FragmentLifecycle {

    public void onPauseFragment(Context context);
    public void onResumeFragment(Context context);

}