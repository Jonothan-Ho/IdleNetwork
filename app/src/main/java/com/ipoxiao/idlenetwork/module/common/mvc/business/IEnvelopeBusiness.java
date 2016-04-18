package com.ipoxiao.idlenetwork.module.common.mvc.business;

import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;

/**
 * {@link com.ipoxiao.idlenetwork.module.common.mvc.business.impl.EnvelopeBusinessImpl}
 * Created by Administrator on 2016/1/7.
 */
public interface IEnvelopeBusiness extends IBaseService {


    void getEnvelopeList(String dist,int page);

    void sendEnvelope(String money,String num,String dist,String cityindex,String remark,String imgpiclist);

    void getEnvelopeByID(String id);

    void receiveEnvelope(String id);

    void getEnvelopeRecord(String id,int page);


}
