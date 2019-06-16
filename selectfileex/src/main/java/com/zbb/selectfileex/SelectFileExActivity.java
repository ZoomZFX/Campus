package com.zbb.selectfileex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zbb.selectfileex.adapter.GvPictureAdapter;
import com.zbb.selectfileex.entity.SelectFileExEntity;
import com.zbb.selectfileex.utils.Constant;
import com.zbb.selectfileex.utils.PermissionUtils;
import com.zbb.selectfileex.view.DialogPromptPermission;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择图片或者视频的页面
 */
public class SelectFileExActivity extends AppCompatActivity {

    private final String TAG = "SelectFileExActivity";

    private static final int REQUEST_CODE = 202;

    private GridView gv_selectPic;
    private RelativeLayout rl_back;
    private Button btn_selected;
    private List<SelectFileExEntity> list;
    private int fileType = 0;//0 图片，1视频
    private int maxFileSize = -1;//选择文件的数量限制 -1代表没有限制
    private TextView tv_selectFileEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file_ex);
        Intent intent = getIntent();
        fileType = intent.getIntExtra("fileType", 0);
        maxFileSize = intent.getIntExtra("maxFileSize", -1);
        initUI();
        if (PermissionUtils.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_CODE)) {
            initData();
        }

        gv_selectPic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                if (list.get(i).getFileType() == 1) {
                    intent.setClass(SelectFileExActivity.this, ShowPictureActivity.class);
                } else if (list.get(i).getFileType() == 2) {
                    intent.setClass(SelectFileExActivity.this, PlayActivity.class);
                    intent.putExtra("name", list.get(i).getFile().getName());
                }
                intent.putExtra("path", list.get(i).getFile().getPath());
                startActivity(intent);
            }
        });
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("list", (Serializable) Constant.list);
                intent.putExtra("thumbnailList", (Serializable) Constant.thumbnailList);
                SelectFileExActivity.this.setResult(RESULT_OK, intent);
                SelectFileExActivity.this.finish();
            }
        });
    }

    private void initData() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        Constant.width = metric.widthPixels; // 屏幕宽度（像素）
        Constant.height = metric.heightPixels; // 屏幕高度（像素）
        Constant.list = new ArrayList<File>();
        Constant.thumbnailList = new ArrayList<File>();
        if (fileType == 0) {
            list = getImageList();
        } else if (fileType == 1) {
            list = getVideoList();
        }
        GvPictureAdapter adapter = new GvPictureAdapter(SelectFileExActivity.this, list, btn_selected, maxFileSize);
        gv_selectPic.setAdapter(adapter);
        if (maxFileSize == -1) {
            btn_selected.setText("确定(" + Constant.list.size() + ")");
        } else {
            btn_selected.setText("确定(" + Constant.list.size() + "/" + maxFileSize + ")");
        }
    }

    private void initUI() {
        gv_selectPic = (GridView) findViewById(R.id.gv_select_picture);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        btn_selected = (Button) findViewById(R.id.btn_selected);
        tv_selectFileEx = (TextView) findViewById(R.id.tv_selectFileEx);
        if (fileType == 0) {
            tv_selectFileEx.setText(getResources().getString(R.string.select_picture));
        } else if (fileType == 1) {
            tv_selectFileEx.setText(getResources().getString(R.string.select_video));
        }
    }

    public List<SelectFileExEntity> getImageList() {
        List<SelectFileExEntity> list = new ArrayList<SelectFileExEntity>();
        String[] columns = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
            /*查询文件路径包含上面指定的文件夹路径的图片--这样才能保证查询到的文件属于当前文件夹下*/
//        String whereclause = MediaStore.Images.ImageColumns.DATA + " like'" + folderPath + "/%'";
//        Cursor corsor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, whereclause, null, null);
//        public final Cursor query (Uri uri, String[] projection,String selection,String[] selectionArgs, String sortOrder)
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, MediaStore.Images.Media.DATE_ADDED + " desc");
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        while (cursor.moveToNext()) {
            String path = cursor.getString(columnIndex);
            File pictureTemp = new File(path);
            if (pictureTemp.length() > 0) {//有的文件虽然是图片格式的，但是没有数据，大小为0KB,导致显示出错
                list.add(new SelectFileExEntity(1, pictureTemp, pictureTemp));//我这里没有获取图片的缩略图，直接使用原始图片当作缩略图
            }
        }
        return list;
    }

    public List<SelectFileExEntity> getVideoList() {
        List<SelectFileExEntity> list = new ArrayList<SelectFileExEntity>();
        String[] columns = new String[]{MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, MediaStore.Video.Media.DATE_ADDED + " desc");
        int columnDataIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        int columnIdIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        while (cursor.moveToNext()) {
            String videoPath = cursor.getString(columnDataIndex);//视频文件地址
            Log.i(TAG, "本地视频文件地址：" + videoPath);
            File videoTemp = new File(videoPath);
            if (videoTemp.length() > 0) {//有的文件虽然是视频格式的，但是没有数据，大小为0KB,导致显示出错
                int id = cursor.getInt(columnIdIndex);//视频id
                String[] columnsThumbnails = new String[]{MediaStore.Video.Thumbnails._ID, MediaStore.Video.Thumbnails.DATA};
                Cursor cs = getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, columnsThumbnails, MediaStore.Video.Thumbnails.VIDEO_ID + "=?", new String[]{id + ""}, null);
                Log.i(TAG, "缩略图数量：" + cs.getCount());
                if (cs.getCount() != 0) {
                    cs.moveToFirst();
                    int columnDataIndexThumbnails = cs.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);
                    String thumbnailsPath = cs.getString(columnDataIndexThumbnails);//视频缩略图地址
                    list.add(new SelectFileExEntity(2, videoTemp, new File(thumbnailsPath)));
                } else {
                    list.add(new SelectFileExEntity(2, videoTemp, null));
                }
            }
        }
        return list;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initData();
        } else {
            //用户授权拒绝之后，友情提示一下就可以了
            Log.e(TAG, "权限被拒绝");
            //这里应该弹出dialog详细说明一下
//            Toast.makeText(this, "您拒绝了所需权限的申请，将不能进行操作，请在设置或安全中心开启该项权限后重新操作", Toast.LENGTH_LONG).show();
            DialogPromptPermission dialogPromptPermission= new DialogPromptPermission(this);
            dialogPromptPermission.setPromptText("您拒绝了选择文件所需权限的申请，将不能进行选择文件，请在设置或安全中心开启读写手机储存的权限后重新操作");
            dialogPromptPermission.show();
        }
    }
}
