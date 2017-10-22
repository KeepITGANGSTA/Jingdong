package com.bwie.jingdong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String url = getIntent().getStringExtra("url");
        WebView mw= (WebView) findViewById(R.id.mWebView);
        mw.getSettings().setJavaScriptEnabled(true);
        mw.setWebChromeClient(new WebChromeClient());
        mw.setWebViewClient(new WebViewClient());
        mw.getSettings().setAppCacheEnabled(true);
        mw.loadUrl(url);
    }
}
