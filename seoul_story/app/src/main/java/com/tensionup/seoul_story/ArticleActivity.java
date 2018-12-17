package com.tensionup.seoul_story;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class ArticleActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        String hrefStr;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                hrefStr= null;
            } else {
                hrefStr= extras.getString("ARTICLE_HREF");
            }
        } else {
            hrefStr= (String) savedInstanceState.getSerializable("ARTICLE_HREF");
        }

        WebView webView = findViewById(R.id.article_webview);
        webView.loadUrl(hrefStr);

    }
}
