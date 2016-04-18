package com.ipoxiao.idlenetwork.module.common.mvc.controller;

import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.domain.ReceiveEnvelope;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Envelope;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Record;

import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 */
public interface IEnvelopeController extends IBaseController {

    enum Action_Envelope {
        list, send
    }

    void onComplete(Action_Envelope action, List<Envelope> envelopes,int page);

    void onCompleteRecord(List<Record> records,int page);

    void onComplete(Action_Envelope action,Domain domain);

    void onComplete(ReceiveEnvelope envelope);

}
