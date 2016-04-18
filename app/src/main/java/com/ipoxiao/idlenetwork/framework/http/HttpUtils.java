package com.ipoxiao.idlenetwork.framework.http;

import com.ipoxiao.idlenetwork.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/1/4.
 */
public class HttpUtils {

    //public static final String URL = "http://192.168.1.15/xrw/";
    public static final String URL = "http://119.29.138.173/";
    public static String sToken;


    /**
     * @param url
     * @param callback
     * @return
     */
    public static Callback.Cancelable get(String url, Callback.CommonCallback callback) {
        RequestParams params = new RequestParams(getAbsoluteUrl(url));
        params.addHeader("ACCESSTOKEN", sToken);
        LogUtil.printf("get===>" + params.getUri());
        LogUtil.printf("ACCESSTOKEN===>" + sToken);
        return x.http().get(params, callback);
    }

    /**
     * @param params
     * @param callback
     * @return
     */
    public static Callback.Cancelable get(RequestParams params, Callback.CommonCallback callback) {
        params.addHeader("ACCESSTOKEN", sToken);
        LogUtil.printf("get===>" + params.getUri());
        LogUtil.printf("ACCESSTOKEN===>" + sToken);
        return x.http().get(params, callback);
    }

    /**
     * @param params
     * @param callback
     * @return
     */
    public static Callback.Cancelable post(RequestParams params, Callback.CommonCallback callback) {
        LogUtil.printf("post===>" + params.getUri());
        params.addHeader("ACCESSTOKEN", sToken);
        LogUtil.printf("ACCESSTOKEN===>" + sToken);
        return x.http().post(params, callback);
    }

    /**
     * @param url
     * @param callback
     * @return
     */
    public static Callback.Cancelable put(String url, Callback.CommonCallback callback) {
        RequestParams params = new RequestParams(getAbsoluteUrl(url));
        params.addHeader("ACCESSTOKEN", sToken);
        LogUtil.printf("put===>" + params.getUri());
        LogUtil.printf("ACCESSTOKEN===>" + sToken);
        return x.http().request(HttpMethod.PUT, params, callback);
    }

    /**
     * @param params
     * @param callback
     * @return
     */
    public static Callback.Cancelable put(RequestParams params, Callback.CommonCallback callback) {
        params.addHeader("ACCESSTOKEN", sToken);
        LogUtil.printf("put===>" + params.getUri());
        LogUtil.printf("ACCESSTOKEN===>" + sToken);
        return x.http().request(HttpMethod.PUT, params, callback);
    }


    /**
     * @param params
     * @return
     * @throws Throwable
     */
    public static String getSync(RequestParams params) throws Throwable {
        params.addHeader("ACCESSTOKEN", sToken);
        LogUtil.printf("ACCESSTOKEN===>" + sToken);
        return x.http().getSync(params, String.class);
    }


    /**
     * @param url
     * @param callback
     * @return
     */
    public static Callback.Cancelable delete(String url, Callback.CommonCallback callback) {
        RequestParams params = new RequestParams(getAbsoluteUrl(url));
        params.addHeader("ACCESSTOKEN", sToken);
        LogUtil.printf("delete===>" + params.getUri());
        LogUtil.printf("ACCESSTOKEN===>" + sToken);
        return x.http().request(HttpMethod.DELETE, params, callback);
    }


    /**
     * @param url
     * @return
     */
    public static String getAbsoluteUrl(String url) {
        return URL + url;
    }

}
