package com.ipoxiao.idlenetwork.module.mine.ui;


import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.common.Common;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.module.common.domain.SystemConfig;
import com.ipoxiao.idlenetwork.module.common.domain.Version;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IConfigBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.ConfigBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IConfigController;
import com.ipoxiao.idlenetwork.module.mine.adapter.SystemMessageAdapter;
import com.ipoxiao.idlenetwork.module.mine.domain.SystemMessage;
import com.ipoxiao.idlenetwork.module.red_envelope.ui.SeparateActivity;
import com.ipoxiao.idlenetwork.view.AppSwipeRefreshLayout;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

public class SystemMessageActivity extends ActionBarOneActivity implements IConfigController, OnItemClickListener {

    @ViewInject(R.id.refresh_layout)
    private AppSwipeRefreshLayout mRefreshLayout;
    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    private IConfigBusiness mConfigBusiness;

    private SystemMessageAdapter mSystemMessageAdapter;

    private int mPage = 1;


    @Override
    protected int getCustomView() {
        return R.layout.layout_recyclerview_title_one;
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnLoadListener(new AppSwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onPullUp() {
                mConfigBusiness.getSystemNotify(mPage);
            }

            @Override
            public void onPullDown() {
                mPage = 1;
                mConfigBusiness.getSystemNotify(mPage);
            }
        });
    }

    @Override
    protected void initSubView() {
        initTitle("系统消息");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSystemMessageAdapter = new SystemMessageAdapter(this);
        mSystemMessageAdapter.addOnItemClickListener(this);

        mRecyclerView.setAdapter(mSystemMessageAdapter);

        mConfigBusiness = new ConfigBusinessImpl(this);
        mConfigBusiness.getSystemNotify(mPage);
        Common.jp_count=0;

    }

    @Override
    public void onComplete(SystemConfig config) {

    }

    @Override
    public void onSystemNotify(List<SystemMessage> messages, int page) {
        mRefreshLayout.stopLoading();
        mPage = page + 1;
        if (page == 1) {
            mSystemMessageAdapter.replaceData(messages);
        } else {
            mSystemMessageAdapter.addData(messages);
        }
    }

    @Override
    public void onUpdateVersion(Version version) {

    }

    @Override
    public void click(View v, int position) {
        SystemMessage message = mSystemMessageAdapter.getItem(position);
        Intent intent = new Intent();
        switch (message.getType()) {
            case 1:
                intent.setClass(this, NewFriendActivity.class);
                break;
            case 2:
            case 4:
                intent.setClass(this, SeparateActivity.class);
                intent.putExtra("id", message.getForm_id());
                break;
            case 3:
                intent.setClass(this, WithdrawActivity.class);
                intent.putExtra("type", 1);
                break;
            default:
                return;
        }
        startActivity(intent);
    }
}
