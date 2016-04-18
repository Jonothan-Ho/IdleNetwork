package com.ipoxiao.idlenetwork.module.account.mvc.dao;

import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/1/5.
 */
public interface IAccountDao extends IBaseDao {

    public static final String URL_REGIST = "api/users/register";
    public static final String URL_LOGIN = "api/users/login";
    public static final String URL_MODIFY = "api/users/updatepwd";
    public static final String URL_USER = "api/users";

    void loginRegister(RequestParams params);

    void modify(RequestParams params);

    void updateUser(RequestParams params);

}
