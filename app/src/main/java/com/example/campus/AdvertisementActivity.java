package com.example.campus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class AdvertisementActivity extends Activity {

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AdvertisementActivity.this,MainActivity.class);
                startActivity(intent);
                AdvertisementActivity.this.finish();
            }
        },3000);
    }
}