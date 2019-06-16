package com.example.campus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Detail6Activity extends AppCompatActivity {

    private static final String TAG = "WebView";
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail6);

        mWebView = (WebView) findViewById(R.id.test2);
        mWebView.loadUrl("file:///android_asset/test2.html");
        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setSupportZoom(true);
//        //设置出现缩放工具
//        mWebView.getSettings().setBuiltInZoomControls(true);
//        //扩大比例的缩放
//        mWebView.getSettings().setUseWideViewPort(true);
//        //js交互
//        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient(){
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}
