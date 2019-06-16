package com.example.campus;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.campus.bean.User;
import com.example.campus.utils.AppUtils;
import com.example.campus.utils.Constant;
import com.example.campus.utils.LogUtils;
import com.example.campus.utils.UiTools;
import com.yalantis.ucrop.UCrop;
import com.zbb.selectfileex.SelectFileExActivity;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MineActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MineActivity";

    TextView tvToolbarText;
    RelativeLayout rlSelectItem;
    ImageView rvUserImage;
    RelativeLayout rlUserImage;
//    TextView tvUserId;
    TextView tvUserNickName;
    RelativeLayout rlUserNickName;
    TextView tvUserSex;
    RelativeLayout rlUserSex;
    TextView tvUserSignature;
    RelativeLayout rlUserSignature;
    TextView tvUserLevel;
    TextView tvUserPro;
    TextView tvUserRegisterDate;

    private final int REQUEST_CODE_SELECT_FILE = 105;
    private BmobFile bfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        initUI();
        initData();
    }

    private void initUI() {
        tvToolbarText = findViewById(R.id.tv_toolbarText);
        rlSelectItem = findViewById(R.id.rl_selectItem);
        rlUserImage = (RelativeLayout) findViewById(R.id.rl_userImage);
        rvUserImage = (ImageView) findViewById(R.id.rv_userImage);
//        tvUserId = (TextView) findViewById(R.id.tv_userId);
        rlUserNickName = (RelativeLayout) findViewById(R.id.rl_userNickName);
        tvUserNickName = (TextView) findViewById(R.id.tv_userNickName);
//        tvUserLevel = (TextView) findViewById(R.id.tv_userLevel);
//        tvUserPro = (TextView) findViewById(R.id.tv_userPro);
        rlUserSex = (RelativeLayout) findViewById(R.id.rl_userSex);
        tvUserSex = (TextView) findViewById(R.id.tv_userSex);
        rlUserSignature = (RelativeLayout) findViewById(R.id.rl_userSignature);
        tvUserSignature = (TextView) findViewById(R.id.tv_userSignature);
        tvUserRegisterDate = (TextView) findViewById(R.id.tv_userRegisterDate);

        tvToolbarText.setText(getString(R.string.mine));
        AppCompatImageView imageView = new AppCompatImageView(this);
        imageView.setImageResource(R.drawable.ic_done_white_24dp);
        rlSelectItem.addView(imageView);

        rlSelectItem.setOnClickListener(this);
        rlUserImage.setOnClickListener(this);
        rlUserNickName.setOnClickListener(this);
        rlUserSex.setOnClickListener(this);
        rlUserSignature.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_selectItem:
                UiTools.showSimpleLD(this, R.string.loading);
                if (bfile != null) {
                    bfile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {//上传文件成功
                                updateUserData();
                            } else {
                                Toast.makeText(MineActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onProgress(Integer value) {
                            super.onProgress(value);
                        }
                    });
                } else {
                    updateUserData();
                }
                break;
            case R.id.rl_userImage:
                /**
                 * 下面这个是调用系统相册后返回的uri,来选择图片,onActivityResult中要与之对应
                 */
//                Intent choosePicIntent = new Intent(Intent.ACTION_PICK, null);
//                choosePicIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(choosePicIntent, REQUEST_CODE_SELECT_FILE);
                /**
                 * 下面这个是调用自定义图片选择器选择的,onActivityResult中要与之对应
                 */
                Intent intent = new Intent(MineActivity.this, SelectFileExActivity.class);
                intent.putExtra("fileType", 0);
                intent.putExtra("maxFileSize", 1);
                startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
                break;
//            case R.id.rl_userNickName:
//                editUserName();
//                break;
            case R.id.rl_userSex:
                editSex();
                break;
            case R.id.rl_userSignature:
                editSignature();
                break;
        }
    }


    private void editSex() {
        AlertDialog.Builder adb = new AlertDialog.Builder(MineActivity.this);
        adb.setTitle("选择");
        final String[] items = {"男生", "女生","保密"};
        adb.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int index) {
                tvUserSex.setText(items[index]);
            }
        });
        adb.show();
    }



