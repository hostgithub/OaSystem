package com.gdtc.oasystem.tablayout;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.fragment.DispatchHasDealFragment;
import com.gdtc.oasystem.fragment.DispatchWaitDealFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjiawei on 2018-2-7.
 */
public class TabActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> fragments;
    private List<String> titles;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.index_blue);// 通知栏所需颜色
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);

        mTabLayout.setupWithViewPager(mViewPager);


        fragments = new ArrayList<Fragment>();
        Fragment aFragment = new DispatchWaitDealFragment();
        Fragment bFragment = new DispatchHasDealFragment();

        fragments.add(aFragment);
        fragments.add(bFragment);

        titles = new ArrayList<String>();
        titles.add("待办");
        titles.add("已办");
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

}
