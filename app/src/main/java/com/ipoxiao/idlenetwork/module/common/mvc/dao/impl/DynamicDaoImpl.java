package com.ipoxiao.idlenetwork.module.common.mvc.dao.impl;

import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.http.ObjectRequestCallBack;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.framework.mvc.dao.impl.BaseDao;
import com.ipoxiao.idlenetwork.module.common.domain.Dynamic;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IDynamicController;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IDynamicDao;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.http.RequestParams;

import java.util.List;


/**
 * Created by Administrator on 2016/1/6.
 */
public class DynamicDaoImpl extends BaseDao implements IDynamicDao {

    private IDynamicController mDynamicController;

    public DynamicDaoImpl(IBaseController baseController) {
        super(baseController);
        if (baseController instanceof IDynamicController) {
            mDynamicController = (IDynamicController) baseController;
        }
    }

    @Override
    public void searchDynamic(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<Dynamic>(ObjectRequestCallBack.Json_parser.Array, Dynamic.class) {
            @Override
            public void onResponse(List<Dynamic> array, Domain domain) {
                mDynamicController.closeDialog();
                mDynamicController.onComplete(IDynamicController.Action_Dynamic.search, array, domain.getPage());
            }
        });
    }

    @Override
    public void topDynamic(String url) {
        HttpUtils.put(url, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mBaseController.closeDialog();
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
            }
        });
    }

    @Override
    public void deleteDynamic(String url) {
        HttpUtils.delete(url, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                mBaseController.closeDialog();
                mDynamicController.onComplete(IDynamicController.Action_Dynamic.delete, domain.isSuccess());
            }
        });
    }

    @Override
    public void getDynamicList(final IDynamicController.Action_Dynamic action, String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<Dynamic>(ObjectRequestCallBack.Json_parser.Array, Dynamic.class) {
            @Override
            public void onResponse(List<Dynamic> array, Domain domain) {
                mDynamicController.closeDialog();
                mDynamicController.onComplete(action, array, domain.getPage());
            }
        });
    }

    @Override
    public void postDynamic(RequestParams params) {
        HttpUtils.post(params, new ObjectRequestCallBack<Dynamic>(ObjectRequestCallBack.Json_parser.Object, Dynamic.class) {
            @Override
            public void onResponse(Dynamic obj, Domain domain) {
                mDynamicController.closeDialog();
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                mDynamicController.onComplete(IDynamicController.Action_Dynamic.publish, domain.isSuccess());
            }
        });
    }
}
