package com.example.bashim.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.bashim.R;
import com.example.bashim.adapter.PageAdapter;
import com.example.bashim.interfaces.FragmentLifecycle;
import com.example.bashim.ui.fragment.AllRecordingsFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById(R.id.toolbar)
    Toolbar toolBar;
    @ViewById
    TabLayout tabLayout;
    @ViewById(R.id.pager)
    ViewPager viewPager;

    PageAdapter adapter;
    private static final String PAGER_TAG = "MainActivity.PAGER_TAG";


    @AfterViews
    public void ready() {
        setupActionBar();
        initTabLayout();
        setTitle(R.string.app_name);
        causeFragment(new AllRecordingsFragment_());
        viewPager.setOnPageChangeListener(pageChangeListener);
    }

    private void causeFragment(Fragment fragment) {
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);


        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.pager, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.main_activity_all_recordings));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.main_activity_liked_recordings));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new PageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), getApplicationContext());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //Лаги здесь)) внизу одна строка
//                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupActionBar() {
        setSupportActionBar(toolBar);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            super.onBackPressed();
        }
    }



    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        int currentPosition = 0;

        @Override
        public void onPageSelected(int newPosition) {

            FragmentLifecycle fragmentToHide = (FragmentLifecycle)adapter.getItem(currentPosition);
            fragmentToHide.onPauseFragment(getApplicationContext());

            FragmentLifecycle fragmentToShow = (FragmentLifecycle)adapter.getItem(newPosition);
            fragmentToShow.onResumeFragment(getApplicationContext());

            currentPosition = newPosition;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) { }

        public void onPageScrollStateChanged(int arg0) { }
    };

}
