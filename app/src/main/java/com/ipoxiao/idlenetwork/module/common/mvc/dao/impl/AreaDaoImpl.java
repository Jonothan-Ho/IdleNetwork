package com.ipoxiao.idlenetwork.module.common.mvc.dao.impl;

import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.http.ObjectRequestCallBack;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.framework.mvc.dao.impl.BaseDao;
import com.ipoxiao.idlenetwork.module.common.domain.Area;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IAreaController;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IAreaDao;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/1/5.
 */
public class AreaDaoImpl extends BaseDao implements IAreaDao {

    private IAreaController mAreaController;

    public AreaDaoImpl(IBaseController baseController) {
        super(baseController);
        mAreaController = (IAreaController) baseController;
    }

    @Override
    public void getAreaList(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<Area>(ObjectRequestCallBack.Json_parser.Array, Area.class) {
            @Override
            public void onResponse(List<Area> array, Domain domain) {
                mAreaController.onArea(array);
            }
        });
    }

    @Override
    public void getIndustryList(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<ChatSession>(ObjectRequestCallBack.Json_parser.Array, ChatSession.class) {
            @Override
            public void onResponse(List<ChatSession> array, Domain domain) {
                mAreaController.closeDialog();
//                ViewUtil.showToast(mAreaController.getContext(), "没有更多的数据");
                mAreaController.onIndustry(array);
            }
        });
    }
}
