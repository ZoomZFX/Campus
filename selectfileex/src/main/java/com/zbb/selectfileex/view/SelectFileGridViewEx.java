package com.zbb.selectfileex.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.zbb.selectfileex.AudioRecorderActivity;
import com.zbb.selectfileex.R;
import com.zbb.selectfileex.SelectFileActivity;
import com.zbb.selectfileex.SelectFileExActivity;
import com.zbb.selectfileex.adapter.GvPictureMinAdapter;
import com.zbb.selectfileex.utils.Constant;
import com.zbb.selectfileex.utils.PermissionUtils;
import com.zbb.selectfileex.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zbb on 2016/9/28.
 */

public class SelectFileGridViewEx extends GridView {

    private final String TAG = "SelectFileGridViewEx";

    private final int REQUEST_CODE_PIC = 100;//选择图片
    private final int REQUEST_CODE_CAMERA = 101;//拍照
    private final int REQUEST_CODE_VIDEO = 102;//选择视频
    private final int REQUEST_CODE_RECORDER = 103;//录像
    private final int REQUEST_CODE_FILE = 104;//选择文件
    private final int REQUEST_CODE_VOICE = 105;// 是调用录音机
    //拍照权限
    private String[] permission_camera = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //录像权限
    private String[] permission_recorder = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //录音权限
    private String[] permission_voice = new String[]{android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String[] itemNames2 = {"文件", "图片", "拍照", "视频", "录像", "录音"};
    private int[] rs2 = new int[]{R.mipmap.wenjiantubiao, R.mipmap.tupiantubiao, R.mipmap.paizhaotubiao, R.mipmap.shipintubiao, R.mipmap.luxiangtubiao, R.mipmap.luyinex};
    private int[] requestCodes2 = {REQUEST_CODE_FILE, REQUEST_CODE_PIC, REQUEST_CODE_CAMERA, REQUEST_CODE_VIDEO, REQUEST_CODE_RECORDER, REQUEST_CODE_VOICE};
    private List<String> itemNames;
    private List<Integer> resourcesIds;
    private List<Integer> requestCodes;
    ArrayList<File> listFiles;
    List<Bitmap> listBitmap;
    private DialogMenuEx dialogMenuEx;
    private View.OnClickListener itemOnClick;

    private Activity activity;
    private Context context;
    private int maxFileSize = -1;//选择文件的数量限制，-1代表没有限制
    private boolean isAdd = false;

    public SelectFileGridViewEx(Context context) {
        super(context);
        this.context = context;
        initData();
    }

    public SelectFileGridViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData();
    }

