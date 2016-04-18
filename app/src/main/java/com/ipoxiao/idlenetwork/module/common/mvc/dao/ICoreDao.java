package com.ipoxiao.idlenetwork.module.common.mvc.dao;

import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;

import org.xutils.http.RequestParams;

/**
 * {@link com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.CoreDaoImpl}
 * Created by Administrator on 2016/1/6.
 */
public interface ICoreDao extends IBaseDao {

    public static final String URL_SWITCH_CITY = "api/users/switchcity";
    final String URL_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    final String URL_ACCESSTOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?";
    final String URL_BIND = "api/users/wxbind";
    final String URL_WITHDRAW = "api/item/withdrawals";
    final String URL_RECORD = "api/order/withdrawals";

    final String URL_ORDER = "api/order";

    void switchCity();

    void postPay(RequestParams params);

    void postUnifiedorder(RequestParams params);

    void getOpenID(String url);

    void bindOpenID(RequestParams params);

    void postWithdraw(RequestParams params);

    void getWithdrawalRecords(String url);
}
