package com.ipoxiao.idlenetwork.module.common.mvc.business;

import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;

import org.xutils.http.RequestParams;

/**
 * {@link com.ipoxiao.idlenetwork.module.common.mvc.business.impl.CoreBusinessImpl}
 * Created by Administrator on 2016/1/6.
 */
public interface ICoreBusiness extends IBaseService {

    final String TYPE_TIMES = "3";
    final String TYPE_VIP = "2";

    void switchCity();

    void postPay(String type, String args);

    void postUnifiedorder(RequestParams params);

    void getWXChatOpenID(String code);

    void bindOpenID(String openID);

    void postWithDraw(String money);

    void getWithDrawalRecords(int page);

}
