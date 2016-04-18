package com.ipoxiao.idlenetwork.module.home.mvc.business;

import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;

/**
 * {@link com.ipoxiao.idlenetwork.module.home.mvc.business.impl.CategoryBusinessImpl}
 * Created by Administrator on 2016/1/4.
 */
public interface ICategoryBusiness extends IBaseService {

    public static final String TYPE_GROUP = "1";
    public static final String TYPE_SEARCH = "2";

    void getCategoryList();

    void getChatSessionByID(String cid, String dist, String type,int page);

    void getChatSessionByID(String dist, String type,int page);

    void getChatSessionInfo(String id);

    void postJoinGroup(String id);

    void deleteExitGroup(String id);


}
