package com.bwie.zhaozenghui1026;

import android.app.Application;

import com.bwie.zhaozenghui1026.util.MImageLoadr;
import com.nostra13.universalimageloader.core.ImageLoader;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.getInstance().init(MImageLoadr.getImageLoaderConfiguration(this));
    }
}
