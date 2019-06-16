package com.example.campus;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

public class BaseActivity extends AppCompatActivity {

    public RelativeLayout rl_back;
    public Toolbar toolbar;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus();//设置透明状态栏
//        }
//    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        rl_back = $(R.id.rl_back);
        toolbar = $(R.id.toolbar);
        if (rl_back != null) {
            rl_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

//    public void setTranslucentStatus() {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置强制竖屏
//        //由于我这个app最低版本设置了21，所以，代码中设置就么有意义了，直接style-v21就完事了
////        Window win = getWindow();
////        WindowManager.LayoutParams winParams = win.getAttributes();
////        winParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | winParams.flags);
////        win.setAttributes(winParams);
////        win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//    }

    public <T extends View> T $(@IdRes int resId) {
        return (T) super.findViewById(resId);
    }

}
