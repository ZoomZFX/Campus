package com.zbb.selectfileex;


import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * 图片查看
 */

public class ShowPictureActivity extends Activity {

    private final String TAG = "ShowPictureActivity";

    private ImageView iv_showPicture;

    String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show_picture);
        init();
        filePath = getIntent().getStringExtra("path");

        iv_showPicture.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });


    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (filePath != null) {
            iv_showPicture.setImageBitmap(BitmapFactory.decodeFile(filePath));
        }
    }

    private void init() {
        // TODO Auto-generated method stub
        iv_showPicture = (ImageView) findViewById(R.id.iv_showPicture);
    }

}