//    private void editUserName() {
//        AlertDialog.Builder adb = new AlertDialog.Builder(MineActivity.this);
//        adb.setTitle(R.string.user_name);
//        final EditText et = new EditText(MineActivity.this);
//        if (Constant.user.getSignature() != null) {
//            et.setText(Constant.user.getUsername());
//        }
//        adb.setView(et);
//        adb.setNegativeButton(R.string.cancel,
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface arg0,
//                                        int arg1) {
//                        arg0.cancel();
//                    }
//                });
//        adb.setPositiveButton(R.string.comfirm,
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface arg0,
//                                        int arg1) {
//                        tvUserNickName.setText(et.getText().toString());
//                    }
//                });
//        adb.show();
//    }

    private void editSignature() {
        AlertDialog.Builder adb = new AlertDialog.Builder(MineActivity.this);
        adb.setTitle(R.string.user_signature);
        final EditText et = new EditText(MineActivity.this);
        if (Constant.user.getSignature() != null) {
            et.setText(Constant.user.getSignature());
        }
        adb.setView(et);
        adb.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0,
                                        int arg1) {
                        arg0.cancel();
                    }
                });
        adb.setPositiveButton(R.string.comfirm,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0,
                                        int arg1) {
                        tvUserSignature.setText(et.getText().toString());
                    }
                });
        adb.show();
    }

    //初始化用户数据
    private void initData() {
//        tvUserId.setText(Constant.user.getObjectId());
        tvUserNickName.setText(Constant.user.getUsername());
        tvUserSignature.setText(Constant.user.getSignature());
//        Integer level = Constant.user.getLevel();
//        if (level == null) {
//            level = 0;
//            Constant.user.setLevel(0);
//        }
//        tvUserLevel.setText("Lv" + level);
//        tvUserPro.setText(Constant.user.getPronoun());
        tvUserRegisterDate.setText(Constant.user.getCreatedAt());
        File file = new File(AppUtils.getAvatarFilePath());
        if (file.exists()) {
            setAvatar(AppUtils.getAvatarFilePath());
        } else {
            //头像文件不存在，有可能是图片被删除了，或者没有设置头像
            BmobFile avatarFile = Constant.user.getAvatar();
            if (avatarFile != null) {//如果是有头像的就下载下来
                avatarFile.download(new File(Constant.imagePath + File.separator + avatarFile.getFilename()), new DownloadFileListener() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            //下载成功
                            setAvatar(s);
                            AppUtils.setAvatarFilePath(s);
                        }
                    }

                    @Override
                    public void onProgress(Integer integer, long l) {

                    }
                });
            } else {
                setDefaultAvatar();
            }
        }
        if (Constant.user.getSignature() != null && !Constant.user.getSignature().equals("")) {
            tvUserSignature.setText(Constant.user.getSignature());
        }
        if (Constant.user.getSex() != null) {
            tvUserSex.setText(Constant.user.getSex());
        }
    }

    private void updateUserData() {
        String userName = tvUserNickName.getText().toString();
        String userSex = tvUserSex.getText().toString();
        String userSignature = tvUserSignature.getText().toString();
        User user = new User();
        user.setUsername(userName);
        user.setSex(userSex);
        user.setSignature(userSignature);
        if (bfile != null) {
            user.setAvatar(bfile);
        }
        user.update(Constant.user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                UiTools.closeSimpleLD();
                if (e == null) {
                    if (bfile != null) {
                        AppUtils.setAvatarFilePath("");
                    }
                    Toast.makeText(MineActivity.this, R.string.update_complete, Toast.LENGTH_SHORT).show();
                    MineActivity.this.setResult(RESULT_OK);
                    MineActivity.this.finish();
                } else {
                    LogUtils.e(TAG, new Throwable(), e.getErrorCode() + "：" + e.getMessage());
                    Toast.makeText(MineActivity.this, R.string.update_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_SELECT_FILE) {

                /**
                 * 请注意， File fileTemp = new File(Constant.basePath + File.separator + "temp.png"  );这句中，Constant.basePath 该目录必须是存在的，否则
                 * 会导致  UCrop.of(uri, Uri.fromFile(fileTemp))后，进行剪裁后，出现E/ANDR-PERF-MPCTL: Invalid profile no. 0, total profiles 0 only
                 * 的错误，该错误仅有一行，不注意的话很难发现。
                 */

                /**
                 * 下面这个是调用系统相册后返回的uri,来选择图片
                 */
//                Uri uri= intent.getData();
//                if (uri != null) {
//                    File fileTemp = new File(Constant.basePath + File.separator + "temp.png"  );
//                    if (fileTemp.exists()) {
//                        fileTemp.delete();
//                    }
//                    UCrop.of(uri, Uri.fromFile(fileTemp))
//                            .withAspectRatio(1, 1)
//                            .withMaxResultSize(512, 512)
//                            .start(this);
//                }

                /**
                 * 下面这个是调用自定义图片选择器来选择的。
                 */
                List<File> list = (List<File>) intent.getSerializableExtra("list");
                if (list.size() > 0) {
                    File selectFile = list.get(0);
                    Uri uri = null;
                    uri = Uri.fromFile(selectFile);
                    if (uri != null) {
                        File fileTemp = new File(Constant.basePath + File.separator + "temp" + selectFile.getName());
                        if (fileTemp.exists()) {
                            fileTemp.delete();
                        }
                        UCrop.of(uri, Uri.fromFile(fileTemp))
                                .withAspectRatio(1, 1)
                                .withMaxResultSize(512, 512)
                                .start(this);
                    }
                }
            }
            if (requestCode == UCrop.REQUEST_CROP) {
                Log.i(TAG, "处理完成");
                Uri resultUri = UCrop.getOutput(intent);
                //这里的resultUri.getPath()获取到的是图片的绝对路径
                Log.e(TAG, "resultUri.getPath()=" + resultUri.getPath());
                bfile = new BmobFile(new File(resultUri.getPath()));
                setAvatar(resultUri.getPath());
            } else if (resultCode == UCrop.RESULT_ERROR) {
                Throwable cropError = UCrop.getError(intent);
                LogUtils.e(TAG, new Throwable(), "剪裁错误：" + cropError.getMessage());
            }
        }
    }

    private void setDefaultAvatar() {
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        Glide.with(this)
                .load(R.mipmap.wheat)
                .apply(options)
                .into(rvUserImage);
    }

    private void setAvatar(String path) {
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        Glide.with(this)
                .load(path)
                .apply(options)
                .into(rvUserImage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bfile != null) {
            bfile.getLocalFile().delete();
        }
    }


}
