package com.example.campus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.example.campus.utils.PermissionUtils;
import com.example.campus.view.DialogPromptPermission;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    //    private RollPagerView mRollViewPager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private RelativeLayout rlOption;
    private TextView tvToolbarText;


    private NavigationView navigationView;
    private TextView tvUserName, tvSignature;
    private ImageView rvAvatar;//头像
    private long lastClickTime;

    private final int REQUEST_CODE_LOGIN = 102;
    private final int REQUEST_CODE_UPDATE = 104;
    private final int REQUEST_CODE_PERMISSIONS = 1005;

    private String[] permission_login = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE
    };

    /**
     * 该类是创建activity时生成的，但是不符合一般规范，所以做了修改，只保留了大体界面布局，代码全部修改过。
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this,"35d006560ca5fd4fd1d22e87e939a184");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView) findViewById(R.id.textView1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AllNoticeActivity.class);
                startActivity(intent);
//              MainActivity.this.finish();
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.first);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GalleryActivity.class);
                startActivity(intent);
            }
        });
        ImageView imageView5 = (ImageView) findViewById(R.id.imageView5);
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.user = User.getCurrentUser(User.class);
                if(Constant.user == null){
                    Toast.makeText(MainActivity.this, "未登录，请先登录后再进行查询！", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MainActivity.this, CjQueryActivity.class);
                    startActivity(intent);
                }
            }
        });
        ImageView imageView6 = (ImageView) findViewById(R.id.imageView6);
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ScheduleActivity.class);
                startActivity(intent);
            }
        });
        ImageView imageView7 = (ImageView) findViewById(R.id.imageView7);
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CampusNewsActivity.class);
                startActivity(intent);
            }
        });
        ImageView imageView8 = (ImageView) findViewById(R.id.imageView8);
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CampusNavigationActivity.class);
                startActivity(intent);
            }
        });
        ImageView imageView9 = (ImageView) findViewById(R.id.imageView9);
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SchoolCalendarActivity.class);
                startActivity(intent);
            }
        });

//        public void setpic() {
//            mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);
//            mRollViewPager.setAnimationDurtion(700);
//            mRollViewPager.setAdapter(new TestNormalAdapter());
//            mRollViewPager.setHintView(new ColorPointHintView(context, Color.parseColor("#f0FF4081"), Color.parseColor("#a0ffffff")));
//
//            menuPresenter.setHeadPic();
//        }
//
//        @Override
//        public void setHeadPic(List<PicHeadTip> list) {
//            mRollViewPager.setAdapter(new Test_networkAdapter(list));
//        }
//
//        @Override
//        public void onDestroyView() {
//            super.onDestroyView();
//            ButterKnife.unbind(this);
//        }
//
//
//        private class TestNormalAdapter extends StaticPagerAdapter {
//            private int[] imgs = {
//                    R.mipmap.home_1,
//                    R.mipmap.home_2,
//                    R.mipmap.home_3,
//                    R.mipmap.home_4
//            };
//
//            @Override
//            public View getView(ViewGroup container, final int position) {
//                ImageView view = new ImageView(container.getContext());
//                view.setImageResource(imgs[position]);
//                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                return view;
//            }
//
//            @Override
//            public int getCount() {
//                return imgs.length;
//            }
//        }
//
//        private class Test_networkAdapter extends StaticPagerAdapter {
//            private List<PicHeadTip> list;
//
//            public Test_networkAdapter(List<PicHeadTip> list) {
//                this.list = list;
//            }
//
//            private int[] imgs = {
//                    R.mipmap.home_1,
//                    R.mipmap.home_2,
//                    R.mipmap.home_3,
//                    R.mipmap.home_4,
//                    R.mipmap.home_2,
//                    R.mipmap.home_3
//            };
//
//            @Override
//            public View getView(ViewGroup container, final int position) {
//                ImageView view = new ImageView(container.getContext());
//                Glide.with(context).load(list.get(position).getUrl())
//                        .centerCrop()
//                        .placeholder(imgs[position])
//                        .error(imgs[position])
//                        .crossFade(1000) // 可设置时长，默认“300ms”
//                        .into(view);
//                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                return view;
//            }
//
//            @Override
//            public int getCount() {
//                return list.size() <= 4 ? 4 : list.size();
//            }
//        }


        initUI();
        rvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.user == null) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_LOGIN);
                } else {
                    Intent intent = new Intent(MainActivity.this, MineActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_UPDATE);
                }
            }
        });

        rlOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

        if (PermissionUtils.checkSelfPermission(this, permission_login, REQUEST_CODE_PERMISSIONS)) {
            initData();
        }
    }

    private void initData() {
        File fileBase = new File(Constant.imagePath);
        if (!fileBase.exists()) {
            fileBase.mkdirs();
        }
        //初始化SDK
        Bmob.initialize(this, "35d006560ca5fd4fd1d22e87e939a184");
        setUserInfo();
    }

    private void setUserInfo() {
        Constant.user = User.getCurrentUser(User.class);//取用缓存的数据
        if (Constant.user != null) {
            tvUserName.setText(Constant.user.getUsername());
            String path = AppUtils.getAvatarFilePath();
            File file = new File(path);
            if (file.exists()) {
                setAvatar(path);
            } else {
                //头像文件不存在，有可能是图片被删除了，或者没有设置头像
                BmobFile avatarFile = Constant.user.getAvatar();
                if (avatarFile != null) {//如果是有头像的就下载下来,用于缓存头像
                    avatarFile.download(new File(Constant.imagePath + File.separator + avatarFile.getFilename()), new DownloadFileListener() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                //下载成功
                                setAvatar(s);
                                AppUtils.setAvatarFilePath(s);
                                if (Looper.myLooper() == Looper.getMainLooper()) {
                                    Log.i(TAG, "主线程");
                                } else {
                                    Log.i(TAG, "子线程");
                                }
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
                tvSignature.setText(Constant.user.getSignature());
            }
        } else {
            //使用Glide裁剪出圆形图片，Glide是个不错的图片加载库，感觉挺好用的，推荐。
            setDefaultAvatar();
            tvUserName.setText(R.string.click_user_image);
            tvSignature.setText(R.string.signature);
        }
    }

    private void setDefaultAvatar() {
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        Glide.with(this)
                .load(R.mipmap.wheat)
                .apply(options)
                .into(rvAvatar);
    }

    private void setAvatar(String path) {
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        Glide.with(this)
                .load(path)
                .apply(options)
                .into(rvAvatar);
    }

    private void initUI() {
        rlOption = findViewById(R.id.rl_option);
        drawerLayout = findViewById(R.id.drawer_layout);
        tvToolbarText = findViewById(R.id.tv_toolbarText);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        rvAvatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.rv_avatar);
        tvUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_userName);
        tvSignature = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_signature);
        tvToolbarText.setText(R.string.app_name);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (lastClickTime == 0) {
                lastClickTime = System.currentTimeMillis();
                Toast.makeText(this, R.string.close_click_again, Toast.LENGTH_SHORT).show();
            } else {
                long now = System.currentTimeMillis();
                if (now - lastClickTime < 3000) {
                    super.onBackPressed();
                } else {
                    lastClickTime = now;
                    Toast.makeText(this, R.string.close_click_again, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_LOGIN || requestCode == REQUEST_CODE_UPDATE) {
                setUserInfo();//登录，或修改个人信息后更新数据
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            boolean isOK = true;
            for (int grantResult : grantResults) {
                LogUtils.i(TAG, new Throwable(), "grantResult=" + grantResult);
                if (PackageManager.PERMISSION_GRANTED != grantResult) {
                    LogUtils.i(TAG, new Throwable(), "grantResult=" + (PackageManager.PERMISSION_GRANTED != grantResult));
                    isOK = false;
                    break;
                }
            }
            if (isOK) {
                initData();
            } else {
                // 用户授权拒绝之后，友情提示一下就可以了
                LogUtils.e("Login", new Throwable(), "权限被拒绝");
                // 这里应该弹出dialog详细说明一下
                // Toast.makeText(this,
                // "您拒绝了所需录音权限的申请，将不能进行操作，请在设置或安全中心开启该项权限后重新操作",
                // Toast.LENGTH_LONG).show();
                DialogPromptPermission dialogPromptPermission = new DialogPromptPermission(this);
                dialogPromptPermission.setPromptText("您拒绝了应用所需权限的申请，继续操作将导致部分功能无法正常使用，请在设置或安全中心开启相应的权限后重新操作");
                dialogPromptPermission.show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // Handle the camera action
        if (id == R.id.nav_school_calendar) {
            Intent intent = new Intent(MainActivity.this,SchoolCalendarActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_grade) {
            Intent intent = new Intent(MainActivity.this,CjQueryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_schedule) {
            Intent intent = new Intent(MainActivity.this,ScheduleActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this,CampusNewsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_campus_navigation) {
            Intent intent = new Intent(MainActivity.this,CampusNavigationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_bus) {
            Intent intent = new Intent(MainActivity.this,MapActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_weibo) {
            Intent intent = new Intent(MainActivity.this,OfficialWeiboActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_tieba) {
            Intent intent = new Intent(MainActivity.this,OfficialTiebaActivity.class);
            startActivity(intent);
//            MainActivity.this.finish();
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            logout();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        BmobUser.logOut();   //清除缓存用户对象
        AppUtils.setAvatarFilePath("");
        Constant.user = null;
        tvSignature.setText(R.string.signature);
        tvUserName.setText(R.string.click_user_image);
        setDefaultAvatar();
    }

    public void diwo(View v){
        Intent intent = new Intent(MainActivity.this, CjQueryActivity.class);
        startActivity(intent);
    }

    private void show(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }


//    public void AddPointer(View view) {
//            User user = new User();
//            user.setUsername("15101236");
//            Grade studentid = new Grade();
//            studentid.setObjectId("uUACFFFj");
//            user.setGrade(studentid);
//            user.save(new SaveListener<String>() {
//                @Override
//                public void done(String s, BmobException e) {
//                    if(e==null){
//                        show(s);
//                    }
//                }
//            });
//            System.out.println("image click");
//        }
}
