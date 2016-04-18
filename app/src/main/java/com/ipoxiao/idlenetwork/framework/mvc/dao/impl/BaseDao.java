package com.ipoxiao.idlenetwork.framework.mvc.dao.impl;


import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;


/**
 * Created by Administrator on 2015/10/21.
 */
public class BaseDao implements IBaseDao {

    protected IBaseController mBaseController;

    public BaseDao(IBaseController baseController) {
        this.mBaseController = baseController;
    }
}
