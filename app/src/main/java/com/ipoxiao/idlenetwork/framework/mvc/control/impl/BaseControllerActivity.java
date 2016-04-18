package com.ipoxiao.idlenetwork.framework.mvc.control.impl;

import android.content.Context;
import android.view.View;

import com.ipoxiao.idlenetwork.framework.activity.BaseActivity;
import com.ipoxiao.idlenetwork.framework.dialog.OnDialogLauncherAble;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.utils.CommonUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;


/**
 * Created by Administrator on 2015/11/20.
 */
public abstract class BaseControllerActivity extends BaseActivity implements IBaseController {


    private OnDialogLauncherAble mProgressDialog;

    @Override
    public View getRootView() {
        return getWindow().getDecorView();
    }

    @Override
    public void openDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = CommonUtil.getProgressDialog(this);
        }
        mProgressDialog.startDialog();

    }

    @Override
    public void closeDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.closeDialog();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}

