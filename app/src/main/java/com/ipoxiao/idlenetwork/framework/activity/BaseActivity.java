package com.ipoxiao.idlenetwork.framework.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ipoxiao.idlenetwork.framework.common.ActivityManager;


/**
 * The base class for all Activity
 * <p/>
 * Created by Administrator on 2015/10/9.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static int JPUSH_COUNT=0;//未读消息数量(极光)
    public static int HX_COUNT=0;//环信未读消息
    /**
     * Manage Activity
     */
    private ActivityManager mActivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        mActivityManager = ActivityManager.getInstance();
        mActivityManager.put(this.getClass().getSimpleName(), this);
        int viewId = getCustomView();
        if (viewId != 0) {
            setContentView(getCustomView());
        }
        initView();
        initListener();
    }


    /**
     * get content view's id
     *
     * @return
     */
    protected abstract int getCustomView();

    /**
     * initialized view
     */
    protected abstract void initView();

    /**
     * initialized listener
     */
    protected abstract void initListener();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityManager.finish(this.getClass().getSimpleName());
    }

}
