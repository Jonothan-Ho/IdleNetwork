package com.ipoxiao.idlenetwork.module.common.mvc.dao.impl;

import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.http.ObjectRequestCallBack;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.framework.mvc.dao.impl.BaseDao;
import com.ipoxiao.idlenetwork.module.common.domain.ReceiveEnvelope;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IEnvelopeController;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IEnvelopeDao;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Envelope;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Record;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 */
public class EnvelopeDaoImpl extends BaseDao implements IEnvelopeDao {

    private IEnvelopeController mEnvelopeController;

    public EnvelopeDaoImpl(IBaseController baseController) {
        super(baseController);
        mEnvelopeController = (IEnvelopeController) baseController;
    }

    @Override
    public void getEnvelopeList(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<Envelope>(ObjectRequestCallBack.Json_parser.Array, Envelope.class) {
            @Override
            public void onResponse(List<Envelope> array, Domain domain) {
                // mEnvelopeController.closeDialog();
                mEnvelopeController.onComplete(IEnvelopeController.Action_Envelope.list, array, domain.getPage());
            }
        });
    }

    @Override
    public void sendEnvelope(RequestParams params) {
        HttpUtils.post(params, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mEnvelopeController.closeDialog();
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                mEnvelopeController.onComplete(IEnvelopeController.Action_Envelope.send, domain);
            }
        });
    }

    @Override
    public void receiveEnvelope(RequestParams params) {
        HttpUtils.post(params, new ObjectRequestCallBack<ReceiveEnvelope>(ObjectRequestCallBack.Json_parser.Object, ReceiveEnvelope.class) {
            @Override
            public void onResponse(ReceiveEnvelope obj, Domain domain) {
                mEnvelopeController.closeDialog();
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                mEnvelopeController.onComplete(obj);
            }
        });
    }

    @Override
    public void getEnvelopeRecord(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<Record>(ObjectRequestCallBack.Json_parser.Array, Record.class) {
            @Override
            public void onResponse(List<Record> array, Domain domain) {
                mEnvelopeController.closeDialog();
                ViewUtil.showToast(mEnvelopeController.getContext(), "没有更多的数据");
                mEnvelopeController.onCompleteRecord(array, domain.getPage());
            }
        });
    }
}
