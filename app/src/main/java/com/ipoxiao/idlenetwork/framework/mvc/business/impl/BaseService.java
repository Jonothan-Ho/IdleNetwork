package com.ipoxiao.idlenetwork.framework.mvc.business.impl;


import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;


/**
 * Created by Administrator on 2015/10/21.
 */
public class BaseService implements IBaseService {

    protected IBaseController mController;

    public BaseService(IBaseController controller) {
        this.mController = controller;
    }


}
