package com.ipoxiao.idlenetwork.module.home.mvc.dao;

import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;

import org.xutils.http.RequestParams;

/**
 * {@link com.ipoxiao.idlenetwork.module.home.mvc.dao.impl.CategoryDaoImpl}
 * Created by Administrator on 2016/1/4.
 */
public interface ICategoryDao extends IBaseDao {

    public static final String URL_CATEGORY = "api/category/membergroup";
    public static final String URL_CHATSESSION = "api/memberGroup";
    public static final String URL_ADDUSERS = "api/memberGroup/addusers";
    public static final String URL_EXIT = "api/memberGroup/delusers";


    void getCategoryList();

    void getChatSessionByID(String id);

    void getChatSessionInfo(String url);

    void postJoinGroup(RequestParams params);

    void deleteExitGroup(String url);
}
