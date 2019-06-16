package com.example.campus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class News3Activity extends AppCompatActivity {

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news3);
        mWebView = (WebView) findViewById(R.id.news_3);
        //跳转学校官网
        mWebView.loadUrl("http://www.tianshi.edu.cn/contents/161/8790.html");
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setSupportZoom(true);
        //设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //js交互
        mWebView.getSettings().setLoadWithOverviewMode(true);


        mWebView.setWebViewClient(new WebViewClient(){
        });
    }
}