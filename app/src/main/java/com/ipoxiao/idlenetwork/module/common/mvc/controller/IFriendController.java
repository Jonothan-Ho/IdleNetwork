package com.ipoxiao.idlenetwork.module.common.mvc.controller;

import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.domain.Friend;
import com.ipoxiao.idlenetwork.module.common.domain.FriendInfo;
import com.ipoxiao.idlenetwork.module.mine.domain.FriendApply;

import java.util.List;

/**
 * Created by Administrator on 2016/1/11.
 */
public interface IFriendController extends IBaseController {


    enum Action_Friend{
        list,info,apply,delete,handle
    }

    void onComplete(Action_Friend action, List<Friend> friends,int page);

    void onComplete(List<FriendInfo> friendInfos);

    void onComplete(Action_Friend action);

    void onComplete(List<FriendApply> applies, int page);

}
