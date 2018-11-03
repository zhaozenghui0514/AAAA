package com.bwie.zhaozenghui1026.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public   class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        initdata();
    }

    public void initdata() {
    }

    public void initview() {
    }
}
