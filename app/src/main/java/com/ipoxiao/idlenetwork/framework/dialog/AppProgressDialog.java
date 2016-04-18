package com.ipoxiao.idlenetwork.framework.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Administrator on 2015/11/23.
 */
public class AppProgressDialog extends ProgressDialog implements OnDialogLauncherAble {

    public AppProgressDialog(Context context) {
        super(context);
        setTitle(null);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setMessage("页面正在努力加载中......");
    }

    @Override
    public void startDialog() {
        show();
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