    public SelectFileGridViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initData();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private void initData() {
        setNumColumns(4);
        setStretchMode(STRETCH_COLUMN_WIDTH);
        setHorizontalSpacing(1);
        setVerticalSpacing(1);
        itemNames = new ArrayList<String>();
        resourcesIds = new ArrayList<Integer>();
        requestCodes = new ArrayList<Integer>();


        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        Constant.width = metric.widthPixels; // 屏幕宽度（像素）
        Constant.height = metric.heightPixels; // 屏幕高度（像素）
        listFiles = new ArrayList<File>();
        listBitmap = new ArrayList<Bitmap>();
        refreshGV();
        setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (maxFileSize == -1 || listFiles.size() < maxFileSize) {
                    //如果满足上面的条件，才能继续添加文件
                    if (i == listFiles.size()) {
                        // 说明点击的是最后一个，就是添加的按钮
                        if (itemNames.size() == 1) {
                            //如果只有一个，直接弹出功能
                            selectFunction(requestCodes.get(0));
                        } else {
                            dialogMenuEx = new DialogMenuEx(activity, itemOnClick, itemNames, resourcesIds, requestCodes, 4, false);
                            dialogMenuEx.show();
                        }
                    }
                }
            }
        });

        setOnItemLongClickListener(new GridView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if (i < listFiles.size()) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(
                            activity);
                    adb.setTitle(R.string.delete);
                    adb.setMessage("确认删除？");
                    adb.setPositiveButton(R.string.determine,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    // TODO Auto-generated method stub
                                    if (maxFileSize == -1 || listFiles.size() < maxFileSize) {
                                        //说明有那个添加的按钮
                                        listFiles.remove(i);
                                        listBitmap.remove(i);//从显示的图片中移除
                                        listBitmap.remove(listBitmap.size() - 1);//从显示的图片中移除加号
                                        refreshGV();
                                    } else {
                                        listFiles.remove(i);
                                        listBitmap.remove(i);//从显示的图片中移除
                                        refreshGV();
                                    }

                                }
                            });
                    adb.setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    arg0.cancel();
                                }
                            });
                    adb.show();

                }
                return false;
            }
        });
        itemOnClick = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                int tag = (Integer) view.getTag();
                selectFunction(tag);
                dialogMenuEx.dismiss();
            }
        };
    }

    private void selectFunction(int tag) {
        switch (tag) {
            case REQUEST_CODE_FILE:
                //文件
                Intent intentFile = new Intent(activity, SelectFileActivity.class);
                activity.startActivityForResult(intentFile, REQUEST_CODE_FILE);
//                Intent intentFile = new Intent(Intent.ACTION_GET_CONTENT);
//                intentFile.setType("*/*");
//                intentFile.putExtra("return-data", true);
//                activity.startActivityForResult(intentFile, REQUEST_CODE_FILE);
                break;
            case REQUEST_CODE_PIC:
                //选择图片
                Intent intentImage = new Intent(activity, SelectFileExActivity.class);
                intentImage.putExtra("fileType", 0);
                int maxImage = maxFileSize;
                if (maxFileSize != -1) {
                    maxImage = maxFileSize - listFiles.size();
                }
                intentImage.putExtra("maxFileSize", maxImage);
                activity.startActivityForResult(intentImage, REQUEST_CODE_PIC);

//                        Intent intentt = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
//                        intentt.setType("image/*"); // 查看类型 String IMAGE_UNSPECIFIED
//                        Intent intent = Intent.createChooser(intentt, null);
//                        activity.startActivityForResult(intent, REQUEST_CODE_PIC);
                break;
            case REQUEST_CODE_VIDEO:
                //选择视频文件
                Intent intentVideo = new Intent(activity, SelectFileExActivity.class);
                intentVideo.putExtra("fileType", 1);
                int maxVideo = maxFileSize;
                if (maxFileSize != -1) {
                    maxVideo = maxFileSize - listFiles.size();
                }
                intentVideo.putExtra("maxFileSize", maxVideo);
                activity.startActivityForResult(intentVideo, REQUEST_CODE_VIDEO);

//                        Intent intentVideo = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
//                        intentVideo.setType("video/*"); // 查看类型 String IMAGE_UNSPECIFIED
//                        intentVideo.putExtra("return-data", true);
//                        //Intent intentVideo2 = Intent.createChooser(intentVideo, null);
//                        activity.startActivityForResult(intentVideo, REQUEST_CODE_VIDEO);
                break;
            case REQUEST_CODE_CAMERA:
                // 拍照
                if (PermissionUtils.checkSelfPermission(activity, permission_camera, REQUEST_CODE_CAMERA)) {
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    activity.startActivityForResult(intent2, REQUEST_CODE_CAMERA);
                }
                break;
            case REQUEST_CODE_RECORDER:
                //录像
                if (PermissionUtils.checkSelfPermission(activity, permission_recorder, REQUEST_CODE_RECORDER)) {
                    Intent intentRecorder = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    activity.startActivityForResult(intentRecorder, REQUEST_CODE_RECORDER);
                }
                break;
            case REQUEST_CODE_VOICE:
                //录音  录音这个不是调用的系统录音机，是自己写的录音，所以在另一个界面判断录音吧，但是录像和拍照都是这里的哎，就这样吧
//                if (PermissionUtils.checkSelfPermission(activity, permission_voice, REQUEST_CODE_VOICE)) {
                Intent intentVoice = new Intent(context, AudioRecorderActivity.class);
                activity.startActivityForResult(intentVoice, REQUEST_CODE_VOICE);
//                }
                break;
            default:

                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean accepted = true;
        for (int grant : grantResults) {
            if (grant != PackageManager.PERMISSION_GRANTED) {
                accepted = false;
            }
        }
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                //拍照
                if (accepted) {
                    //授权成功之后，调用系统相机进行拍照操作等
                    Log.e(TAG, "授权成功");
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    activity.startActivityForResult(intent2, REQUEST_CODE_CAMERA);
                } else {
                    //用户授权拒绝之后，友情提示一下就可以了
                    Log.e(TAG, "权限被拒绝");
                    //这里应该弹出dialog详细说明一下
//                    Toast.makeText(activity, "您拒绝了拍照所需权限的申请，将不能进行拍照，请在设置或安全中心开启该项权限后重新操作", Toast.LENGTH_LONG).show();
                    DialogPromptPermission dialogPromptPermission = new DialogPromptPermission(context);
                    dialogPromptPermission.setPromptText("您拒绝了拍照所需权限的申请，将不能进行拍照，请在设置或安全中心开启相机和读写手机储存的权限后重新操作");
                    dialogPromptPermission.show();
                }
                break;
            case REQUEST_CODE_RECORDER:
                //录像
                if (accepted) {
                    //授权成功之后，调用系统相机进行拍照操作等
                    Log.e(TAG, "授权成功");
                    Intent intentRecorder = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    activity.startActivityForResult(intentRecorder, REQUEST_CODE_RECORDER);
                } else {
                    //用户授权拒绝之后，友情提示一下就可以了
                    Log.e(TAG, "权限被拒绝");
//                    Toast.makeText(activity, "您拒绝了录像所需权限的申请，将不能进行录像，请在设置或安全中心开启该项权限后重新操作", Toast.LENGTH_LONG).show();
                    DialogPromptPermission dialogPromptPermission = new DialogPromptPermission(context);
                    dialogPromptPermission.setPromptText("您拒绝了录像所需权限的申请，将不能进行录像，请在设置或安全中心开启相机和读写手机储存的权限后重新操作");
                    dialogPromptPermission.show();
                }
                break;
//            case REQUEST_CODE_VOICE:
//                //录音
//                if (accepted) {
//                    //授权成功之后，调用系统相机进行拍照操作等
//                    Log.e(TAG, "授权成功");
//                    Intent intentVoice = new Intent(context, AudioRecorderActivity.class);
//                    activity.startActivityForResult(intentVoice, REQUEST_CODE_VOICE);
//                } else {
//                    //用户授权拒绝之后，友情提示一下就可以了
//                    Log.e(TAG, "权限被拒绝");
////                    Toast.makeText(activity, "您拒绝了录音所需权限的申请，将不能进行录音，请在设置或安全中心开启该项权限后重新操作", Toast.LENGTH_LONG).show();
//                    DialogPromptPermission dialogPromptPermission= new DialogPromptPermission(context);
//                    dialogPromptPermission.setPromptText("您拒绝了录音所需权限的申请，将不能进行录像，请在设置或安全中心开启录音和读写手机储存的权限后重新操作");
//                    dialogPromptPermission.show();
//                }
//                break;

        }
    }

    private void refreshGV() {
// 用户选择的图片加入之前先移除最后一个（那个添加的按钮），添加图片后，再加入
        // 将添加的按钮放到GridView中
        if (maxFileSize == -1 || listFiles.size() < maxFileSize) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.shangchuan);
            listBitmap.add(Utils.getImageBitmap(bmp));
        }
        GvPictureMinAdapter gap = new GvPictureMinAdapter(context, listBitmap);
        setAdapter(gap);
    }

    public ArrayList<File> getFileList() {
        return listFiles;
    }

    public void init(Activity activity) {
        this.activity = activity;
        for (String str : itemNames2) {
            itemNames.add(str);
        }
        for (Integer id : rs2) {
            resourcesIds.add(id);
        }
        for (Integer code : requestCodes2) {
            requestCodes.add(code);
        }
    }

    public void init(Activity activity, int maxFileSize) {
        this.activity = activity;
        this.maxFileSize = maxFileSize;
        for (String str : itemNames2) {
            itemNames.add(str);
        }
        for (Integer id : rs2) {
            resourcesIds.add(id);
        }
        for (Integer code : requestCodes2) {
            requestCodes.add(code);
        }
    }

    public void setMenu(boolean file, boolean picture, boolean photo, boolean video, boolean videoRecord, boolean voiceRecord) {
        itemNames.clear();
        resourcesIds.clear();
        requestCodes.clear();
        if (file) {
            itemNames.add("文件");
            resourcesIds.add(R.mipmap.wenjiantubiao);
            requestCodes.add(REQUEST_CODE_FILE);
        }
        if (picture) {
            itemNames.add("图片");
            resourcesIds.add(R.mipmap.tupiantubiao);
            requestCodes.add(REQUEST_CODE_PIC);
        }
        if (photo) {
            itemNames.add("拍照");
            resourcesIds.add(R.mipmap.paizhaotubiao);
            requestCodes.add(REQUEST_CODE_CAMERA);
        }
        if (video) {
            itemNames.add("视频");
            resourcesIds.add(R.mipmap.shipintubiao);
            requestCodes.add(REQUEST_CODE_VIDEO);
        }
        if (videoRecord) {
            itemNames.add("录像");
            resourcesIds.add(R.mipmap.luxiangtubiao);
            requestCodes.add(REQUEST_CODE_RECORDER);
        }
        if (voiceRecord) {
            itemNames.add("录音");
            resourcesIds.add(R.mipmap.luyinex);
            requestCodes.add(REQUEST_CODE_VOICE);
        }

    }

    //这样可以的，但是一旦开始设置的文件过多，就会导致内存溢出。后续修改
    public void setImages(ArrayList<File> list) {
        if (list != null && list.size() > 0) {
            listFiles = list;
            int picW = (int) (Constant.width * 0.25);
            listBitmap.remove(listBitmap.size() - 1);
            RequestOptions options = new RequestOptions();
            options.centerCrop();//CenterCrop：等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。
            options.override(picW, picW);
            options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            for (File file : list) {
                Glide.with(context)
                        .asBitmap()
                        .load(file)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                //先移除加号，然后将新的图片加入到集合，如果已经全部加载了，就刷新GridView
                                //这个图片处理应该使用Glide
                                listBitmap.add(resource);
                                if (listBitmap.size() == listFiles.size()) {
                                    refreshGV();
                                }
                            }
                        });
            }
        }
    }

    public void setVoice(ArrayList<File> list) {
        if (list != null && list.size() > 0) {
            listFiles = list;
            Bitmap bitmap = Utils.getImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.yuyin));
            int size = list.size();
            listBitmap.remove(listBitmap.size() - 1);
            for (int i = 0; i < size; i++) {
                listBitmap.add(bitmap);
            }

            refreshGV();
        }
    }

    public void setVideo(ArrayList<File> list) {
        if (list != null && list.size() > 0) {
            listFiles = list;
            Bitmap bitmap = Utils.getImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.shipin));
            int size = list.size();
            listBitmap.remove(listBitmap.size() - 1);
            for (int i = 0; i < size; i++) {
                listBitmap.add(bitmap);
            }
            refreshGV();
        }
    }

    public void setFile(ArrayList<File> list) {
        if (list != null && list.size() > 0) {
            listFiles = list;
            Bitmap bitmap = Utils.getImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.wenjian));
            int size = list.size();
            listBitmap.remove(listBitmap.size() - 1);
            for (int i = 0; i < size; i++) {
                listBitmap.add(bitmap);
            }
            refreshGV();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == activity.RESULT_OK) {
//            下面这句不能写在这里，必须写在里面，否则，如果从别的页面返回到调用了SelectFileGridViewEx的页面，会导致那个加号被移除
//            listBitmap.remove(listBitmap.size() - 1);
            switch (requestCode) {
                case REQUEST_CODE_PIC:
                    //选择图片
                    listBitmap.remove(listBitmap.size() - 1);
                    List<File> list = (List<File>) intent.getSerializableExtra("list");
                    Log.i(TAG, "总共选中了" + list.size() + "个文件");
                    for (File file : list) {
                        Log.i(TAG, "文件名：" + file.getName());
                        listFiles.add(file);
                        listBitmap.add(Utils.getImageBitmap(BitmapFactory.decodeFile(file.getPath())));
                    }
                    refreshGV();

//                    Uri uri = intent.getData();
//                    String findFilePath = "";
//                    findFilePath = uri.getPath();
//                    if (!findFilePath.contains(".")) {
//                        //根据uri在图片数据库中查到这个图片的绝对地址
//                        Log.e(TAG, "uri.getEncodedPath()=" + findFilePath);
//                        Cursor cursor = activity.getContentResolver().query(uri, null, null,
//                                null, null);
//                        cursor.moveToFirst();
//                        findFilePath = cursor.getString(cursor
//                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
//                        cursor.close();
//                    }
//                    Log.i(TAG, "选择的图片地址：" + findFilePath);
//                    listFiles.add(new File(findFilePath));
//                    listBitmap.add(Utils.getImageBitmap(BitmapFactory.decodeFile(findFilePath)));
//                    refreshGV();
                    break;
                case REQUEST_CODE_CAMERA:
                    //拍照
                    listBitmap.remove(listBitmap.size() - 1);
                    Bundle bundle = intent.getExtras();
                    Bitmap cameraBitmap = (Bitmap) bundle.get("data");
                    File fileImage = new File(Utils.saveBitmapFile(cameraBitmap).getPath());
                    listFiles.add(fileImage);
                    listBitmap.add(Utils.getImageBitmap(cameraBitmap));
                    refreshGV();

                    break;
                case REQUEST_CODE_VIDEO:
                    //选择视频
                    listBitmap.remove(listBitmap.size() - 1);
                    List<File> listVideo = (List<File>) intent.getSerializableExtra("list");
                    List<File> thumbnailList = (List<File>) intent.getSerializableExtra("thumbnailList");
                    for (File file : listVideo) {
                        //添加所有的视频文件到文件集合
                        listFiles.add(file);
                    }
                    Log.i(TAG, "总共选中了" + listVideo.size() + "个文件");
                    for (File file : thumbnailList) {
                        //添加所有的视频缩略图到缩略图集合
                        if (file == null) {
                            listBitmap.add(Utils.getImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.shipinsuoluetu)));
                        } else {
                            listBitmap.add(Utils.getImageBitmap(BitmapFactory.decodeFile(file.getPath())));
                        }
                    }

                    refreshGV();
