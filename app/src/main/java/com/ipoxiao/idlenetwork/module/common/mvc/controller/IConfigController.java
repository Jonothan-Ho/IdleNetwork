package com.ipoxiao.idlenetwork.module.common.mvc.controller;

import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.domain.SystemConfig;
import com.ipoxiao.idlenetwork.module.common.domain.Version;
import com.ipoxiao.idlenetwork.module.mine.domain.SystemMessage;

import java.util.List;

/**
 * Created by Administrator on 2016/1/19.
 */
public interface IConfigController extends IBaseController {

    void onComplete(SystemConfig config);

    void onSystemNotify(List<SystemMessage> messages, int page);

    void onUpdateVersion(Version version);

}
