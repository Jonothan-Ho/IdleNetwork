package com.ipoxiao.idlenetwork.module.common.mvc.business.impl;

import android.text.TextUtils;

import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IDynamicBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IDynamicController;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IDynamicDao;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.DynamicDaoImpl;
import com.ipoxiao.idlenetwork.utils.TextUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by Administrator on 2016/1/6.
 */
public class DynamicBusinessImpl extends BaseService implements IDynamicBusiness {

    private IDynamicDao dao;

    public DynamicBusinessImpl(IBaseController controller) {
        super(controller);
        dao = new DynamicDaoImpl(controller);
    }


    @Override
    public void searchDynamic(String dist, String title, int page) {

        if (TextUtils.isEmpty(title)) {
            ViewUtil.showToast(mController.getContext(), "搜索关键字不能为空");
            return;
        }

        StringBuffer buffer = new StringBuffer(IDynamicDao.URL_DYNAMIC);
        buffer.append("?dist=").append(dist);
        try {
            buffer.append("&title=").append(URLEncoder.encode(title, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        buffer.append("&p=").append(page);
        mController.openDialog();
        dao.searchDynamic(buffer.toString());
    }

    @Override
    public void topDynamic(String id) {
        StringBuffer buffer = new StringBuffer(IDynamicDao.URL_DYNAMIC);
        buffer.append("/").append(id);
        mController.openDialog();
        dao.topDynamic(buffer.toString());
    }

    @Override
    public void deleteDynamic(String id) {
        StringBuffer buffer = new StringBuffer(IDynamicDao.URL_DYNAMIC);
        buffer.append("/").append(id);
        mController.openDialog();
        dao.deleteDynamic(buffer.toString());
    }

    @Override
    public void getDynamicList(Dynamic_Action action, String gid, String uid, int page) {
        StringBuffer buffer = new StringBuffer(IDynamicDao.URL_DYNAMIC);
        IDynamicController.Action_Dynamic action_dynamic = null;
        switch (action) {
            case list_me:
                buffer.append("?gid=").append(gid).append("&uid=").append(uid);
                action_dynamic = IDynamicController.Action_Dynamic.me;
                break;
            case list_group:
                buffer.append("?gid=").append(gid);
                break;
        }

        buffer.append("&p=").append(page);
        mController.openDialog();
        dao.getDynamicList(action_dynamic, buffer.toString());
    }

    @Override
    public void publishDynamic(String gid, String title, String toclick, String remark, String imgpiclist) {
        if (TextUtils.isEmpty(title)) {
            ViewUtil.showToast(mController.getContext(), "标题不能为空");
            return;
        }

        if (TextUtils.isEmpty(remark)) {
            ViewUtil.showToast(mController.getContext(), "内容不能为空");
            return;
        }

        if (TextUtils.isEmpty(imgpiclist)) {
            ViewUtil.showToast(mController.getContext(), "图片不能为空");
            return;
        }
        if (TextUtils.isEmpty(toclick)) {
            toclick = "0";
        }
        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(IDynamicDao.URL_DYNAMIC));
        params.addBodyParameter("gid", gid);
        params.addBodyParameter("title", title);
        params.addBodyParameter("remark", remark);
        params.addBodyParameter("imgpiclist", imgpiclist);
        params.addBodyParameter("toclick",toclick);
        mController.openDialog();

        dao.postDynamic(params);

    }
}
