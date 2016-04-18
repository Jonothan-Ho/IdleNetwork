package com.ipoxiao.idlenetwork.module.common.mvc.dao;

import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/1/19.
 */
public interface IConfigDao extends IBaseDao {

    String URL_CONFIG = "api/users/getconfig";
    String URL_MESSAGE = "api/message";
    String URL_VERSION = "api/app";

    void getConfig(String url);

    void getNotify(String url);

    void updateVersion(RequestParams params);

}
