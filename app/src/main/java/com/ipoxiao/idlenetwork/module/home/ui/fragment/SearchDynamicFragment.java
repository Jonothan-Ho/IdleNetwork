package com.ipoxiao.idlenetwork.module.home.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.decoration.SpaceItemDecoration;
import com.ipoxiao.idlenetwork.framework.fragment.BaseNormalFragment;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.module.chat.ui.DynamicInfoActivity;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IDynamicBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.DynamicBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IDynamicController;
import com.ipoxiao.idlenetwork.module.home.adapter.SearchDynamicAdapter;
import com.ipoxiao.idlenetwork.module.common.domain.Dynamic;
import com.ipoxiao.idlenetwork.module.home.listener.OnDistListener;
import com.ipoxiao.idlenetwork.module.home.listener.OnSearchDynamicListener;
import com.ipoxiao.idlenetwork.view.AppSwipeRefreshLayout;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDynamicFragment extends BaseNormalFragment implements OnItemClickListener, OnSearchDynamicListener, IDynamicController {

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    private SearchDynamicAdapter mSearchDynamicAdapter;
    private IDynamicBusiness mBusiness;
    private int mCurrentIndex = -1;

    private int mNextPage = 1;
    @ViewInject(R.id.refresh_layout)
    private AppSwipeRefreshLayout mRefreshLayout;

    private String title;

    @Override
    public void initSubView(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        mSearchDynamicAdapter = new SearchDynamicAdapter(getActivity());
        mSearchDynamicAdapter.addOnItemClickListener(this);
        mRecyclerView.setAdapter(mSearchDynamicAdapter);
        mBusiness = new DynamicBusinessImpl(this);
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
                OnDistListener distListener = (OnDistListener) getActivity();
                mBusiness.searchDynamic(distListener.getDistID(), title, mNextPage);
            }

            @Override
            public void onPullDown() {
                mNextPage = 1;
                OnDistListener distListener = (OnDistListener) getActivity();
                mBusiness.searchDynamic(distListener.getDistID(), title, mNextPage);
            }
        });
    }

    @Override
    public void onSearch(String title) {
        this.title = title;
        mNextPage = 1;
        OnDistListener distListener = (OnDistListener) getActivity();
        mBusiness.searchDynamic(distListener.getDistID(), title, mNextPage);
    }

    @Override
    public void onComplete(Action_Dynamic action, List<Dynamic> dynamics, int page) {
        mNextPage = page + 1;
        mRefreshLayout.stopLoading();
        if (page == 1) {
            mSearchDynamicAdapter.replaceData(dynamics);
        } else {
            mSearchDynamicAdapter.addData(dynamics);
        }

    }

    @Override
    public void onComplete(Action_Dynamic action, boolean isSuccess) {

    }

    @Override
    public void click(View v, int position) {
        Intent intent = new Intent(getActivity(), DynamicInfoActivity.class);
        intent.putExtra("object", mSearchDynamicAdapter.getItem(position));
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (mCurrentIndex != -1) {
                mSearchDynamicAdapter.removeData(mCurrentIndex);
            }
        }
    }
}
