package com.example.campus;

import android.app.Application;

/**
 * @author 作者 zbb
 * @version 创建时间：2018/6/5 9:37
 */
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
