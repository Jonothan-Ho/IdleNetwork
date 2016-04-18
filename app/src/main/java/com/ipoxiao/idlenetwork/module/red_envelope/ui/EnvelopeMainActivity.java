package com.ipoxiao.idlenetwork.module.red_envelope.ui;


import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.NoBarActivity;
import com.ipoxiao.idlenetwork.framework.adapter.decoration.SpaceItemDecoration;
import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.module.common.domain.Ad;
import com.ipoxiao.idlenetwork.module.common.domain.ReceiveEnvelope;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IAdBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IEnvelopeBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.AdBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.EnvelopeBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IAdController;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IEnvelopeController;
import com.ipoxiao.idlenetwork.module.common.ui.WebViewActivity;
import com.ipoxiao.idlenetwork.module.mine.ui.GrantEnvelopeActivity;
import com.ipoxiao.idlenetwork.module.red_envelope.adapter.EnvelopeAdapter;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Envelope;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Record;
import com.ipoxiao.idlenetwork.utils.DensityUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.ipoxiao.idlenetwork.view.AppSwipeRefreshLayout;
import com.ipoxiao.idlenetwork.view.AutoScrollViewPager;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

public class EnvelopeMainActivity extends NoBarActivity implements IEnvelopeController, IAdController, View.OnClickListener, OnItemClickListener, AutoScrollViewPager.OnImageViewClickListener {

    @ViewInject(R.id.viewpager)
    private AutoScrollViewPager mViewPager;
    @ViewInject(R.id.btn_back)
    private ImageButton mBtnBack;

    @ViewInject(R.id.tv_grab)
    private TextView mTextGrab;
    @ViewInject(R.id.tv_grant)
    private TextView mTextGrant;

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.refresh_layout)
    private AppSwipeRefreshLayout mSwipeRefreshLayout;

    private EnvelopeAdapter mEnvelopeAdapter;

    private IAdBusiness mAdBusiness;
    private List<Ad> ads;
    private IEnvelopeBusiness mEnvelopeBusiness;

    private String dist;

    private int page = 1;


    @Override
    protected int getCustomView() {
        return R.layout.activity_envelope_main;
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        mTextGrant.setOnClickListener(this);

        mSwipeRefreshLayout.setOnLoadListener(new AppSwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onPullUp() {
                mEnvelopeBusiness.getEnvelopeList(dist, page);
            }

            @Override
            public void onPullDown() {
                page = 1;
                mEnvelopeBusiness.getEnvelopeList(dist, page);
            }
        });


    }

    @Override
    protected void initSubView() {
        mRecyclerView.setPadding(8, 0, 8, 0);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(this, 8)));
        mEnvelopeAdapter = new EnvelopeAdapter(this);
        mEnvelopeAdapter.addOnItemClickListener(this);
        mRecyclerView.setAdapter(mEnvelopeAdapter);


        mEnvelopeBusiness = new EnvelopeBusinessImpl(this);
        dist = getIntent().getStringExtra("dist");
        mEnvelopeBusiness.getEnvelopeList(dist, page);
        mAdBusiness = new AdBusinessImpl(this);
        mAdBusiness.getAd(dist);

    }

    @Override
    public void click(View v, int position) {
        Intent intent = new Intent(this, GrabRedEnvelopeActivity.class);
        intent.putExtra("object", mEnvelopeAdapter.getItem(position));
        startActivity(intent);
    }

    private void buildNativeViewPager(String[] urls) {
        mViewPager.setUrls(urls);
        mViewPager.setOnImageClickListener(this);
        LinearLayout layoutIndictor = new LinearLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutIndictor.setGravity(Gravity.RIGHT);
        layoutIndictor.setPadding(0, 10, 0, 10);
        mViewPager.setIndicatorLayout(layoutIndictor, params, 25, R.drawable.selector_shape_oval_style_one);
        mViewPager.startAutoScroll(10);
    }

    @Override
    public void onImageClick(int position, View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.FLAG_URL, ads.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_grant:
                Intent intent = new Intent(this, GrantEnvelopeActivity.class);
                intent.putExtra("dist", getIntent().getStringExtra("dist"));
                startActivityForResult(intent, 10);
                break;
        }
    }

    @Override
    public void onAd(List<Ad> ads) {
        this.ads = ads;
        String urls[] = new String[ads.size()];
        for (int i = 0; i < ads.size(); i++) {
            urls[i] = ads.get(i).getImgpic_link();
        }

        if (urls == null || urls.length == 0) {
            ViewUtil.showToast(this, "没有广告");
            return;
        }
        buildNativeViewPager(urls);
    }

    @Override
    public void onComplete(Action_Envelope action, List<Envelope> envelopes, int page) {
        mSwipeRefreshLayout.stopLoading();
        this.page = page + 1;
        if (page == 1) {
            mEnvelopeAdapter.replaceData(envelopes);
        } else {
            mEnvelopeAdapter.addData(envelopes);
        }
    }

    @Override
    public void onCompleteRecord(List<Record> records,int page) {

    }

    @Override
    public void onComplete(Action_Envelope action, Domain isSuccess) {

    }

    @Override
    public void onComplete(ReceiveEnvelope envelope) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            page = 1;
            mEnvelopeBusiness.getEnvelopeList(dist, page);
        }
    }
}
