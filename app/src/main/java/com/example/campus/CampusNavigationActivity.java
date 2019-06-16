package com.example.campus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class CampusNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_navigation);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        //6号楼
        ImageView imageView6 = (ImageView) findViewById(R.id.mark_6);
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNavigationActivity.this,Detail6Activity.class);
                startActivity(intent);
            }
        });
        //7号楼 医务室
        ImageView imageView7 = (ImageView) findViewById(R.id.mark_7);
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNavigationActivity.this,Detail7Activity.class);
                startActivity(intent);
            }
        });
        //4号楼 水卡充值处
        ImageView imageView8 = (ImageView) findViewById(R.id.mark_8);
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNavigationActivity.this,Detail8Activity.class);
                startActivity(intent);
            }
        });
        //2号楼 校园便利店
        ImageView imageView9 = (ImageView) findViewById(R.id.mark_9);
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNavigationActivity.this,Detail9Activity.class);
                startActivity(intent);
            }
        });
        //食堂
        ImageView imageView10 = (ImageView) findViewById(R.id.mark_10);
        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNavigationActivity.this,Detail10Activity.class);
                startActivity(intent);
            }
        });
        //教学楼
        ImageView imageView11 = (ImageView) findViewById(R.id.mark_11);
        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNavigationActivity.this,Detail11Activity.class);
                startActivity(intent);
            }
        });
        //校门
        ImageView imageView12 = (ImageView) findViewById(R.id.mark_12);
        imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusNavigationActivity.this,Detail12Activity.class);
                startActivity(intent);
            }
        });
    }
}