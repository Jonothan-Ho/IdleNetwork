package com.ipoxiao.idlenetwork.module.common.mvc.dao.impl;


import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.http.ObjectRequestCallBack;
import com.ipoxiao.idlenetwork.framework.http.TextRequestCallBack;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.framework.mvc.dao.impl.BaseDao;
import com.ipoxiao.idlenetwork.module.common.domain.Order;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.ICoreController;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.ICoreDao;
import com.ipoxiao.idlenetwork.module.mine.domain.WithdrawRecord;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by Administrator on 2016/1/6.
 */
public class CoreDaoImpl extends BaseDao implements ICoreDao {

    ICoreController mCoreController;

    public CoreDaoImpl(IBaseController baseController) {
        super(baseController);
        mCoreController = (ICoreController) baseController;
    }


    @Override
    public void switchCity() {
        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(URL_SWITCH_CITY));
        HttpUtils.post(params, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mBaseController.closeDialog();
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());

                mCoreController.onComplete(ICoreController.Action_Core.SwitchCity, domain.isSuccess(), domain);

            }
        });
    }

    @Override
    public void postPay(RequestParams params) {
        HttpUtils.post(params, new ObjectRequestCallBack<Order>(ObjectRequestCallBack.Json_parser.Object, Order.class) {
            @Override
            public void onResponse(Order obj, Domain domain) {
                mCoreController.closeDialog();
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                if (domain.isSuccess()) {
                    mCoreController.onComplete(obj);
                }
            }
        });
    }

    @Override
    public void postUnifiedorder(RequestParams params) {
        HttpUtils.post(params, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mCoreController.onComplete(ICoreController.Action_Core.unifiedorder, domain.getData());
            }
        });
    }

    @Override
    public void getOpenID(String url) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter(TextRequestCallBack.METHOD_NULL, TextRequestCallBack.METHOD_NULL);
        HttpUtils.get(params, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mCoreController.closeDialog();
                mCoreController.onComplete(ICoreController.Action_Core.openid, domain.getData());
            }
        });
    }

    @Override
    public void bindOpenID(RequestParams params) {
        HttpUtils.put(params, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mCoreController.closeDialog();
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                mCoreController.onComplete(ICoreController.Action_Core.bind, domain.isSuccess(), domain);
            }
        });
    }

    @Override
    public void postWithdraw(RequestParams params) {
        HttpUtils.post(params, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mCoreController.closeDialog();
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                mCoreController.onComplete(ICoreController.Action_Core.withdraw, domain.isSuccess(), domain);
            }
        });
    }

    @Override
    public void getWithdrawalRecords(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<WithdrawRecord>(ObjectRequestCallBack.Json_parser.Array, WithdrawRecord.class) {
            @Override
            public void onResponse(List<WithdrawRecord> array, Domain domain) {
                mCoreController.closeDialog();
                if (array != null) {
                    if (array.size() == 0) {
                        ViewUtil.showToast(mBaseController.getContext(), "无更多数据");
                    }
                    mCoreController.onComplete(array, domain.getPage());
                }
            }
        });
    }
}
