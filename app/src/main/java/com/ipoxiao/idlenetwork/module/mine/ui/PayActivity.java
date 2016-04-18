package com.ipoxiao.idlenetwork.module.mine.ui;


import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.NoBarActivity;
import com.ipoxiao.idlenetwork.framework.adapter.DynamicFragmentAdapter;
import com.ipoxiao.idlenetwork.framework.adapter.FragmentTag;
import com.ipoxiao.idlenetwork.module.mine.ui.fragment.PayTimesFragment;
import com.ipoxiao.idlenetwork.module.mine.ui.fragment.PayVipFragment;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

public class PayActivity extends NoBarActivity {


    @ViewInject(R.id.ibtn_back)
    private ImageButton mBtnBack;
    @ViewInject(R.id.radioGroup)
    private RadioGroup mRadioGroup;

    private DynamicFragmentAdapter mDynamicFragmentAdapter;


    @Override
    protected int getCustomView() {
        return R.layout.activity_mine_pay;
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    }

    @Override
    protected void initSubView() {
        ArrayList<FragmentTag> tags = new ArrayList<>();
        tags.add(new FragmentTag(null, PayTimesFragment.class, null));
        tags.add(new FragmentTag(null, PayVipFragment.class, null));
        mDynamicFragmentAdapter = new DynamicFragmentAdapter(this, tags, R.id.layout_frame, getSupportFragmentManager());
        mDynamicFragmentAdapter.loadFragment(0);
    }
}
