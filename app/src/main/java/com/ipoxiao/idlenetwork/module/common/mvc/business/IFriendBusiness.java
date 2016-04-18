package com.ipoxiao.idlenetwork.module.common.mvc.business;

import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;

/**
 * {@link com.ipoxiao.idlenetwork.module.common.mvc.business.impl.FriendBusinessImpl}
 * Created by Administrator on 2016/1/11.
 */
public interface IFriendBusiness extends IBaseService {


    void getFriendList(int page);

    void getFriendInfo(String id);

    void applyFriend(String id);

    void deleteFriend(String id);

    void getApplyFriend(int page);

    void handleFriend(String id);
}
