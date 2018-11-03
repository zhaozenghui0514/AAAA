package com.bwie.zhaozenghui1026.ui.activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.MenuItem;

import com.bwie.zhaozenghui1026.R;
import com.bwie.zhaozenghui1026.adapter.MyfFragmentPager;
import com.bwie.zhaozenghui1026.ui.fragment.FragmentYP;
import com.bwie.zhaozenghui1026.ui.fragment.FragmentYY;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private android.support.v7.app.ActionBar supportActionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initdata();
        setVP();
       setDrawerLayout();
        setTab();
    }

    @Override
    public void initview() {
        super.initview();
        drawerLayout = findViewById(R.id.drawerLayout);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

    }

    @Override
    public void initdata() {

    }

    private void setVP() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentYP());
        fragmentList.add(new FragmentYY());
        fragmentList.add(new FragmentYY());
        fragmentList.add(new FragmentYY());


        MyfFragmentPager  myfFragmentPager =  new MyfFragmentPager(getFragmentManager(),fragmentList);
          viewPager.setAdapter(myfFragmentPager);
    }


    private void setTab() {

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("影片");
        tabLayout.getTabAt(1).setText("影院");
        tabLayout.getTabAt(2).setText("会员");
        tabLayout.getTabAt(3).setText("设置");
    }

    private void setDrawerLayout() {
        supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