//                    Uri uriVideo = intent.getData();
//                    String pathVideo = null;
//                    pathVideo = uriVideo.getPath();
//                    Log.i(TAG, "视频uri：" + uriVideo.toString());
//                    Log.i(TAG, "视频文件路径：" + pathVideo);
//                    listFiles.add(new File(pathVideo));
//                    Bitmap bmp = BitmapFactory.decodeResource(getResources(),
//                            R.mipmap.shipin);
//                    listBitmap.add(Utils.getImageBitmap(bmp));
//                    refreshGV();

                    break;
                case REQUEST_CODE_RECORDER:
                    //摄像
                    listBitmap.remove(listBitmap.size() - 1);
                    Uri uriRecorder = intent.getData();
                    String pathRecorder = null;
                    Cursor cursor = activity.getContentResolver().query(uriRecorder, null, null,
                            null, null);
                    cursor.moveToFirst();
                    pathRecorder = cursor.getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

                    Log.i(TAG, "拍摄视频uri：" + uriRecorder.toString());
                    Log.i(TAG, "拍摄视频文件路径：" + pathRecorder);
                    listFiles.add(new File(pathRecorder));
                    Bitmap bmp2 = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.luxiang);
                    listBitmap.add(Utils.getImageBitmap(bmp2));
                    refreshGV();

                    break;
                case REQUEST_CODE_FILE:
                    //文件
                    listBitmap.remove(listBitmap.size() - 1);
                    List<File> fileList = (List<File>) intent.getSerializableExtra("fileList");
                    for (File file : fileList) {
                        listFiles.add(file);
                        Bitmap bmp3 = BitmapFactory.decodeResource(getResources(),
                                R.mipmap.wenjian);
                        listBitmap.add(Utils.getImageBitmap(bmp3));
                    }
                    refreshGV();
