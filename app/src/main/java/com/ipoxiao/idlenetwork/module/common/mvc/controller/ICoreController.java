package com.ipoxiao.idlenetwork.module.common.mvc.controller;

import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.domain.Order;
import com.ipoxiao.idlenetwork.module.mine.domain.WithdrawRecord;

import java.util.List;

/**
 * Created by Administrator on 2016/1/6.
 */
public interface ICoreController extends IBaseController {

    enum Action_Core {
        SwitchCity, unifiedorder, openid, bind,withdraw
    }

    void onComplete(Action_Core action, boolean isSuccess,Domain domain);

    void onComplete(Order order);

    void onComplete(Action_Core action, String content);

    void onComplete(List<WithdrawRecord> records,int page);

}
