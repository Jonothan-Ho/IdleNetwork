package com.ipoxiao.idlenetwork.module.common.mvc.business.impl;

import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IMBusiness;

/**
 * Created by Administrator on 2016/1/14.
 */
public class IMBusinessImpl extends BaseService implements IMBusiness {

    public IMBusinessImpl(IBaseController controller) {
        super(controller);
    }

    @Override
    public void IMLogin(String username, String passwd) {

    }
}
