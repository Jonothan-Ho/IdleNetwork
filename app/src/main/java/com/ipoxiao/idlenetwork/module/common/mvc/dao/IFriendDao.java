package com.ipoxiao.idlenetwork.module.common.mvc.dao;

import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;

import org.xutils.http.RequestParams;

/**
 * {@link com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.FriendDaoImpl}
 * Created by Administrator on 2016/1/11.
 */
public interface IFriendDao extends IBaseDao {

    public static final String URL_FRIEND = "api/memberFriends";
    public static final String URL_INFO = "api/users";
    public static final String URL_APPLY = "api/memberFriends/applys";


    void getFriendList(String url);

    void getInfo(String url);

    void applyFriend(RequestParams params);

    void deleteFriend(String url);

    void getApplyFriend(String url);

    void handleFriend(RequestParams params);

}
