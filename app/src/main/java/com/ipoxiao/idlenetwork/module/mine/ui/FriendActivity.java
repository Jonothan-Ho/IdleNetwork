package com.ipoxiao.idlenetwork.module.mine.ui;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.module.common.domain.FriendInfo;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IFriendBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.FriendBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IFriendController;
import com.ipoxiao.idlenetwork.module.mine.adapter.FriendAdapter;
import com.ipoxiao.idlenetwork.module.common.domain.Friend;
import com.ipoxiao.idlenetwork.module.mine.domain.FriendApply;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.ipoxiao.idlenetwork.view.AppSwipeRefreshLayout;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends ActionBarOneActivity implements OnItemClickListener, IFriendController {

    @ViewInject(R.id.refresh_layout)
    private AppSwipeRefreshLayout mRefreshLayout;
    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    private FriendAdapter mFriendAdapter;

    @ViewInject(R.id.layout_friend)
    private RelativeLayout mLayoutFriend;


    private IFriendBusiness mFriendBusiness;
    private int mCurrentIndex = -1;

    private int mNextPage = 1;

    @Override
    protected int getCustomView() {
        return R.layout.activity_mine_friend;
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnLoadListener(new AppSwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onPullUp() {
                mFriendBusiness.getFriendList(mNextPage);
            }

            @Override
            public void onPullDown() {
                mNextPage = 1;
                mFriendBusiness.getFriendList(mNextPage);
            }
        });

        mLayoutFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendActivity.this, NewFriendActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initSubView() {
        initTitle("我的好友");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFriendAdapter = new FriendAdapter(this);
        mFriendAdapter.addOnItemClickListener(this);
        mRecyclerView.setAdapter(mFriendAdapter);

        mFriendBusiness = new FriendBusinessImpl(this);
        mFriendBusiness.getFriendList(mNextPage);
    }

    @Override
    public void click(View v, int position) {
        Intent intent = new Intent(this, PersonalHomeActivity.class);
        Friend friend = mFriendAdapter.getItem(position);
        intent.putExtra("id", friend.getId());
        mCurrentIndex = position;
        startActivityForResult(intent, 0);
    }

    @Override
    public void onComplete(Action_Friend action, List<Friend> friends, int page) {
        mNextPage = page + 1;
        mRefreshLayout.stopLoading();
        if (page == 1) {
            mFriendAdapter.replaceData(friends);
        } else {
            mFriendAdapter.addData(friends);
        }
    }

    @Override
    public void onComplete(List<FriendInfo> friendInfos) {

    }

    @Override
    public void onComplete(Action_Friend action) {

    }

    @Override
    public void onComplete(List<FriendApply> applies, int page) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int status = data.getIntExtra("STATUS", -1);
            if (status == PersonalHomeActivity.OK_DELETE) {
                mFriendAdapter.removeData(mCurrentIndex);
            } else if (status == PersonalHomeActivity.OK_APPLY) {
                //mFriendBusiness.getFriendList();
            } else {
                ViewUtil.showToast(getContext(), "未知错误");
            }
        }
    }
}
