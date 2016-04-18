package com.ipoxiao.idlenetwork.module.common.ui;


import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.utils.TextUtil;

import org.xutils.view.annotation.ViewInject;

public class WebViewActivity extends ActionBarOneActivity {

    public static final String FLAG_URL = "url";
    public static final String FLAG_TITLE = "title";

    @ViewInject(R.id.webview)
    private WebView mWebView;

    @Override
    protected int getCustomView() {
        return R.layout.activity_common_web_view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initSubView() {
        initTitle("详情");

        mWebView.setWebViewClient(new MyBrowser());
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        String title = getIntent().getStringExtra(FLAG_TITLE);
        if (!TextUtils.isEmpty(title)) {
            initTitle(title);
        }
        String url = getIntent().getStringExtra(FLAG_URL);
        mWebView.loadUrl(url);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
