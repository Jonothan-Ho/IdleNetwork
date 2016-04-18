package com.ipoxiao.idlenetwork.module.common.mvc.business;

import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;

/**
 * Created by Administrator on 2016/1/5.
 */
public interface IAreaBusiness extends IBaseService {


    void getAreaListByID(String id,String name);

    void getIndustryByDist(String id,int page);

    void getAreaListByDefault();

}
