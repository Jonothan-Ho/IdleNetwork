package com.ipoxiao.idlenetwork.framework.activity;


import com.ipoxiao.idlenetwork.framework.mvc.control.impl.BaseControllerActivity;

import org.xutils.x;

/**
 * without title bar of super class activity extends this activity
 * Created by Administrator on 2015/10/9.
 */
public abstract class NoBarActivity extends BaseControllerActivity {

    @Override
    protected void initView() {
        x.view().inject(this);
        initSubView();
    }

    protected abstract void initSubView();
    protected static  boolean IS_RED=false;//是否有红包
}
