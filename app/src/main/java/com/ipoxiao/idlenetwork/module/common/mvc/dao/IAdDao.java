package com.ipoxiao.idlenetwork.module.common.mvc.dao;

import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;

/**
 * {@link com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.AdDaoImpl}
 * Created by Administrator on 2016/1/4.
 */
public interface IAdDao extends IBaseDao {

    public static final String URL_AD = "api/item/ad";

    void getAd(String url);
}
