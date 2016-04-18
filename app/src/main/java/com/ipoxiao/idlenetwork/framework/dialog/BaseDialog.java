package com.ipoxiao.idlenetwork.framework.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import com.ipoxiao.idlenetwork.R;


/**
 * this is base of dialog,the app all dialog can extends the base class
 * Created by Administrator on 2015/10/28.
 */
public abstract class BaseDialog extends Dialog implements OnDialogLauncherAble {

    protected Activity activity;

    public BaseDialog(Activity context) {
        super(context, R.style.custom_dialog);
        this.activity = context;
        setContentView(getCustomView());
        initView();
    }

    public abstract int getCustomView();

    public abstract void initView();

    @Override
    public void startDialog() {
        if (!isShowing()) {
            show();
        }
    }

    @Override
    public void closeDialog() {
        if (isShowing()) {
            dismiss();
        }
    }

    @Override
    public boolean isShow() {
        return isShowing();
    }
}
