package com.example.campus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CampusGalleryActivity extends AppCompatActivity {

    private static final String TAG = "WebView";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_gallery);

        mWebView = (WebView) findViewById(R.id.campus_gallery);
        mWebView.loadUrl("file:///android_asset/test.html");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){

        });
    }
}
