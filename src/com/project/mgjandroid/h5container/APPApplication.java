package com.project.mgjandroid.h5container;

import android.app.Application;

import com.project.mgjandroid.h5base.YLH5CManager;


public class APPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        YLH5CManager.setDebugMode(true); // 默认为false
        YLH5CManager.init(this);
    }
}
