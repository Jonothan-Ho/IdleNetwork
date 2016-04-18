package com.ipoxiao.idlenetwork.module.account.mvc.business.impl;

import android.text.TextUtils;
import android.util.Log;

import com.ipoxiao.idlenetwork.framework.app.IdleApplication;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.account.mvc.business.IAccountBusiness;
import com.ipoxiao.idlenetwork.module.account.mvc.dao.IAccountDao;
import com.ipoxiao.idlenetwork.module.account.mvc.dao.impl.AccountDaoImpl;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.TextUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.http.RequestParams;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/1/5.
 */
public class AccountBusinessImpl extends BaseService implements IAccountBusiness {

    private IAccountDao dao;

    public AccountBusinessImpl(IBaseController controller) {
        super(controller);
        dao = new AccountDaoImpl(controller);
    }

    @Override
    public void register(String mobile, String password, String repassword, String code, String cityindex, String dist, String trade) {
        if (!TextUtil.isValidPhone(mobile, mController.getContext()))
            return;

        if (!TextUtil.isValidPassword(password, mController.getContext()))
            return;

        if (!password.equals(repassword)) {
            ViewUtil.showToast(mController.getContext(), "两次密码输入不相同");
            return;
        }

        if (TextUtils.isEmpty(code)) {
            ViewUtil.showToast(mController.getContext(), "验证码不能为空");
            return;
        }

        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(IAccountDao.URL_REGIST));
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("password", password);
        params.addBodyParameter("repassword", repassword);
        params.addBodyParameter("code", code);
        params.addBodyParameter("cityindex", cityindex);
        params.addBodyParameter("dist", dist);
        params.addBodyParameter("trade", trade);
        params.addBodyParameter("jpush", JPushInterface.getRegistrationID(mController.getContext()));
        try {
            JPushInterface.setAlias(IdleApplication.getInstance(), mobile, null);
        } catch (Exception e) {
            LogUtil.printf("JPush==>BroadcastReceiver components are not allowed to register to receive intents");
        }


        mController.openDialog();
        dao.loginRegister(params);

    }

    @Override
    public void login(String username, String passwd) {
        if (!TextUtil.isValidUser(username, passwd, mController.getContext()))
            return;

        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(IAccountDao.URL_LOGIN));
        params.addBodyParameter("mobile", username);
        params.addBodyParameter("password", passwd);
        params.addBodyParameter("jpush", JPushInterface.getRegistrationID(mController.getContext()));

        try {
            JPushInterface.setAlias(IdleApplication.getInstance(), username, null);
         } catch (Exception e) {
            LogUtil.printf("JPush==>BroadcastReceiver components are not allowed to register to receive intents");
        }


        mController.openDialog();
        dao.loginRegister(params);

    }

    @Override
    public void modify(String mobile, String password, String repassword, String code) {
        if (!TextUtil.isValidUser(mobile, password, mController.getContext()))
            return;

        if (!password.equals(repassword)) {
            ViewUtil.showToast(mController.getContext(), "两次密码输入不相同");
            return;
        }

        if (TextUtils.isEmpty(code)) {
            ViewUtil.showToast(mController.getContext(), "验证码不能为空");
            return;
        }

        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(IAccountDao.URL_MODIFY));
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("password", password);
        params.addBodyParameter("repassword", repassword);
        params.addBodyParameter("code", code);

        mController.openDialog();
        dao.modify(params);

    }

    @Override
    public void updateUser(String head, String nickname, String dist, String cityindex, String trade) {
        if (TextUtils.isEmpty(head)) {
            ViewUtil.showToast(mController.getContext(), "头像不能为空");
            return;
        }

        if (TextUtils.isEmpty(nickname)) {
            ViewUtil.showToast(mController.getContext(), "用户名不能为空");
            return;
        }

        if (TextUtils.isEmpty(dist)) {
            ViewUtil.showToast(mController.getContext(), "区域不能为空");
            return;
        }

        if (TextUtils.isEmpty(cityindex)) {
            ViewUtil.showToast(mController.getContext(), "省市区不能为空");
            return;
        }

        if (TextUtils.isEmpty(trade)) {
            ViewUtil.showToast(mController.getContext(), "行业不能为空");
            return;
        }

        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(IAccountDao.URL_USER));
        params.addBodyParameter("head", head);
        params.addBodyParameter("nickname", nickname);
        params.addBodyParameter("dist", dist);
        params.addBodyParameter("cityindex", cityindex);
        params.addBodyParameter("trade", trade);

        mController.openDialog();

        dao.updateUser(params);

    }
}
