package com.ipoxiao.idlenetwork.module.common.mvc.controller;

import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.domain.Dynamic;

import java.util.List;

/**
 * Created by Administrator on 2016/1/6.
 */
public interface IDynamicController extends IBaseController {

    enum Action_Dynamic{
        search,delete,me,group,dist,publish
    }

    void onComplete(Action_Dynamic action, List<Dynamic> dynamics,int page);

    void onComplete(Action_Dynamic action, boolean isSuccess);

}
