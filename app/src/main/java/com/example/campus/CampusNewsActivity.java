package com.example.campus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class CampusNewsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        TextView textView = (TextView) findViewById(R.id.news_1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNewsActivity.this, News1Activity.class);
                startActivity(intent);
            }
        });
        TextView textView2 = (TextView) findViewById(R.id.news_2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNewsActivity.this,News2Activity.class);
                startActivity(intent);
//              MainActivity.this.finish();
            }
        });
        TextView textView3 = (TextView) findViewById(R.id.news_3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNewsActivity.this,News3Activity.class);
                startActivity(intent);
//              MainActivity.this.finish();
            }
        });
        TextView textView4 = (TextView) findViewById(R.id.news_4);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNewsActivity.this,News4Activity.class);
                startActivity(intent);
//              MainActivity.this.finish();
            }
        });
        TextView textView5 = (TextView) findViewById(R.id.news_5);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNewsActivity.this,News5Activity.class);
                startActivity(intent);
//              MainActivity.this.finish();
            }
        });
        TextView textView6 = (TextView) findViewById(R.id.campus_gallery);
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNewsActivity.this,CampusGalleryActivity.class);
                startActivity(intent);
//              MainActivity.this.finish();
            }
        });

//        StatusBarUtils.setColor(this,getResources().getColor(R.color.colorPrimary),0);
//        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
//        mToolbar.inflateMenu(R.menu.setting);
//        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if(item.getItemId() ==R.id.banner_mode){
//                    switchBannerMode();
//                }else if(item.getItemId() == R.id.viewPager_mode){
//                    switchViewPagerMode();
//                }else if(item.getItemId() == R.id.remote_mode){
//                    switchRemoteMode();
//                }else if(item.getItemId() == R.id.mz_mode_not_cover){
//                    switchMZModeNotCover();
//                }
//                return true;
//            }
//        });

        Fragment fragment = MZModeBannerFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.home_container, fragment).commit();
    }

    /**
     * banner模式
     */
//    /**
//     * 普通ViewPager模式
//     */
//    public void switchViewPagerMode(){
//        Fragment fragment = NormalViewPagerFragment.newInstance();
//        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
//    }
//
//    /**
//     * 从网络获取数据
//     */
//    public void switchRemoteMode(){
//        Fragment fragment = RemoteTestFragment.newInstance();
//        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
//    }
//
//    public void switchMZModeNotCover(){
//        Fragment fragment = MZModeNotCoverFragment.newInstance();
//        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
//    }
}
