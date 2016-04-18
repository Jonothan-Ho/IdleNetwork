package com.ipoxiao.idlenetwork.module.mine.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.fragment.BaseNormalFragment;
import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.module.common.domain.Order;
import com.ipoxiao.idlenetwork.module.common.mvc.business.ICoreBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.CoreBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.ICoreController;
import com.ipoxiao.idlenetwork.module.mine.adapter.WithdrawRecordAdapter;
import com.ipoxiao.idlenetwork.module.mine.domain.WithdrawRecord;
import com.ipoxiao.idlenetwork.view.AppSwipeRefreshLayout;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawRecordFragment extends BaseNormalFragment implements ICoreController {

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    private WithdrawRecordAdapter mAdapter;
   // private StickyRecyclerHeadersDecoration mDecoration;

    private ICoreBusiness mCoreBusiness;

    @ViewInject(R.id.refresh_layout)
    private AppSwipeRefreshLayout mSwipeRefreshLayout;

    private int mPage = 1;

    @Override
    public void initSubView(Bundle savedInstanceState) {
        mAdapter = new WithdrawRecordAdapter(getActivity());
       // mDecoration = new StickyRecyclerHeadersDecoration(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
       // mRecyclerView.addItemDecoration(mDecoration);
        mRecyclerView.setAdapter(mAdapter);

        mCoreBusiness = new CoreBusinessImpl(this);
        mCoreBusiness.getWithDrawalRecords(mPage);
    }

    @Override
    protected int getCustomView() {
        return R.layout.fragment_mine_withdraw_record;
    }

    @Override
    protected void initListener() {
       /* StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(mRecyclerView, mDecoration);
        touchListener.setOnHeaderClickListener(
                new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, int position, long headerId) {
                        ViewUtil.showSnackbar(getActivity().getWindow().getDecorView(), position + "====>");
                    }
                });
        mRecyclerView.addOnItemTouchListener(touchListener);*/


        /*mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                mDecoration.invalidateHeaders();
            }
        });*/

        mSwipeRefreshLayout.setOnLoadListener(new AppSwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onPullUp() {
                mCoreBusiness.getWithDrawalRecords(mPage);
            }

            @Override
            public void onPullDown() {
                mPage = 1;
                mCoreBusiness.getWithDrawalRecords(mPage);
            }
        });


    }

    @Override
    public void onComplete(Action_Core action, boolean isSuccess,Domain domain) {

    }

    @Override
    public void onComplete(Order order) {

    }

    @Override
    public void onComplete(Action_Core action, String content) {

    }

    @Override
    public void onComplete(List<WithdrawRecord> records, int page) {
        mSwipeRefreshLayout.stopLoading();
        this.mPage = page + 1;
        if (page == 1) {
            mAdapter.replaceData(records);
        } else {
            mAdapter.addData(records);
        }
    }
}
