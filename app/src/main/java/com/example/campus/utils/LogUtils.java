package com.example.campus.utils;

import android.util.Log;

public class LogUtils {

    private static boolean showV = true;
    private static boolean showD = true;
    private static boolean showI = true;
    private static boolean showW = true;
    private static boolean showE = true;

    private static String className;//文件名
    private static String methodName;//方法名
    private static int lineNumber;//行数

    //   static String threadName  = Thread.currentThread().getName();//线程名字，能判断主线程还是子线程

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[0].getFileName();
        methodName = sElements[0].getMethodName();
        lineNumber = sElements[0].getLineNumber();
    }

    /**
     * 获取当前文件的基本信息
     */
    private static StringBuffer getFileInfo(Throwable throwable) {
        getMethodNames(throwable.getStackTrace());//获取获取包含文件名、方法名、行数的对象数据
        StringBuffer buffer = new StringBuffer();
//        buffer.append(methodName);//触发日志时候的方法名
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        return buffer;
    }


    public static void v(String tag, Throwable throwable, String msg) {
        if (showV) {
            Log.v(getFileInfo(throwable).toString() + tag, msg);
        }
    }

    public static void d(String tag, Throwable throwable, String msg) {
        if (showD) {
            Log.d(getFileInfo(throwable).toString() + tag, msg);
        }
    }

    public static void i(String tag, Throwable throwable, String msg) {
        if (showI) {
            Log.i(getFileInfo(throwable).toString() + tag, msg);
        }
    }

    public static void w(String tag, Throwable throwable, String msg) {
        if (showW) {
            Log.w(getFileInfo(throwable).toString() + tag, msg);
        }
    }

    public static void e(String tag, Throwable throwable, String msg) {
        if (showE) {
            Log.e(getFileInfo(throwable).toString() + tag, msg);
        }
    }

    public static void isDebug(boolean isShow) {
        showV = isShow;
        showD = isShow;
        showI = isShow;
        showW = isShow;
        showE = isShow;
    }

    public static void isDebug2(boolean v, boolean d, boolean i, boolean w, boolean e, boolean a) {
        showV = v;
        showD = d;
        showI = i;
        showW = w;
        showE = e;
    }
}
