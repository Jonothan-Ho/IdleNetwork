package com.ipoxiao.idlenetwork.module.common.mvc.business.impl;

import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IAdBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IAdDao;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.AdDaoImpl;

/**
 * Created by Administrator on 2016/1/4.
 */
public class AdBusinessImpl extends BaseService implements IAdBusiness {

    private IAdDao dao;

    public AdBusinessImpl(IBaseController controller) {
        super(controller);
        dao = new AdDaoImpl(controller);
    }

    @Override
    public void getAd(String dist) {
        StringBuffer buffer = new StringBuffer(IAdDao.URL_AD);
        buffer.append("?dist=").append(dist);
        dao.getAd(buffer.toString());
    }
}
