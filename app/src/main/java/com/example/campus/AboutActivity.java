package com.example.campus;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;


public class AboutActivity extends BaseActivity {

    final String apkUrl = "https://raw.githubusercontent.com/JustinRoom/JSCKit/master/capture/JSCKitDemo.apk";
    TextView tvVersion;
//    ImageView ivQRCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tvVersion = findViewById(R.id.tv_version);
//        ivQRCode = findViewById(R.id.iv_qr_code);
        showVersion();

    }

    private void showVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            tvVersion.setText("version:" + info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
