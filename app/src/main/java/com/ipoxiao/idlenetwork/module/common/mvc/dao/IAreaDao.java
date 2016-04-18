package com.ipoxiao.idlenetwork.module.common.mvc.dao;

import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;

/**
 * Created by Administrator on 2016/1/5.
 */
public interface IAreaDao extends IBaseDao {

    public static final String URL_AREA = "api/users/getcity";

    void getAreaList(String url);

    void getIndustryList(String url);
}
