package com.example.campus.utils;

import android.os.Environment;

import com.example.campus.bean.User;

import java.io.File;

/**
 * Created by snsoft on 2016/12/15.
 */

public class Constant {
    public static String basePath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "Test";
    public static String imagePath = basePath + File.separator + "images";
    public static User user;
}
