package com.example.campus;

import android.app.Application;

public class MZApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       //ANR检测
       // new ANRWatchDog().start();
    }
}
