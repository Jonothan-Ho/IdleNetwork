package com.ipoxiao.idlenetwork.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2015/11/23.
 */
public class PreferencesUtil {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    private SharedPreferences mPreferences;
    private static PreferencesUtil mInstance;


    private PreferencesUtil() {
    }

    private PreferencesUtil(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * @param context
     * @return
     */
    public static PreferencesUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (PreferencesUtil.class) {
                if (mInstance == null) {
                    mInstance = new PreferencesUtil(context);
                }
            }
        }
        return mInstance;
    }


    /**
     * @param object
     * @param clazz
     * @param <T>
     */
    public <T> void put(T object, Class<T> clazz) {
        String data = JSON.toJSONString(object);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(clazz.getSimpleName(), data);
        editor.commit();
    }

    /**
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(Class<T> clazz) {
        String data = mPreferences.getString(clazz.getSimpleName(), null);
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        return JSON.parseObject(data, clazz);
    }

    /**
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * @param key
     * @return
     */
    public String get(String key) {
        return mPreferences.getString(key, null);
    }


}
