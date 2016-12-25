package com.example.dta.fragmentdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private ActionBar actionBar;
    private CustomViewPager customViewPager;
    private boolean isSelectionMode;
    private int mCurrentTabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        customViewPager = (CustomViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentAdapter adapter = new FragmentAdapter(fragmentManager);
        customViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(customViewPager);

        changeTabSelection();
        tabLayout.addOnTabSelectedListener(new setOnTabSelectedListener());
        customViewPager.addOnPageChangeListener(new setOnPageChangeListener());

        registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_SCREEN_ON));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteractionHome(Fragment fragment, boolean isSelection, Menu mMenu) {
        isSelectionMode = isSelection;

        if (isSelection) {
            showCheckBoxForSelectionMode();
            customViewPager.setSwipeTabEnabled(false);
        } else {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
            customViewPager.setSwipeTabEnabled(true);
        }

        if (fragment instanceof SecondFragment) {
            Toast.makeText(getApplicationContext(), "SecondFragment", Toast.LENGTH_SHORT).show();
        }
    }

    public void showCheckBoxForSelectionMode() {
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.cb_actionbar_layout, null);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        android.support.v7.app.ActionBar.LayoutParams params = new android.support.v7.app.ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(v, params);
    }

    private void changeTabSelection() {
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            final int index = i;
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return isSelectionMode && mCurrentTabIndex != index;
                }
            });
        }
    }

    class setOnTabSelectedListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mCurrentTabIndex = tab.getPosition();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    class setOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (isSelectionMode) {
                customViewPager.setSwipeTabEnabled(false);
            } else {
                customViewPager.setSwipeTabEnabled(true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    BroadcastReceiver mybroadcast = new BroadcastReceiver() {
        //When Event is published, onReceive method is called
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Log.w("anhdt", "ACTION_SCREEN_ON...");

                Intent i = new Intent(context, UnlockedScreenActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    };
}
