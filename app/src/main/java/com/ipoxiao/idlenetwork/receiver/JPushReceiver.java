package com.ipoxiao.idlenetwork.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ipoxiao.idlenetwork.common.Common;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.account.mvc.business.IAccountBusiness;
import com.ipoxiao.idlenetwork.module.account.mvc.business.impl.AccountBusinessImpl;
import com.ipoxiao.idlenetwork.module.account.mvc.controller.IAccountController;
import com.ipoxiao.idlenetwork.module.mine.domain.SystemMessage;
import com.ipoxiao.idlenetwork.module.mine.ui.NewFriendActivity;
import com.ipoxiao.idlenetwork.module.mine.ui.SystemMessageActivity;
import com.ipoxiao.idlenetwork.module.red_envelope.ui.SeparateActivity;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.utils.TextUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/1/20.
 */
public class JPushReceiver extends BroadcastReceiver implements IAccountController {


    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Bundle bundle = intent.getExtras();
        LogUtil.printf("[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogUtil.printf("[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtil.printf("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtil.printf("[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            LogUtil.printf("[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            Common.jp_count++;
            context.sendBroadcast(new Intent().setAction("com.byread.dynamic"));
            try {
                JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                int type = json.getInt("type");
                switch (type) {
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        String username = PreferencesUtil.getInstance(context).get("username");
                        String passwd = PreferencesUtil.getInstance(context).get("password");
                        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(passwd)) {
                            IAccountBusiness accountBusiness = new AccountBusinessImpl(this);
                            LogUtil.printf("===========>登录操作");
                            accountBusiness.login(username, passwd);
                        }
                        break;
                }
            } catch (JSONException e) {
                LogUtil.printf("Get message extra JSON error!");
            }


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtil.printf("[MyReceiver] 用户点击打开了通知");

            //go to the activity by action
            goToActivityByType(context, bundle);


            //打开自定义的Activity
          /*  Intent i = new Intent(context, TestActivity.class);
            i.putExtras(bundle);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);*/

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogUtil.printf("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtil.printf("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            LogUtil.printf("[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("key:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("key:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    LogUtil.printf("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtil.printf("Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
       /* if (MainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (null != extraJson && extraJson.length() > 0) {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            context.sendBroadcast(msgIntent);
        }*/
    }


    private void goToActivityByType(Context context, Bundle bundle) {
        try {
            JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
            // int type = json.getInt("type");
            Intent intent = new Intent(context, SystemMessageActivity.class);
            /*switch (type) {
                case 0:
                    //system message
                    intent.setClass(context, SystemMessageActivity.class);
                    break;
                case 1:
                    // friend apply
                    intent.setClass(context, NewFriendActivity.class);
                    break;
                case 2:
                    // friend apply
                    intent.setClass(context, SeparateActivity.class);
                    break;
            }*/

            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);

        } catch (JSONException e) {
            LogUtil.printf("Get message extra JSON error!");
        }
    }


    @Override
    public void onComplete(User user, boolean isSuccess) {
        HttpUtils.sToken = user.getApp_token();
        LogUtil.printf("user===>"+user.toString());
        PreferencesUtil.getInstance(context).put(user, User.class);
    }

    @Override
    public View getRootView() {
        return null;
    }

    @Override
    public void openDialog() {

    }

    @Override
    public void closeDialog() {

    }

    @Override
    public Context getContext() {
        return context;
    }
}
