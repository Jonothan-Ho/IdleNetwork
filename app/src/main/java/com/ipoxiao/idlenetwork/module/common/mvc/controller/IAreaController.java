package com.ipoxiao.idlenetwork.module.common.mvc.controller;

import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.domain.Area;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;

import java.util.List;

/**
 * Created by Administrator on 2016/1/5.
 */
public interface IAreaController extends IBaseController {

    void onArea(List<Area> areas);

    void onIndustry(List<ChatSession> chatSessions);

}
