package com.bwie.zhaozenghui1026.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.zhaozenghui1026.R;
import com.bwie.zhaozenghui1026.adapter.MyfFragmentPager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentYP extends Fragment {


    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_y, container, false);
        iniview();
        inidata();
        setTab();
        return view;
    }

    private void inidata() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentZZSY());
        fragmentList.add(new FragmentJJSY());

        MyfFragmentPager  myfFragmentPager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            myfFragmentPager = new MyfFragmentPager(getChildFragmentManager(),fragmentList);
        }
        viewPager.setAdapter(myfFragmentPager);
    }

    private void iniview() {
        viewPager = view.findViewById(R.id.viewPager_two);
        tabLayout = view.findViewById(R.id.tabLayout_two);
    }
    private void setTab() {

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("正在影片");
        tabLayout.getTabAt(1).setText("即将影院");
    }
}
