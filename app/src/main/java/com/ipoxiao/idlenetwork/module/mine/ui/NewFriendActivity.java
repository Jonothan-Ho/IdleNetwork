package com.ipoxiao.idlenetwork.module.mine.ui;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.module.common.domain.Friend;
import com.ipoxiao.idlenetwork.module.common.domain.FriendInfo;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IFriendBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.FriendBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IFriendController;
import com.ipoxiao.idlenetwork.module.mine.adapter.ApplyFriendAdapter;
import com.ipoxiao.idlenetwork.module.mine.domain.FriendApply;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.ipoxiao.idlenetwork.view.AppSwipeRefreshLayout;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

public class NewFriendActivity extends ActionBarOneActivity implements OnItemClickListener, IFriendController {

    @ViewInject(R.id.refresh_layout)
    private AppSwipeRefreshLayout mRefreshLayout;
    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    private IFriendBusiness mFriendBusiness;

    private ApplyFriendAdapter mFriendAdapter;

    private int mPage = 1;

    private int mCurrentIndex = -1;


    @Override
    protected int getCustomView() {
        return R.layout.layout_recyclerview_title_one;
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnLoadListener(new AppSwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onPullUp() {
                mFriendBusiness.getApplyFriend(mPage);
            }

            @Override
            public void onPullDown() {
                mPage = 1;
                mFriendBusiness.getApplyFriend(mPage);
            }
        });
    }

    @Override
    protected void initSubView() {
        initTitle("新的好友");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFriendAdapter = new ApplyFriendAdapter(this);
        mFriendAdapter.addOnItemClickListener(this);
        mRecyclerView.setAdapter(mFriendAdapter);

        mFriendBusiness = new FriendBusinessImpl(this);
        mFriendBusiness.getApplyFriend(mPage);
    }

    @Override
    public void click(View v, int position) {
        FriendApply friend = mFriendAdapter.getItem(position);
        this.mCurrentIndex = position;
        if (friend.getStatus() == 1) {
            ViewUtil.showToast(getContext(),"已处理该请求");
        } else {
            mFriendBusiness.handleFriend(friend.getId());
        }


    }

    @Override
    public void onComplete(Action_Friend action, List<Friend> friends, int page) {

    }

    @Override
    public void onComplete(List<FriendInfo> friendInfos) {

    }

    @Override
    public void onComplete(Action_Friend action) {
        if (mCurrentIndex != -1) {
            mFriendAdapter.getItem(mCurrentIndex).setStatus(1);
            mFriendAdapter.notifyItemChanged(mCurrentIndex);
        }
    }

    @Override
    public void onComplete(List<FriendApply> applies, int page) {
        mRefreshLayout.stopLoading();
        mPage = page + 1;
        if (page == 1) {
            mFriendAdapter.replaceData(applies);
        } else {
            mFriendAdapter.addData(applies);
        }
    }
}
