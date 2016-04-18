package com.ipoxiao.idlenetwork.module.common.mvc.business;

import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;

/**
 * Created by Administrator on 2016/1/19.
 */
public interface IConfigBusiness extends IBaseService {

    void getSystemConfig();

    void getSystemNotify(int page);

    void updateVersion();

}
