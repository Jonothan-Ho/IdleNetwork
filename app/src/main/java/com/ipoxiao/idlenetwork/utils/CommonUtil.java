package com.ipoxiao.idlenetwork.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.easemob.chat.EMChatManager;
import com.ipoxiao.idlenetwork.common.Common;
import com.ipoxiao.idlenetwork.framework.dialog.AppProgressDialog;
import com.ipoxiao.idlenetwork.framework.dialog.OnDialogLauncherAble;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.account.ui.LoginActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2015/11/10.
 */
public class CommonUtil {


    /**
     * call telephone view from system
     *
     * @param telphone
     */
    public static void sendTelephoneNumber(Activity activity, String telphone) {

        if (TextUtils.isEmpty(telphone)) {
            ViewUtil.showToast(activity, "Telephone Number cannot be empty");
        } else if (!TextUtil.isValidPhone(telphone)) {
            ViewUtil.showToast(activity, "Telephone Number format error");
        } else {
            Uri uri = Uri.parse("tel:" + telphone);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(uri);
            activity.startActivity(intent);
        }
    }


    /**
     * @param context
     * @return
     */
    public static OnDialogLauncherAble getProgressDialog(Context context) {
        AppProgressDialog dialog = new AppProgressDialog(context);
        return dialog;
    }


    /**
     * @param msg
     * @return
     */
    public static String getHttpExceptionMessage(String msg) {
        String message = null;
        try {
            message = URLDecoder.decode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return message;
    }


    /**
     * @param context
     * @return
     */
    public static boolean isLogged(Context context,boolean isLogin) {
        PreferencesUtil util = PreferencesUtil.getInstance(context);
        User user = util.get(User.class);
        if (user == null) {
            if (isLogin)
            context.startActivity(new Intent(context, LoginActivity.class));

            return false;
        }
        return true;
    }


    /**
     * @param context
     * @return
     */
    public static boolean isBindWXChat(Context context) {
        User user = PreferencesUtil.getInstance(context).get(User.class);
        return !TextUtils.isEmpty(user.getOpenid());
    }

    /**
     * 设置未读消息
     * @param v
     * @param size
     * @param type
     * @return
     */
    public static void setMsgCount(TextView v, int size,String type){
        if(type=="hx"){
            Common.hx_count = EMChatManager.getInstance().getUnreadMsgsCount();
            size=Common.hx_count;
        }
        if (size > 0 && size < 99) {
            v.setText(size+"");
            v.setVisibility(View.VISIBLE);
        }else if(size>99){
            v.setText("99+");
            v.setVisibility(View.VISIBLE);
        }else {
            v.setVisibility(View.GONE);
        }
    }
}
