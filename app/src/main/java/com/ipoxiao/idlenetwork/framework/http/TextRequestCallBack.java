package com.ipoxiao.idlenetwork.framework.http;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.ipoxiao.idlenetwork.framework.app.IdleApplication;
import com.ipoxiao.idlenetwork.framework.common.ActivityManager;
import com.ipoxiao.idlenetwork.module.account.ui.LoginActivity;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.app.InterceptRequestListener;
import org.xutils.http.request.UriRequest;

import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 */
public abstract class TextRequestCallBack implements Callback.CommonCallback<String>, InterceptRequestListener {

    public static final String TOTAL_COUNT = "X-Pagination-Total-Count";
    public static final String CURRENT_PAGE = "X-Pagination-Current-Page";

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    public static final String METHOD_NULL = "NULL";

    private String mCurrentMethod;
    private String mTotalCount;
    private int mCurrentPage;
    private Context context;
    private int statusCode;

    public TextRequestCallBack() {
        context = IdleApplication.getInstance();
    }

    @Override
    public void onSuccess(String result) {

        LogUtil.printf("===>json==" + result);
        Domain domain;
        switch (mCurrentMethod) {
            case METHOD_POST:
            case METHOD_DELETE:
            case METHOD_PUT:
                domain = getPostDomain(result);
                break;
            case METHOD_GET:
                domain = getGetDomain(result);
                domain.setTotalCount(mTotalCount);
                domain.setPage(mCurrentPage);
                break;
            case METHOD_NULL:
                domain = new Domain();
                domain.setData(result);
                break;
            default:
                domain = new Domain();
                domain.setIsSuccess(false);
                domain.setMsg("服务器未返回请求方法");
                break;
        }

        if (domain != null) {
            domain.setStatus(statusCode);
        }

        boolean isRun = isRun(domain);
        if (isRun) {
            onResult(domain);
        }

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtil.printf("===>Exception==>" + ex.getMessage());
        failure(ex.getMessage());
    }

    @Override
    public void onCancelled(CancelledException cex) {
        failure("已经取消网络连接");
    }

    @Override
    public void onFinished() {

    }

    @Override
    public void beforeRequest(UriRequest request) throws Throwable {
    }

    @Override
    public void afterRequest(UriRequest request) throws Throwable {
        statusCode = request.getResponseCode();
        mCurrentMethod = request.getParams().getMethod().toString();
        List<KeyValue> values = request.getParams().getParams(METHOD_NULL);
        if (values != null && values.size() > 0) {
            KeyValue values1 = values.get(0);
            String key = values1.key;
            String value = (String) values1.value;
            if (METHOD_NULL.equals(key) && METHOD_NULL.equals(value)) {
                mCurrentMethod = METHOD_NULL;
            }
        }
        mTotalCount = request.getResponseHeader(TOTAL_COUNT);
        try {
            mCurrentPage = Integer.parseInt(request.getResponseHeader(CURRENT_PAGE));
        } catch (Exception e) {
            mCurrentPage = 1;
        }

    }


    /**
     * @param message
     */
    private void failure(String message) {
        Domain domain = new Domain();
        domain.setIsSuccess(false);
        domain.setMsg(message);
        domain.setStatus(statusCode);
        boolean isRun = isRun(domain);
        if (isRun) {
            onResult(domain);
        }
    }

    /**
     * @param domain
     */
    public abstract void onResult(Domain domain);


    /**
     * @param text
     */
    private Domain getGetDomain(String text) {
        Domain domain = null;
        try {
            domain = new Domain();
            domain.setIsSuccess(true);
            domain.setData(text);
        } catch (Exception e) {
            if (domain == null) {
                domain = new Domain();
            }
            domain.setIsSuccess(false);
            domain.setMsg("服务器异常JSON");
        }
        return domain;
    }

    /**
     * @param text
     * @return
     */
    private Domain getPostDomain(String text) {
        Domain domain = null;
        try {
            domain = JSONObject.parseObject(text, Domain.class);
            domain.setIsSuccess(true);
        } catch (Exception e) {
            if (domain == null) {
                domain = new Domain();
            }
            domain.setIsSuccess(false);
            domain.setMsg("服务器异常JSON");
        }
        return domain;
    }


    /**
     * @return
     */
    private boolean isRun(Domain domain) {
        switch (domain.getStatus()) {
            case 401:
                ViewUtil.showToast(context, domain.getMsg());
                ActivityManager.getInstance().clearAll(context);
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return false;
        }

        return true;
    }

}

