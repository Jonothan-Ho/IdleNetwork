package com.ipoxiao.idlenetwork.module.common.mvc.business;

import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;

/**
 * {@link com.ipoxiao.idlenetwork.module.common.mvc.business.impl.DynamicBusinessImpl}
 * Created by Administrator on 2016/1/6.
 */
public interface IDynamicBusiness extends IBaseService {

    enum Dynamic_Action {
        me, list_me, list_group, list_dist
    }

    void searchDynamic(String dist, String title,int page);

    void topDynamic(String id);

    void deleteDynamic(String id);

    void getDynamicList(Dynamic_Action action, String gid,String uid,int page);

    void publishDynamic(String gid, String title,String toclick, String remark, String imgpiclist);

}
