package com.ipoxiao.idlenetwork.framework.mvc.control.impl;

import android.content.Context;
import android.view.View;

import com.ipoxiao.idlenetwork.framework.dialog.OnDialogLauncherAble;
import com.ipoxiao.idlenetwork.framework.fragment.BaseFragment;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.utils.CommonUtil;


/**
 * Created by Administrator on 2015/11/25.
 */
public abstract class BaseControllerFragment extends BaseFragment implements IBaseController {

    private OnDialogLauncherAble mProgressDialog;

    @Override
    public View getRootView() {
        return getActivity().getWindow().getDecorView();
    }

    @Override
    public void openDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = CommonUtil.getProgressDialog(getActivity());
        }
        mProgressDialog.startDialog();
    }

    @Override
    public void closeDialog() {
        mProgressDialog.closeDialog();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
