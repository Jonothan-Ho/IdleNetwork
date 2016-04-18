package com.ipoxiao.idlenetwork.module.account.mvc.controller;

import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.account.domain.User;

/**
 * Created by Administrator on 2016/1/5.
 */
public interface IAccountController extends IBaseController {

    void onComplete(User user,boolean isSuccess);

}
