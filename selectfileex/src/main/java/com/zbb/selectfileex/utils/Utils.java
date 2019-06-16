package com.zbb.selectfileex.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.util.TypedValue;

import com.zbb.selectfileex.R;
import com.zbb.selectfileex.utils.Constant;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by snsoft on 2016/9/28.
 */

public class Utils {

    private static final String TAG="Utils";

    public static Bitmap getImageBitmap(Bitmap bmp) {
        return ThumbnailUtils.extractThumbnail(bmp,
                (int) (Constant.width * 0.25), (int) (Constant.width * 0.25));
    }

    public static String getTimeFormat(int time) {
        String time1 = new BigDecimal(time * 0.001).setScale(0,
                BigDecimal.ROUND_HALF_UP) + "";
        time = Integer.parseInt(time1);// 这个time单位是秒
        String hhS = "00";
        String fenS = "00";
        String miaoS = "00";
        int hh = time / 3600;
        int fen = (time - hh * 3600) / 60;
        int miao = time % 60;
        if (hh < 10) {
            hhS = "0" + hh;
        } else {
            hhS = "" + hh;
        }
        if (fen < 10) {
            fenS = "0" + fen;
        } else {
            fenS = "" + fen;
        }
        if (miao < 10) {
            miaoS = "0" + miao;
        } else {
            miaoS = "" + miao;
        }
        Log.e("时间:", fenS + ":" + miaoS);
        Log.e("时间2:", hhS + ":" + fenS + ":" + miaoS);
        if (hh == 0) {
            return fenS + ":" + miaoS;
        } else {
            return hhS + ":" + fenS + ":" + miaoS;
        }

    }
    public static String getTimeFormatByS(int time) {
//        String time1 = new BigDecimal(time * 0.001).setScale(0,
//                BigDecimal.ROUND_HALF_UP) + "";
//        time = Integer.parseInt(time1);// 这个time单位是秒
        String hhS = "00";
        String fenS = "00";
        String miaoS = "00";
        int hh = time / 3600;
        int fen = (time - hh * 3600) / 60;
        int miao = time % 60;
        if (hh < 10) {
            hhS = "0" + hh;
        } else {
            hhS = "" + hh;
        }
        if (fen < 10) {
            fenS = "0" + fen;
        } else {
            fenS = "" + fen;
        }
        if (miao < 10) {
            miaoS = "0" + miao;
        } else {
            miaoS = "" + miao;
        }
        Log.e("时间:", fenS + ":" + miaoS);
        Log.e("时间2:", hhS + ":" + fenS + ":" + miaoS);
        if (hh == 0) {
            return fenS + ":" + miaoS;
        } else {
            return hhS + ":" + fenS + ":" + miaoS;
        }

    }

    public static File saveBitmapFile(Bitmap bitmap) {
        //bitmap = ExUtils.comp(bitmap);//先按比例压缩，再按质量压缩,但是压缩会失真,但是不压缩，又会加载很慢，还是按大小压缩下吧
        String name = System.currentTimeMillis() + "";
        File fileFolder = new File(Constant.picturePath);
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        File file = new File(Constant.picturePath + File.separator + name
                + ".png");// 将要保存图片的路径
        Log.i(TAG,"图片保存地址："+Constant.picturePath + File.separator + name
                + ".png");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static int px2dp(float pxValue, Context context) {
        return (int) (pxValue / context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String str = sdf.format(date);
        return str;
    }

    public static String getModifiedFormat(long time) {
        Date date = new Date(time);
        return dateToString(date, "yyyy-MM-dd HH:mm");
    }

    public static long mb = 1024 * 1024;
    public static long gb = 1024 * 1024 * 1024;
    static DecimalFormat format = new DecimalFormat("#0.00");

    //    String result = format.format(percent);
    public static String getSizeformat(long fileSize) {
        String size = fileSize + "B";
        if (fileSize < 1024) {
            size = fileSize + "B";
        } else if (fileSize < mb) {
            //大于1B小于1Mb
            size = format.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < gb) {
            //大于1Mb小于1G
            size = format.format((double) fileSize / mb) + "M";
        } else {
            //大于1G
            size = format.format((double) fileSize / gb) + "G";
        }
        return size;
    }


    public static int getPicByFileType(String fileName) {
        //获取后缀名并转为小写
        String type = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        int typeImage = R.mipmap.unknown;
        switch (type) {
            case "jpg":
            case "jpeg":
            case "bmp":
            case "png":
            case "gif":
                typeImage = R.mipmap.pic;
                break;
            case "txt":
            case "html":
            case "xml":
            case "log":
            case "conf":
                typeImage = R.mipmap.txt;
                break;
            case "apk":
                typeImage = R.mipmap.apk;
                break;
            case "mp4":
            case "3gp":
            case "avi":
            case "rmvb":
            case "wmv":
            case "rm":
            case "asf":
            case "mov":
                typeImage = R.mipmap.sp;
                break;
            case "mp3":
            case "wav":
            case "wma":
            case "ogg":
            case "ape":
            case "acc":
                typeImage = R.mipmap.music;
                break;
            case "rar":
            case "zip":
                typeImage = R.mipmap.zip;
                break;
            case "doc":
            case "docx":
                typeImage = R.mipmap.doc;
                break;
            default:
                typeImage = R.mipmap.unknown;
                break;

        }
        return typeImage;
    }

}
