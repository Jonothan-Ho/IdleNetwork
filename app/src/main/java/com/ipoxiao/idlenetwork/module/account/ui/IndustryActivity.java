package com.ipoxiao.idlenetwork.module.account.ui;


import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.module.account.adapter.IndustryAdapter;
import com.ipoxiao.idlenetwork.module.common.domain.Area;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IAreaBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.AreaBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IAreaController;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.view.AppSwipeRefreshLayout;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

public class IndustryActivity extends ActionBarOneActivity implements OnItemClickListener, IAreaController {

    @ViewInject(R.id.et_search)
    private EditText mEditSearch;
    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.refresh_layout)
    private AppSwipeRefreshLayout mRefreshLayout;

    private IndustryAdapter mIndustryAdapter;
    private IAreaBusiness mAreaBusiness;
    private int page = 1;
    private String id = "0";

    @Override
    protected int getCustomView() {
        return R.layout.layout_recyclerview_title_one;
    }

    @Override
    protected void initListener() {

        mRefreshLayout.setOnLoadListener(new AppSwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onPullUp() {
                if (page > 0) {
                    mAreaBusiness.getIndustryByDist(id, page);
                }
            }

            @Override
            public void onPullDown() {
                LogUtil.printf("page=====================================>"+page);
                if (page > 0) {
                    mAreaBusiness.getIndustryByDist(id, page);
                }
            }
        });
    }

    @Override
    protected void initSubView() {
        initTitle("所在行业");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mIndustryAdapter = new IndustryAdapter(this);
        mIndustryAdapter.addOnItemClickListener(this);
        mRecyclerView.setAdapter(mIndustryAdapter);
        mAreaBusiness = new AreaBusinessImpl(this);

        id = getIntent().getStringExtra("id");
        mAreaBusiness.getIndustryByDist(id, page);
    }

    @Override
    public void click(View v, int position) {
        Intent intent = new Intent();
        intent.putExtra("object", mIndustryAdapter.getItem(position));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onArea(List<Area> areas) {

    }

    @Override
    public void onIndustry(List<ChatSession> chatSessions) {
        page++;
        mRefreshLayout.stopLoading();
        LogUtil.printf(chatSessions.size() + "===>");
        if (chatSessions != null) {
            mIndustryAdapter.addData(chatSessions);
        } else {
            page = -1;
        }

    }
}
