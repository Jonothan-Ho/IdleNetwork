package com.ipoxiao.idlenetwork.module.common.mvc.controller;

import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;

/**
 * Created by Administrator on 2016/1/5.
 */
public interface IValidateCodeController extends IBaseController {

    void onCode(String code);

}
