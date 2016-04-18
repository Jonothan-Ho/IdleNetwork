package com.ipoxiao.idlenetwork.module.common.mvc.business.impl;

import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IAreaBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IAreaDao;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.AreaDaoImpl;
import com.ipoxiao.idlenetwork.module.home.mvc.dao.ICategoryDao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/1/5.
 */
public class AreaBusinessImpl extends BaseService implements IAreaBusiness {

    private IAreaDao dao;

    public AreaBusinessImpl(IBaseController controller) {
        super(controller);
        dao = new AreaDaoImpl(controller);
    }

    @Override
    public void getAreaListByID(String id,String name) {
        StringBuffer buffer = new StringBuffer(IAreaDao.URL_AREA);
        buffer.append("?pid=").append(id);
        try {
            buffer.append("&name=").append(URLEncoder.encode(name, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        dao.getAreaList(buffer.toString());
    }

    @Override
    public void getIndustryByDist(String id,int page) {
        StringBuffer buffer = new StringBuffer(ICategoryDao.URL_CHATSESSION);
        buffer.append("?dist=").append(id).append("&p=").append(page);
        mController.openDialog();
        dao.getIndustryList(buffer.toString());
    }

    @Override
    public void getAreaListByDefault() {
        StringBuffer buffer = new StringBuffer(IAreaDao.URL_AREA);
        buffer.append("?default=1");
        dao.getAreaList(buffer.toString());
    }
}
