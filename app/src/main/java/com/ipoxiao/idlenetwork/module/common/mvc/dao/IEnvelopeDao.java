package com.ipoxiao.idlenetwork.module.common.mvc.dao;

import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/1/7.
 */
public interface IEnvelopeDao extends IBaseDao {

    static final String URL_ENVELOPE = "api/redpaper";
    static final String URL_RECEIVE = "api/redpaper/receive";
    static final String URL_RECORD = "/api/redpaper/log";


    void getEnvelopeList(String url);

    void sendEnvelope(RequestParams params);

    void receiveEnvelope(RequestParams params);

    void getEnvelopeRecord(String url);

}
