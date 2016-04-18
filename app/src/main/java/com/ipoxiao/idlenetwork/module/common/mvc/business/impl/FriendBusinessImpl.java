package com.ipoxiao.idlenetwork.module.common.mvc.business.impl;

import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IFriendBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IFriendDao;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.FriendDaoImpl;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/1/11.
 */
public class FriendBusinessImpl extends BaseService implements IFriendBusiness {

    private IFriendDao dao;

    public FriendBusinessImpl(IBaseController controller) {
        super(controller);
        dao = new FriendDaoImpl(controller);
    }

    @Override
    public void getFriendList(int page) {
        mController.openDialog();
        StringBuffer buffer = new StringBuffer(IFriendDao.URL_FRIEND);
        buffer.append("?p=").append(page);
        dao.getFriendList(buffer.toString());

    }

    @Override
    public void getFriendInfo(String id) {
        StringBuffer buffer = new StringBuffer(IFriendDao.URL_INFO);
        buffer.append("?id=").append(id);
        //mController.openDialog();
        dao.getInfo(buffer.toString());
    }


    @Override
    public void applyFriend(String id) {
        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(IFriendDao.URL_FRIEND));
        params.addBodyParameter("to_uid", id);
        mController.openDialog();
        dao.applyFriend(params);
    }

    @Override
    public void deleteFriend(String id) {
        StringBuffer buffer = new StringBuffer(IFriendDao.URL_FRIEND);
        buffer.append("?to_uid=").append(id);
        mController.openDialog();
        dao.deleteFriend(buffer.toString());
    }

    @Override
    public void getApplyFriend(int page) {
        StringBuffer buffer = new StringBuffer(IFriendDao.URL_APPLY);
        buffer.append("?p=").append(page);
        dao.getApplyFriend(buffer.toString());
    }

    @Override
    public void handleFriend(String id) {
        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(IFriendDao.URL_FRIEND));
        params.addBodyParameter("id", id);
        params.addBodyParameter("status", "1");
        mController.openDialog();
        dao.handleFriend(params);
    }
}
