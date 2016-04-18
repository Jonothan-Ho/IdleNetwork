package com.ipoxiao.idlenetwork.module.chat.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarTwoActivity;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.framework.popup.BasePopupWindow;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.chat.popupwindow.MorePopupwindow;
import com.ipoxiao.idlenetwork.module.common.domain.Dynamic;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IDynamicBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.DynamicBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IDynamicController;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

public class DynamicInfoActivity extends ActionBarTwoActivity implements IDynamicController, OnItemClickListener, BasePopupWindow.OnDismissListener {

    @ViewInject(R.id.webview)
    private WebView mWebView;
    private MorePopupwindow mMorePopupwindow;
    private IDynamicBusiness mDynamicBusiness;
    private String id;


    @Override
    protected int getCustomView() {
        return R.layout.activity_chat_dynamic_info;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initSubView() {
        initActionText("更多");


        mWebView.setWebViewClient(new MyBrowser());
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        Dynamic dynamic = (Dynamic) getIntent().getSerializableExtra("object");
        User user = PreferencesUtil.getInstance(this).get(User.class);
        if (!user.getId().equals(dynamic.getUid())) {
            setActionTextVisibility(View.GONE);
        }

        initTitle(dynamic.getTitle());

        if (dynamic != null) {
            id = dynamic.getId();
            mWebView.loadUrl(dynamic.getLink());
        }


        mMorePopupwindow = new MorePopupwindow(this);
        mMorePopupwindow.setItemClickListener(this);
        mMorePopupwindow.setOnDismissListener(this);

        mDynamicBusiness = new DynamicBusinessImpl(this);

    }

    @Override
    protected void onActionEvent() {
        mMorePopupwindow.showAsDropDown(mTextAction);
        mTextAction.setTextColor(getResources().getColor(R.color.text_grey));
    }

    @Override
    public void onDismiss(PopupWindow popupWindow) {
        mTextAction.setTextColor(getResources().getColor(R.color.text_white));
    }

    @Override
    public void click(View v, int position) {
        mMorePopupwindow.closePopupWindow();
        switch (position) {
            case 0:
                mDynamicBusiness.topDynamic(id);
                break;
            case 1:
                mDynamicBusiness.deleteDynamic(id);
                break;
        }
    }

    @Override
    public void onComplete(Action_Dynamic action, List<Dynamic> dynamics, int page) {

    }

    @Override
    public void onComplete(Action_Dynamic action, boolean isSuccess) {
        if (isSuccess) {
            setResult(RESULT_OK, null);
            finish();
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