//
//                    Uri uriFile = intent.getData();//得到uri，后面就是将uri转化成file的过程。
//
//                    Cursor actualimagecursor = activity.getContentResolver().query(uriFile, null, null,
//                            null, null);
//                    int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    actualimagecursor.moveToFirst();
//                    String img_path = actualimagecursor.getString(actual_image_column_index);
//
//                    File file = new File(img_path);
//                    listFiles.add(file);
//                    Bitmap bmp3 = BitmapFactory.decodeResource(getResources(),
//                            R.mipmap.wenjian);
//                    listBitmap.add(Utils.getImageBitmap(bmp3));
//                    refreshGV();

                    break;
                case REQUEST_CODE_VOICE:
                    listBitmap.remove(listBitmap.size() - 1);
//                    Uri uriVoice = intent.getData();//得到uri，后面就是将uri转化成file的过程。
//                    Cursor voiceCursor = activity.getContentResolver().query(uriVoice, null, null,
//                            null, null);
//                    voiceCursor.moveToFirst();
//                    String voicePath = voiceCursor.getString(voiceCursor
//                            .getColumnIndexOrThrow(Medi aStore.Video.Media.DATA));
//                    listFiles.add(new File(voicePath));
                    listFiles.add((File) intent.getSerializableExtra("voice"));
                    Bitmap bmpVoice = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.yuyin);
                    listBitmap.add(Utils.getImageBitmap(bmpVoice));
                    refreshGV();
                    break;
            }
        }
    }


}
