package com.ipoxiao.idlenetwork.module.account.mvc.dao.impl;

import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.http.ObjectRequestCallBack;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.framework.mvc.dao.impl.BaseDao;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.account.mvc.controller.IAccountController;
import com.ipoxiao.idlenetwork.module.account.mvc.dao.IAccountDao;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/1/5.
 */
public class AccountDaoImpl extends BaseDao implements IAccountDao {

    private IAccountController mAccountController;

    public AccountDaoImpl(IBaseController baseController) {
        super(baseController);
        mAccountController = (IAccountController) baseController;
    }

    @Override
    public void loginRegister(RequestParams params) {
        HttpUtils.post(params, new ObjectRequestCallBack<User>(ObjectRequestCallBack.Json_parser.Object, User.class) {
            @Override
            public void onResponse(User obj, Domain domain) {
                mAccountController.closeDialog();
                ViewUtil.showToast(mAccountController.getContext(), domain.getMsg());
                mAccountController.onComplete(obj, domain.isSuccess());
            }
        });
    }

    @Override
    public void modify(RequestParams params) {
        HttpUtils.post(params, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mAccountController.closeDialog();
                ViewUtil.showToast(mAccountController.getContext(), domain.getMsg());
                mAccountController.onComplete(null, domain.isSuccess());
            }
        });
    }

    @Override
    public void updateUser(RequestParams params) {
        HttpUtils.put(params, new ObjectRequestCallBack<User>(ObjectRequestCallBack.Json_parser.Object, User.class) {
            @Override
            public void onResponse(User obj, Domain domain) {
                mAccountController.closeDialog();
                ViewUtil.showToast(mAccountController.getContext(), domain.getMsg());
                mAccountController.onComplete(obj, domain.isSuccess());
            }
        });
    }
}
