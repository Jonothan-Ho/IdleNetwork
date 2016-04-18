package com.ipoxiao.idlenetwork.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import java.security.MessageDigest;

/**
 * Created by Administrator on 2015/10/23.
 */
public class TextUtil {

    /**
     * verify user's username and password is valid
     *
     * @return
     */
    public static boolean isValidUser(String username, String passwd, Context rootView) {

        if (!isValidPhone(username,rootView)) {
            return false;
        } else if (!isValidPassword(passwd,rootView)) {
            return false;
        } else {
            return true;
        }

    }


    /**
     * @param phone
     * @param rootView
     * @return
     */
    public static boolean isValidPhone(String phone, Context rootView) {
        if (TextUtils.isEmpty(phone)) {
            ViewUtil.showToast(rootView, "手机号不能为空");
            return false;
        }
        if (!isValidPhone(phone)) {
            ViewUtil.showToast(rootView, "手机号格式错误");
            return false;
        }
        return true;
    }


    /**
     * Verify phone is valid
     *
     * @param phone
     * @return
     */
    public static boolean isValidPhone(String phone) {

        String pattern = "^(13[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$";
        if (phone.matches(pattern)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password, Context rootView) {
        if (TextUtils.isEmpty(password)) {
            ViewUtil.showToast(rootView, "密码不能为空");
            return false;
        }
        if (password.length() <= 6 && password.length() >= 32) {
            ViewUtil.showToast(rootView, "密码必须为6-32位");
            return false;
        }
        return true;
    }


    /**
     * @param buffer
     * @return
     */
    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

}
