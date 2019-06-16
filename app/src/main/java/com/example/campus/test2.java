package com.example.campus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class test2 extends AppCompatActivity {

    private static final String TAG = "WebView";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        mWebView = (WebView) findViewById(R.id.test2);
        mWebView.loadUrl("file:///android_asset/test2.html");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){

        });
    }
}
