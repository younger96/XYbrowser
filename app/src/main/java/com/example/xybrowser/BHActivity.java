package com.example.xybrowser;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.BLACK;
import static com.example.xybrowser.R.color.colorGREEN;
import static com.example.xybrowser.R.color.colorYELLOW;


/**
 * Created by xy on 2017/4/10.
 */

public class BHActivity extends FragmentActivity {
    private List<String> titleList;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bh);


        titleList = new ArrayList<>();
        titleList.add("书签");
        titleList.add("历史记录");

        fragments = new ArrayList<Fragment>();
        fragments.add(new BookMarksFragment());
        fragments.add(new HistoryFragment());

        FragmentPagerAdapter adapter1 = new FragmentPagerAdapter((FragmentManager) getSupportFragmentManager(), fragments, titleList);
        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);
        PagerTabStrip tab = (PagerTabStrip) findViewById(R.id.tab);
        vp.setAdapter(adapter1);


        tab.setBackgroundColor(getResources().getColor(colorGREEN));
        tab.setTextColor(BLACK);
        tab.setDrawFullUnderline(false);
        tab.setTabIndicatorColor(getResources().getColor(colorYELLOW));


    }
}

