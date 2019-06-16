package com.example.campus.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.campus.MyApplication;

/**
 * Created by zbb on 2017/9/20.
 */

public class AppUtils {

    private static String avatarFilePath = "";

    public static String getAvatarFilePath() {
        if (TextUtils.isEmpty(avatarFilePath)) {
            avatarFilePath = getSharedPerferences().getString("avatarFilePath", "");
        }
        return avatarFilePath;
    }

    public static void setAvatarFilePath(String avatarPath) {
        avatarFilePath = avatarPath;
        SharedPreferences.Editor editor = getSharedPerferences().edit();
        editor.putString("avatarFilePath", avatarPath);
        editor.commit();
    }

    public static SharedPreferences getSharedPerferences() {
        return MyApplication.getInstance().getSharedPreferences("300fans", Context.MODE_PRIVATE);
    }


}
