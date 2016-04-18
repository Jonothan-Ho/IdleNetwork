package com.ipoxiao.idlenetwork.module.common.mvc.business;

import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;

/**
 * Created by Administrator on 2016/1/5.
 */
public interface IValidateCodeBusiness extends IBaseService {

    public static final String TYPE_REGIST = "1";
    public static final String TYPE_FORGET = "2";

    void getCode(String mobile, String type);

}
