package com.ipoxiao.idlenetwork.framework.common;

import android.app.Activity;
import android.content.Context;

import com.easemob.chat.EMChatManager;
import com.ipoxiao.idlenetwork.framework.app.IdleApplication;
import com.ipoxiao.idlenetwork.framework.db.DbUtil;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.common.domain.FriendInfo;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Manage all activity
 * <p/>
 * Created by Administrator on 2015/10/9.
 */
public class ActivityManager {

    private static ActivityManager mInstance;
    private HashMap<String, Activity> mCacheActivity;

    private ActivityManager() {
        mCacheActivity = new HashMap<>();
    }

    public static ActivityManager getInstance() {
        if (mInstance == null) {
            synchronized (ActivityManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityManager();
                }
            }
        }
        return mInstance;
    }

    public HashMap<String, Activity> getmCacheActivity() {
        return mCacheActivity;
    }


    public void clearAll(Context context) {
        Iterator<Map.Entry<String, Activity>> iterator = mCacheActivity.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Activity> entry = iterator.next();
            String key = entry.getKey();
            Activity activity = mCacheActivity.get(key);
            activity.finish();
        }
        mCacheActivity.clear();

        PreferencesUtil.getInstance(context).put(null, User.class);
//        PreferencesUtil.getInstance(context).put("username", "");
        PreferencesUtil.getInstance(context).put("password", "");

        JPushInterface.setAlias(IdleApplication.getInstance(), "", null);
        EMChatManager.getInstance().logout();
       // DbUtil.getInstance().dropTable(FriendInfo.class);
    }

    public void finish(String key) {
        if (mCacheActivity.get(key) != null) {
            mCacheActivity.remove(key).finish();
        }
    }

    public void put(String key, Activity activity) {
        mCacheActivity.put(key, activity);
    }

}
