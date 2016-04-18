package com.ipoxiao.idlenetwork.module.chat.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.fragment.BaseNormalFragment;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.chat.adapter.DynamicAdapter;
import com.ipoxiao.idlenetwork.module.chat.ui.DynamicInfoActivity;
import com.ipoxiao.idlenetwork.module.common.domain.Dynamic;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IDynamicBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.DynamicBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IDynamicController;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.view.AppSwipeRefreshLayout;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

public class DynamicMeFragment extends BaseNormalFragment implements OnItemClickListener, IDynamicController {

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    private DynamicAdapter mDynamicAdapter;
    private IDynamicBusiness mDynamicBusiness;
    @ViewInject(R.id.refresh_layout)
    private AppSwipeRefreshLayout mRefreshLayout;

    private int mNextPage = 1;
    private String gid;
    private String uid;

    @Override
    public void initSubView(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mDynamicAdapter = new DynamicAdapter(getActivity());
        mDynamicAdapter.addOnItemClickListener(this);
        mRecyclerView.setAdapter(mDynamicAdapter);

        mDynamicBusiness = new DynamicBusinessImpl(this);
        User user = PreferencesUtil.getInstance(getActivity()).get(User.class);
        gid = getArguments().getString("id");
        uid = user.getId();
        mDynamicBusiness.getDynamicList(IDynamicBusiness.Dynamic_Action.list_me, gid, uid, mNextPage);
    }

    @Override
    protected int getCustomView() {
        return R.layout.layout_recyclerview_two;
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnLoadListener(new AppSwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onPullUp() {
                mDynamicBusiness.getDynamicList(IDynamicBusiness.Dynamic_Action.list_me, gid, uid, mNextPage);
            }

            @Override
            public void onPullDown() {
                mNextPage = 1;
                mDynamicBusiness.getDynamicList(IDynamicBusiness.Dynamic_Action.list_me, gid, uid, mNextPage);
            }
        });
    }

    @Override
    public void click(View v, int position) {
        Intent intent = new Intent(getActivity(), DynamicInfoActivity.class);
        intent.putExtra("object", mDynamicAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onComplete(Action_Dynamic action, List<Dynamic> dynamics, int page) {
        mNextPage = page + 1;
        mRefreshLayout.stopLoading();
        if (page == 1) {
            mDynamicAdapter.replaceData(dynamics);
        } else {
            mDynamicAdapter.addData(dynamics);
        }

    }

    @Override
    public void onComplete(Action_Dynamic action, boolean isSuccess) {

    }
}
