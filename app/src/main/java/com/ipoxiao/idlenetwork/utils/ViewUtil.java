package com.ipoxiao.idlenetwork.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.ipoxiao.idlenetwork.framework.common.ActivityManager;
import com.ipoxiao.idlenetwork.module.account.ui.LoginActivity;

/**
 * View Tool
 * Created by Administrator on 2015/10/15.
 */
public class ViewUtil {


    /**
     * Show Text By Snackbar
     *
     * @param rootView
     * @param content
     */
    public static void showSnackbar(View rootView, String content) {
        try {
            if (!TextUtils.isEmpty(content)) {
                Snackbar.make(rootView, content, Snackbar.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }

    }

    /**
     * Show Text By Toast
     *
     * @param context
     * @param content
     */
    public static void showToast(Context context, String content) {
        try {
            if (!TextUtils.isEmpty(content)) {
                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }

    /**
     * change activity alpha
     *
     * @param activity
     * @param alpha
     */
    public static void changeActivityAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = alpha;
        activity.getWindow().setAttributes(params);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public static void dialogShowLogin(final Context context, String str) {
        LogUtil.printf("登录===================================》");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ActivityManager.getInstance().clearAll(context);
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}
