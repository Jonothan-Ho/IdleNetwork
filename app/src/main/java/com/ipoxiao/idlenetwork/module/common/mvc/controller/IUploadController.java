package com.ipoxiao.idlenetwork.module.common.mvc.controller;

import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;

/**
 * Created by Administrator on 2016/1/7.
 */
public interface IUploadController extends IBaseController {

    void onUpload(String sha1);
}
