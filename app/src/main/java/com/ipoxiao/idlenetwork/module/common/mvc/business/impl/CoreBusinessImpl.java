package com.ipoxiao.idlenetwork.module.common.mvc.business.impl;

import android.text.TextUtils;

import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.mvc.business.ICoreBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.ICoreDao;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.CoreDaoImpl;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.ipoxiao.idlenetwork.utils.WXChatCore;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/1/6.
 */
public class CoreBusinessImpl extends BaseService implements ICoreBusiness {

    private ICoreDao mCoreDao;

    public CoreBusinessImpl(IBaseController controller) {
        super(controller);
        mCoreDao = new CoreDaoImpl(controller);
    }

    @Override
    public void switchCity() {
        mController.openDialog();
        mCoreDao.switchCity();
    }

    @Override
    public void postPay(String type, String args) {
        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(ICoreDao.URL_ORDER));
        params.addBodyParameter("type", type);
        params.addBodyParameter("args", args);
        mController.openDialog();
        mCoreDao.postPay(params);
    }

    @Override
    public void postUnifiedorder(RequestParams params) {
        mCoreDao.postUnifiedorder(params);
    }

    @Override
    public void getWXChatOpenID(String code) {
        StringBuffer buffer = new StringBuffer(ICoreDao.URL_ACCESSTOKEN);
        buffer.append("appid=").append(WXChatCore.APP_ID).append("&secret=").append(WXChatCore.SECRET).append("&code=").append(code).append("&grant_type=authorization_code");
        mController.openDialog();
        mCoreDao.getOpenID(buffer.toString());
    }

    @Override
    public void bindOpenID(String openID) {
        if (TextUtils.isEmpty(openID)) {
            ViewUtil.showToast(mController.getContext(), "微信号为空，请重试");
            return;
        }

        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(ICoreDao.URL_BIND));
        params.addBodyParameter("openid", openID);

        mController.openDialog();

        mCoreDao.bindOpenID(params);
    }

    @Override
    public void postWithDraw(String money) {
        if (TextUtils.isEmpty(money)) {
            ViewUtil.showToast(mController.getContext(), "提现金额不能为空");
            return;
        }

        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(ICoreDao.URL_WITHDRAW));
        params.addBodyParameter("money", money);

        mController.openDialog();

        mCoreDao.postWithdraw(params);

    }

    @Override
    public void getWithDrawalRecords(int page) {
        StringBuffer buffer = new StringBuffer(ICoreDao.URL_RECORD);
        buffer.append("?p=").append(page);
        mController.openDialog();
        mCoreDao.getWithdrawalRecords(buffer.toString());
    }
}
