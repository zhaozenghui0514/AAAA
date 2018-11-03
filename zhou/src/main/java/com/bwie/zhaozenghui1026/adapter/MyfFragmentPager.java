package com.bwie.zhaozenghui1026.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyfFragmentPager extends FragmentPagerAdapter {
    List<Fragment> list = new ArrayList<>();

    public MyfFragmentPager(FragmentManager fm,List<Fragment> list1) {
        super(fm);
        list = list1;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }


}
