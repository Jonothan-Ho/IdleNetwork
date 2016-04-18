package com.ipoxiao.idlenetwork.module.common.mvc.dao;

import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IDynamicController;

import org.xutils.http.RequestParams;

/**
 * {@link com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.DynamicDaoImpl}
 * Created by Administrator on 2016/1/6.
 */
public interface IDynamicDao extends IBaseDao {

    public static final String URL_DYNAMIC = "api/dynamic";

    void searchDynamic(String url);

    void topDynamic(String url);

    void deleteDynamic(String url);

    void getDynamicList(IDynamicController.Action_Dynamic action,String url);

    void postDynamic(RequestParams params);
}
