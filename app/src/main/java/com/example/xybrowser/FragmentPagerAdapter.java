package com.example.xybrowser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by xy on 2017/4/10.
 */

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {


    private List<Fragment> fragmentLists;
    private List<String> titleList;

    public FragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentLists, List<String> titleList) {
        super(fm);
        this.fragmentLists = fragmentLists;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentLists.get(position);
    }

    @Override
    public int getCount() {
        return fragmentLists.size();
    }

    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
