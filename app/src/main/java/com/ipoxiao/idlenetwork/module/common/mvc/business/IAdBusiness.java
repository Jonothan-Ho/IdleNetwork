package com.ipoxiao.idlenetwork.module.common.mvc.business;

import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;

/**
 * {@link com.ipoxiao.idlenetwork.module.common.mvc.business.impl.AdBusinessImpl}
 * Created by Administrator on 2016/1/4.
 */
public interface IAdBusiness extends IBaseService {

    void getAd(String dist);
}
