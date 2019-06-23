package com.example.campus;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication applicationAcg;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationAcg = this;
    }

    public static MyApplication getInstance() {
        return applicationAcg;
    }
}
