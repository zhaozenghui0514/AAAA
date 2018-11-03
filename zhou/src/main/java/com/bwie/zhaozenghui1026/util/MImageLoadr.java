package com.bwie.zhaozenghui1026.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.bwie.zhaozenghui1026.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MImageLoadr {
    public  static ImageLoaderConfiguration getImageLoaderConfiguration(Context context){

        return  new ImageLoaderConfiguration.Builder(context)
                .diskCacheSize(13)
                .diskCacheFileCount(10*1024*1024)
                .writeDebugLogs()
                .build();
    }

    public  static DisplayImageOptions getDisplayImageOptions(Context context){
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .build();
    }
}
