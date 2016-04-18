package com.ipoxiao.idlenetwork.module.common.mvc.dao.impl;

import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.http.ObjectRequestCallBack;
import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.domain.Friend;
import com.ipoxiao.idlenetwork.module.common.domain.FriendInfo;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IFriendController;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IFriendDao;
import com.ipoxiao.idlenetwork.module.mine.domain.FriendApply;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by Administrator on 2016/1/11.
 */
public class FriendDaoImpl extends BaseService implements IFriendDao {

    private IFriendController mFriendController;

    public FriendDaoImpl(IBaseController controller) {
        super(controller);
        mFriendController = (IFriendController) controller;
    }

    @Override
    public void getFriendList(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<Friend>(ObjectRequestCallBack.Json_parser.Array, Friend.class) {
            @Override
            public void onResponse(List<Friend> array, Domain domain) {
                mFriendController.closeDialog();
                mFriendController.onComplete(IFriendController.Action_Friend.list, array, domain.getPage());
            }
        });
    }

    @Override
    public void getInfo(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<FriendInfo>(ObjectRequestCallBack.Json_parser.Array, FriendInfo.class) {
            @Override
            public void onResponse(List<FriendInfo> array, Domain domain) {
                //mFriendController.closeDialog();
                mFriendController.onComplete(array);
            }
        });
    }

    @Override
    public void applyFriend(RequestParams params) {
        HttpUtils.post(params, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mFriendController.closeDialog();
                ViewUtil.showToast(mController.getContext(), domain.getMsg());
                if (domain.isSuccess()) {
                    mFriendController.onComplete(IFriendController.Action_Friend.apply);
                }
            }
        });
    }

    @Override
    public void deleteFriend(String url) {
        HttpUtils.delete(url, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mFriendController.closeDialog();
                ViewUtil.showToast(mController.getContext(), domain.getMsg());
                if (domain.isSuccess()) {
                    mFriendController.onComplete(IFriendController.Action_Friend.delete);
                }
            }
        });
    }

    @Override
    public void getApplyFriend(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<FriendApply>(ObjectRequestCallBack.Json_parser.Array, FriendApply.class) {
            @Override
            public void onResponse(List<FriendApply> array, Domain domain) {
                if (array != null) {
                    if (array.size() == 0) {
                        ViewUtil.showToast(mFriendController.getContext(), "没有更多数据");
                    }
                    mFriendController.onComplete(array, domain.getPage());
                }
            }
        });
    }

    @Override
    public void handleFriend(RequestParams params) {
        HttpUtils.put(params, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mController.closeDialog();
                if (domain.isSuccess()) {
                    mFriendController.onComplete(IFriendController.Action_Friend.handle);
                }
            }
        });
    }
}
