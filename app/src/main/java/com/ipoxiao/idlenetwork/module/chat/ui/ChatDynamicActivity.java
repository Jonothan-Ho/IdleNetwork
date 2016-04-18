package com.ipoxiao.idlenetwork.module.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.NoBarActivity;
import com.ipoxiao.idlenetwork.framework.adapter.DynamicFragmentAdapter;
import com.ipoxiao.idlenetwork.framework.adapter.FragmentTag;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.chat.ui.fragment.DynamicAllFragment;
import com.ipoxiao.idlenetwork.module.chat.ui.fragment.DynamicMeFragment;
import com.ipoxiao.idlenetwork.module.common.domain.Ad;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IAdBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.AdBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IAdController;
import com.ipoxiao.idlenetwork.module.common.ui.WebViewActivity;
import com.ipoxiao.idlenetwork.module.home.ui.fragment.ChatRoomFragment;
import com.ipoxiao.idlenetwork.module.home.ui.fragment.SearchDynamicFragment;
import com.ipoxiao.idlenetwork.module.red_envelope.ui.EnvelopeMainActivity;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.ipoxiao.idlenetwork.view.AutoScrollViewPager;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class ChatDynamicActivity extends NoBarActivity implements AutoScrollViewPager.OnImageViewClickListener, IAdController {

    @ViewInject(R.id.viewpager)
    private AutoScrollViewPager mViewPager;
    @ViewInject(R.id.radioGroup)
    private RadioGroup mRadioGroup;
    private DynamicFragmentAdapter mDynamicFragmentAdapter;

    @ViewInject(R.id.btn_envelope)
    private FloatingActionButton mBtnEnvelope;

    @ViewInject(R.id.btn_back)
    private ImageButton mBtnBack;
    @ViewInject(R.id.tv_publish)
    private TextView mTextPublish;

    private IAdBusiness mAdBusiness;

    private List<Ad> ads;


    @Override
    protected int getCustomView() {
        return R.layout.activity_chat_dynamic;
    }

    @Override
    protected void initListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_one:
                        mDynamicFragmentAdapter.loadFragment(0);
                        break;
                    case R.id.rbtn_two:
                        mDynamicFragmentAdapter.loadFragment(1);
                        break;
                }
            }
        });

        mTextPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = PreferencesUtil.getInstance(ChatDynamicActivity.this).get(User.class);
//                if (user.getIs_vip() != 1) {
//                    ViewUtil.showToast(getContext(), "您不是VIP用户，不能发布");
//                    return;
//                }

                if (getIntent().getIntExtra("isJoin", 0) == 0) {
                    ViewUtil.showToast(getContext(), "您还没加入群组，不能发布");
                    return;
                }


                Intent intent = new Intent(ChatDynamicActivity.this, PublishActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (IS_RED) {
            mBtnEnvelope.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChatDynamicActivity.this, EnvelopeMainActivity.class);
                    intent.putExtra("dist", PreferencesUtil.getInstance(ChatDynamicActivity.this).get("dist"));
                    startActivity(intent);
                }
            });
        } else {
            mBtnEnvelope.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initSubView() {
        String id = getIntent().getStringExtra("id");
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        ArrayList<FragmentTag> tags = new ArrayList<>();
        tags.add(new FragmentTag(null, DynamicAllFragment.class, bundle));
        tags.add(new FragmentTag(null, DynamicMeFragment.class, bundle));
        mDynamicFragmentAdapter = new DynamicFragmentAdapter(this, tags, R.id.layout_frame, getSupportFragmentManager());
        mDynamicFragmentAdapter.loadFragment(0);

        String dist = PreferencesUtil.getInstance(this).get("dist");
        mAdBusiness = new AdBusinessImpl(this);
        mAdBusiness.getAd(dist);

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
}
