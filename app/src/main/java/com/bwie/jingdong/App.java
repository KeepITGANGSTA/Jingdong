package com.bwie.jingdong;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import manager.GreenDaoManager;

/**
 * Created by 李英杰 on 2017/9/29.
 */

public class App extends Application {

    public static Context context;
    public static int screen_width = -1;
    public static int screen_height = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        context=getApplicationContext();
        getScreen();
        initGreenDao();
    }

    private void initGreenDao() {
        GreenDaoManager.initDatabase();
    }

    private void initImageLoader() {
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    private void getScreen() {

        DisplayMetrics display = getResources().getDisplayMetrics();
        screen_width = display.widthPixels;
        screen_height = display.heightPixels;
    }
}
