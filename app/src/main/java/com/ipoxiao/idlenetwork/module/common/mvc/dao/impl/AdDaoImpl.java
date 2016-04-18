package com.ipoxiao.idlenetwork.module.common.mvc.dao.impl;

import android.util.Log;

import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.http.ObjectRequestCallBack;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.framework.mvc.dao.impl.BaseDao;
import com.ipoxiao.idlenetwork.module.common.domain.Ad;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IAdController;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IAdDao;

import java.util.List;

/**
 * Created by Administrator on 2016/1/4.
 */
public class AdDaoImpl extends BaseDao implements IAdDao {

    private IAdController mAdController;

    public AdDaoImpl(IBaseController baseController) {
        super(baseController);
        mAdController = (IAdController) baseController;
    }

    @Override
    public void getAd(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<Ad>(ObjectRequestCallBack.Json_parser.Array, Ad.class) {
            @Override
            public void onResponse(List<Ad> array, Domain domain) {
                mAdController.onAd(array);
            }
        });
    }
}
