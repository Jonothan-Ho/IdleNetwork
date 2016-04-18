package com.ipoxiao.idlenetwork.module.home.mvc.business.impl;

import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.home.mvc.business.ICategoryBusiness;
import com.ipoxiao.idlenetwork.module.home.mvc.dao.ICategoryDao;
import com.ipoxiao.idlenetwork.module.home.mvc.dao.impl.CategoryDaoImpl;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/1/4.
 */
public class CategoryBusinessImpl extends BaseService implements ICategoryBusiness {

    private ICategoryDao dao;

    public CategoryBusinessImpl(IBaseController controller) {
        super(controller);
        dao = new CategoryDaoImpl(controller);
    }


    @Override
    public void getCategoryList() {
        dao.getCategoryList();
    }

    @Override
    public void getChatSessionByID(String cid, String dist, String type, int page) {
        StringBuffer buffer = new StringBuffer(ICategoryDao.URL_CHATSESSION);
        buffer.append("?cid=").append(cid).append("&dist=").append(dist).append("&type=").append(type).append("&p=").append(page);
        dao.getChatSessionByID(buffer.toString());
    }

    @Override
    public void getChatSessionByID(String dist, String type, int page) {
        StringBuffer buffer = new StringBuffer(ICategoryDao.URL_CHATSESSION);
        buffer.append("?dist=").append(dist).append("&type=").append(type).append("&p=").append(page);
        dao.getChatSessionByID(buffer.toString());
    }

    @Override
    public void getChatSessionInfo(String id) {
        StringBuffer buffer = new StringBuffer(ICategoryDao.URL_CHATSESSION);
        buffer.append("?id=").append(id);
        mController.openDialog();
        dao.getChatSessionInfo(buffer.toString());
    }

    @Override
    public void postJoinGroup(String id) {
        StringBuffer buffer = new StringBuffer(HttpUtils.getAbsoluteUrl(ICategoryDao.URL_ADDUSERS));
        buffer.append("?id=").append(id);
        RequestParams params = new RequestParams(buffer.toString());
        params.addBodyParameter("id", id);
        mController.openDialog();
        dao.postJoinGroup(params);
    }

    @Override
    public void deleteExitGroup(String id) {
        StringBuffer buffer = new StringBuffer(ICategoryDao.URL_EXIT);
        buffer.append("?id=").append(id);
        mController.openDialog();
        dao.deleteExitGroup(buffer.toString());
    }
}
