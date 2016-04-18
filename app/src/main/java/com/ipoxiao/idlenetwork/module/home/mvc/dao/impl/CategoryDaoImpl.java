package com.ipoxiao.idlenetwork.module.home.mvc.dao.impl;

import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.http.ObjectRequestCallBack;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.framework.mvc.dao.impl.BaseDao;
import com.ipoxiao.idlenetwork.module.home.domain.ChatCategory;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSessionInfo;
import com.ipoxiao.idlenetwork.module.home.domain.parser.ChatSessionInfoParser;
import com.ipoxiao.idlenetwork.module.home.mvc.controller.ICategoryController;
import com.ipoxiao.idlenetwork.module.home.mvc.dao.ICategoryDao;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by Administrator on 2016/1/4.
 */
public class CategoryDaoImpl extends BaseDao implements ICategoryDao {

    private ICategoryController mCategoryController;

    public CategoryDaoImpl(IBaseController baseController) {
        super(baseController);
        mCategoryController = (ICategoryController) baseController;
    }

    @Override
    public void getCategoryList() {
        HttpUtils.get(URL_CATEGORY, new ObjectRequestCallBack<ChatCategory>(ObjectRequestCallBack.Json_parser.Array, ChatCategory.class) {
            @Override
            public void onResponse(List<ChatCategory> array, Domain domain) {
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                if (domain.isSuccess()) {
                    mCategoryController.onCategoryList(array);
                }
            }
        });
    }

    @Override
    public void getChatSessionByID(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<ChatSession>(ObjectRequestCallBack.Json_parser.Array, ChatSession.class) {
            @Override
            public void onResponse(List<ChatSession> array, Domain domain) {
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                mCategoryController.onChatSessionList(array, domain);
            }
        });
    }

    @Override
    public void getChatSessionInfo(String url) {
        HttpUtils.get(url, new ObjectRequestCallBack<ChatSessionInfo>(ObjectRequestCallBack.Json_parser.Array, new ChatSessionInfoParser()) {
            @Override
            public void onResponse(List<ChatSessionInfo> array, Domain domain) {
                mCategoryController.closeDialog();
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                if (domain.isSuccess() && array != null && array.size() > 0) {
                    mCategoryController.onChatSessionInfo(array.get(0));
                }
            }
        });
    }

    @Override
    public void postJoinGroup(RequestParams params) {
        HttpUtils.post(params, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mCategoryController.closeDialog();
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                if (domain.isSuccess()) {
                    mCategoryController.onComplete(ICategoryController.Category_Action.Group);
                }
            }
        });
    }

    @Override
    public void deleteExitGroup(String url) {
        HttpUtils.delete(url, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class){
            @Override
            public void onResponse(String obj, Domain domain) {
                mCategoryController.closeDialog();
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                if (domain.isSuccess()) {
                    mCategoryController.onComplete(ICategoryController.Category_Action.Exit);
                }
            }
        });
    }
}
