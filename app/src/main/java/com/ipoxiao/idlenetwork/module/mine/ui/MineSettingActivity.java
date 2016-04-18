package com.ipoxiao.idlenetwork.module.mine.ui;


import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.easeui.widget.EaseSwitchButton;
import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.widget.DemoHelper;
import com.ipoxiao.idlenetwork.widget.DemoModel;

import org.xutils.view.annotation.ViewInject;

/**
 * 设置
 */
public class MineSettingActivity extends ActionBarOneActivity implements View.OnClickListener {
    @ViewInject(R.id.switch_notification)
    private EaseSwitchButton mSchBtnNotification;
    @ViewInject(R.id.switch_sound)
    private EaseSwitchButton mSchBtnSound;
    @ViewInject(R.id.switch_vibrate)
    private EaseSwitchButton mSchBtnVibrate;
    @ViewInject(R.id.rl_switch_sound)
    private RelativeLayout mRLayoutSound;
    @ViewInject(R.id.rl_switch_vibrate)
    private RelativeLayout mRLayoutVibrate;
    @ViewInject(R.id.rl_switch_notification)
    private RelativeLayout mRLayoutNotification;
    //    @ViewInject(R.id.tv_notification)
//    private TextView mTextNotification;
    @ViewInject(R.id.tv_sound)
    private TextView mTextSound;
    @ViewInject(R.id.tv_vibrate)
    private TextView mTextVibrate;


    private DemoModel settingsModel;

    @Override
    protected void initSubView() {
        initTitle("设置");
        settingsModel = DemoHelper.getInstance().getModel();
        if (settingsModel.getSettingMsgNotification()) {//是否打开消息提示
            mSchBtnNotification.openSwitch();
            mRLayoutSound.setVisibility(View.VISIBLE);
            mRLayoutVibrate.setVisibility(View.VISIBLE);
            mTextSound.setVisibility(View.VISIBLE);
            mTextVibrate.setVisibility(View.VISIBLE);
        } else {
            mSchBtnNotification.closeSwitch();
            mRLayoutSound.setVisibility(View.GONE);
            mRLayoutVibrate.setVisibility(View.GONE);
            mTextSound.setVisibility(View.GONE);
            mTextVibrate.setVisibility(View.GONE);
        }
        if (settingsModel.getSettingMsgSound()) {//是否打开声音
            mSchBtnSound.openSwitch();
        } else {
            mSchBtnSound.closeSwitch();
        }
        if (settingsModel.getSettingMsgVibrate()) {//是否打开震动
            mSchBtnVibrate.openSwitch();
        } else {
            mSchBtnVibrate.closeSwitch();
        }
    }

    @Override
    protected int getCustomView() {
        return R.layout.activity_mine_settings;
    }

    @Override
    protected void initListener() {
        mRLayoutNotification.setOnClickListener(this);
        mRLayoutSound.setOnClickListener(this);
        mRLayoutVibrate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_switch_notification://消息通知
                if (mSchBtnNotification.isSwitchOpen()) {
                    mSchBtnNotification.closeSwitch();
                    mRLayoutSound.setVisibility(View.GONE);
                    mRLayoutVibrate.setVisibility(View.GONE);
                    mTextSound.setVisibility(View.GONE);
                    mTextVibrate.setVisibility(View.GONE);
                    settingsModel.setSettingMsgNotification(false);
                } else {
                    mSchBtnNotification.openSwitch();
                    mRLayoutSound.setVisibility(View.VISIBLE);
                    mRLayoutVibrate.setVisibility(View.VISIBLE);
                    mTextSound.setVisibility(View.VISIBLE);
                    mTextVibrate.setVisibility(View.VISIBLE);
                    settingsModel.setSettingMsgNotification(true);
                }
                break;
            case R.id.rl_switch_sound://声音
                if (mSchBtnSound.isSwitchOpen()) {
                    mSchBtnSound.closeSwitch();
                    settingsModel.setSettingMsgSound(false);
                } else {
                    mSchBtnSound.openSwitch();
                    settingsModel.setSettingMsgSound(true);
                }
                break;
            case R.id.rl_switch_vibrate://震动
                if (mSchBtnVibrate.isSwitchOpen()) {
                    mSchBtnVibrate.closeSwitch();
                    settingsModel.setSettingMsgVibrate(false);
                } else {
                    mSchBtnVibrate.openSwitch();
                    settingsModel.setSettingMsgVibrate(true);
                }
                break;
        }
    }
}
