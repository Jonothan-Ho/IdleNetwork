package com.ipoxiao.idlenetwork.module.common.mvc.dao;

import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/1/5.
 */
public interface IValidateCodeDao extends IBaseDao {

    public static final String URL_CODE = "api/users/sendcode";

    void getCode(RequestParams params);
}
