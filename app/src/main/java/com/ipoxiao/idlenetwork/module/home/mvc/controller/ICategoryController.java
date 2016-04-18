package com.ipoxiao.idlenetwork.module.home.mvc.controller;

import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.home.domain.ChatCategory;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSessionInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/1/4.
 */
public interface ICategoryController extends IBaseController {

    enum Category_Action {
        Group,Exit
    }

    void onCategoryList(List<ChatCategory> categories);

    void onChatSessionInfo(ChatSessionInfo info);

    void onChatSessionList(List<ChatSession> sessions, Domain domain);

    void onComplete(Category_Action action);

}
