package com.example.campus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OfficialWeiboActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_tieba);
        mWebView = (WebView) findViewById(R.id.official_tieba);
        //跳转学校官方微博
        mWebView.loadUrl("https://weibo.com/u/5870285581");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){

        });
    }
}
