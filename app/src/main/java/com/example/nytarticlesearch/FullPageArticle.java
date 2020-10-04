package com.example.nytarticlesearch;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class FullPageArticle extends AppCompatActivity {
    private WebView mWvArticle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_page_article);
        mWvArticle = (WebView) findViewById(R.id.wvArticle);
        // Configure related browser settings
        mWvArticle.getSettings().setLoadsImagesAutomatically(true);
        mWvArticle.getSettings().setJavaScriptEnabled(true);
        mWvArticle.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWvArticle.setWebViewClient(new WebViewClient());
        mWvArticle.loadUrl(getIntent().getStringExtra("url"));
    }
}