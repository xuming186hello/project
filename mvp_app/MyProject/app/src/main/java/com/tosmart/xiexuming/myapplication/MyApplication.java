package com.tosmart.xiexuming.myapplication;

import android.app.Application;

import com.tosmart.xiexuming.myapplication.reposity.DataRepository;

/**
 * @author xiexuming
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataRepository.getInstance(this);
    }

}
