package com.ipoxiao.idlenetwork.module.common.mvc.business;

import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;

/**
 * Created by Administrator on 2016/1/14.
 */
public interface IMBusiness extends IBaseService{

    void IMLogin(String username, String passwd);
}
