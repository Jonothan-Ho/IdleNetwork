package com.ipoxiao.idlenetwork.module.common.mvc.dao.impl;

import android.text.TextUtils;

import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.http.ObjectRequestCallBack;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.framework.mvc.dao.impl.BaseDao;
import com.ipoxiao.idlenetwork.module.common.domain.SystemConfig;
import com.ipoxiao.idlenetwork.module.common.domain.Version;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IConfigController;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IConfigDao;
import com.ipoxiao.idlenetwork.module.mine.domain.SystemMessage;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by Administrator on 2016/1/19.
 */
public class ConfigDaoImpl extends BaseDao implements IConfigDao {

    private IConfigController mConfigController;

    public ConfigDaoImpl(IBaseController baseController) {
        super(baseController);
        mConfigController = (IConfigController) baseController;
    }

    @Override
    public void getConfig(String url) {
        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(url));
        HttpUtils.post(params, new ObjectRequestCallBack<SystemConfig>(ObjectRequestCallBack.Json_parser.Object, SystemConfig.class) {
            @Override
            public void onResponse(SystemConfig obj, Domain domain) {
//                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
              
                if (domain.isSuccess()) {
                    mConfigController.onComplete(obj);
                }
            }
        });
    }

    @Override
    public void getNotify(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<SystemMessage>(ObjectRequestCallBack.Json_parser.Array, SystemMessage.class) {
            @Override
            public void onResponse(List<SystemMessage> array, Domain domain) {
                if (array != null) {
                    if (array.size() == 0) {
                        ViewUtil.showToast(mConfigController.getContext(), "没有更多的数据");
                    }
                    mConfigController.onSystemNotify(array, domain.getPage());
                }
            }
        });
    }

    @Override
    public void updateVersion(RequestParams params) {
        HttpUtils.post(params, new ObjectRequestCallBack<Version>(ObjectRequestCallBack.Json_parser.Object, Version.class) {
            @Override
            public void onResponse(Version obj, Domain domain) {
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                if (obj != null && !TextUtils.isEmpty(obj.getFiles_link())) {
                    mConfigController.onUpdateVersion(obj);
                }
            }
        });
    }
}
