package com.ipoxiao.idlenetwork.module.mine.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.common.Common;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.framework.common.ActivityManager;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.common.domain.SystemConfig;
import com.ipoxiao.idlenetwork.module.common.ui.WebViewActivity;

import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.utils.CommonUtil;

import com.ipoxiao.idlenetwork.utils.PreferencesUtil;

import com.ipoxiao.idlenetwork.view.CircleImageView;
import com.ipoxiao.idlenetwork.widget.Constant;

import org.xutils.view.annotation.ViewInject;

public class MineActivity extends ActionBarOneActivity implements View.OnClickListener {

    @ViewInject(R.id.layout_data)
    private RelativeLayout mLayoutData;
    @ViewInject(R.id.iv_portrait)
    private CircleImageView mImgPortrait;

    @ViewInject(R.id.tv_pay)
    private TextView mTextPay;
    @ViewInject(R.id.tv_withdraw)
    private TextView mTextWithdraw;
    @ViewInject(R.id.tv_grant)
    private TextView mTextGrant;
    @ViewInject(R.id.tv_message)
    private TextView mTextMessage;
    @ViewInject(R.id.tv_sys_message)
    private TextView mTextSysMessage;
    @ViewInject(R.id.tv_friend)
    private TextView mTextFriend;
    @ViewInject(R.id.tv_help)
    private TextView mTextHelp;
    @ViewInject(R.id.s_tv_red)
    private TextView sTextMsg;
    @ViewInject(R.id.m_tv_red)
    private TextView mTextMsg;
    @ViewInject(R.id.tv_setting)
    private TextView mTextSetting;
    @ViewInject(R.id.btn_exit)
    private Button mBtnExit;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    @Override
    protected int getCustomView() {
        return R.layout.activity_mine_main;
    }

    @Override
    protected void initListener() {
        mLayoutData.setOnClickListener(this);
        mBtnExit.setOnClickListener(this);
        mTextFriend.setOnClickListener(this);
        mTextPay.setOnClickListener(this);
        mTextGrant.setOnClickListener(this);
        mTextHelp.setOnClickListener(this);
        mTextWithdraw.setOnClickListener(this);
        mTextMessage.setOnClickListener(this);
        mTextSysMessage.setOnClickListener(this);
        mTextSetting.setOnClickListener(this);
    }

    @Override
    protected void initSubView() {
        initTitle("个人中心");
        CommonUtil.setMsgCount(mTextMsg, Common.hx_count, "hx");
        CommonUtil.setMsgCount(sTextMsg, Common.jp_count, "");
        registerBroadcastReceiver();
    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//        IntentFilter filter_dynamic = new IntentFilter();
//        filter_dynamic.addAction("com.byread.dynamic");
//        registerReceiver(mReceiver, filter_dynamic);
//    }
//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            CommonUtil.setMsgCount(mTextMsg, Common.hx_count, "hx");
//           CommonUtil.setMsgCount(sTextMsg, Common.jp_count, "");
//        }
//    };
    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                CommonUtil.setMsgCount(mTextMsg, Common.hx_count, "hx");
                CommonUtil.setMsgCount(sTextMsg, Common.jp_count, "");

            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        if (broadcastReceiver != null) {
            broadcastManager.unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = PreferencesUtil.getInstance(this).get(User.class);
        BitmapUtil.displayImage(mImgPortrait, user.getHead_link());
        CommonUtil.setMsgCount(mTextMsg, Common.hx_count, "hx");
        CommonUtil.setMsgCount(sTextMsg, Common.jp_count, "");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.layout_data:
                intent.setClass(this, MineDataActivity.class);
                break;
            case R.id.btn_exit:
                PreferencesUtil.getInstance(this).put(null, User.class);
                ActivityManager.getInstance().clearAll(this);
                return;
            case R.id.tv_friend:
                intent.setClass(this, FriendActivity.class);
                break;
            case R.id.tv_pay:
                intent.setClass(this, PayActivity.class);
                break;
            case R.id.tv_grant:
                intent.setClass(this, GrantEnvelopeActivity.class);
                break;
            case R.id.tv_help:
                SystemConfig config = PreferencesUtil.getInstance(this).get(SystemConfig.class);
                Intent helpIntent = new Intent(this, WebViewActivity.class);
                helpIntent.putExtra(WebViewActivity.FLAG_URL, config.getApp_help());
                helpIntent.putExtra(WebViewActivity.FLAG_TITLE, "使用帮助");
                startActivity(helpIntent);
                return;
            case R.id.tv_withdraw:
                intent.setClass(this, WithdrawActivity.class);
                break;
            case R.id.tv_message://我的消息
                intent.setClass(this, MyMessageActivity.class);
                break;
            case R.id.tv_sys_message://系统消息
                intent.setClass(this, SystemMessageActivity.class);
                break;
            case R.id.tv_setting://设置
                intent.setClass(this, MineSettingActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }
}
