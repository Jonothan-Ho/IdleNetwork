package com.ipoxiao.idlenetwork.module.common.mvc.business.impl;

import android.text.TextUtils;

import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IEnvelopeBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IEnvelopeDao;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.EnvelopeDaoImpl;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.TextUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/1/7.
 */
public class EnvelopeBusinessImpl extends BaseService implements IEnvelopeBusiness {

    private IEnvelopeDao dao;

    public EnvelopeBusinessImpl(IBaseController controller) {
        super(controller);
        dao = new EnvelopeDaoImpl(controller);
    }

    @Override
    public void getEnvelopeList(String dist, int page) {
        StringBuffer buffer = new StringBuffer(IEnvelopeDao.URL_ENVELOPE);
        buffer.append("?dist=").append(dist).append("&p=").append(page);
        //mController.openDialog();
        dao.getEnvelopeList(buffer.toString());
    }

    @Override
    public void sendEnvelope(String money, String num, String dist, String cityindex, String remark, String imgpiclist) {
        if (TextUtils.isEmpty(money)) {
            ViewUtil.showToast(mController.getContext(), "红包金额不能为空");
            return;
        }

        if (TextUtils.isEmpty(num)) {
            ViewUtil.showToast(mController.getContext(), "红包个数不能为空");
            return;
        }

        if (TextUtils.isEmpty(cityindex)) {
            ViewUtil.showToast(mController.getContext(), "省市区不能为空");
            return;
        }

//        if (TextUtils.isEmpty(title)) {
//            ViewUtil.showToast(mController.getContext(), "标题不能为空");
//            return;
//        }

        if (TextUtils.isEmpty(remark)) {
            ViewUtil.showToast(mController.getContext(), "红包内容不能为空");
            return;
        }
        if (TextUtils.isEmpty(imgpiclist)) {
            ViewUtil.showToast(mController.getContext(), "红包图片不能为空");
            return;
        }

        if (TextUtils.isEmpty(dist)) {
            ViewUtil.showToast(mController.getContext(), "当前区域ID不能为空");
            return;
        }

        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(IEnvelopeDao.URL_ENVELOPE));
        params.addBodyParameter("money", money);
        params.addBodyParameter("num", num);
        params.addBodyParameter("dist", dist);
        params.addBodyParameter("cityindex", cityindex);
//        params.addBodyParameter("title", title);
        params.addBodyParameter("remark", remark);
//        params.addBodyParameter("imgpic", imgpic);
        params.addBodyParameter("imgpiclist", imgpiclist);

        LogUtil.printf(params.getBodyContent() + "====>");

        mController.openDialog();

        dao.sendEnvelope(params);

    }

    @Override
    public void getEnvelopeByID(String id) {
        StringBuffer buffer = new StringBuffer(IEnvelopeDao.URL_ENVELOPE);
        buffer.append("?id=").append(id);
        mController.openDialog();
        dao.getEnvelopeList(buffer.toString());
    }

    @Override
    public void receiveEnvelope(String id) {
        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(IEnvelopeDao.URL_RECEIVE));
        params.addBodyParameter("red_id", id);
        mController.openDialog();
        dao.receiveEnvelope(params);
    }

    @Override
    public void getEnvelopeRecord(String id,int page) {
        StringBuffer buffer = new StringBuffer(IEnvelopeDao.URL_RECORD);
        buffer.append("?red_id=").append(id).append("&p=").append(page);
        mController.openDialog();
        dao.getEnvelopeRecord(buffer.toString());
    }
}
