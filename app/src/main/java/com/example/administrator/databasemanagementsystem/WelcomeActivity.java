package com.example.administrator.databasemanagementsystem;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Administrator on 2017/4/7.
 */

public class WelcomeActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
