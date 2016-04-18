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
import com.ipoxiao.idlenetwork.module.mine.ui.fragment.WithdrawMoneyFragment;
import com.ipoxiao.idlenetwork.module.mine.ui.fragment.WithdrawRecordFragment;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

public class WithdrawActivity extends NoBarActivity implements IWXAPIEventHandler {

    @ViewInject(R.id.ibtn_back)
    private ImageButton mBtnBack;
    @ViewInject(R.id.radioGroup)
    private RadioGroup mRadioGroup;

    private DynamicFragmentAdapter mDynamicFragmentAdapter;

    @Override
    protected int getCustomView() {
        return R.layout.activity_mine_withdraw;
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
        tags.add(new FragmentTag(null, WithdrawMoneyFragment.class, null));
        tags.add(new FragmentTag(null, WithdrawRecordFragment.class, null));
        mDynamicFragmentAdapter = new DynamicFragmentAdapter(this, tags, R.id.layout_frame, getSupportFragmentManager());

        int type = getIntent().getIntExtra("type", -1);
        if (type == 1) {
            setCurrent(1);
            mDynamicFragmentAdapter.loadFragment(1);
        } else {
            setCurrent(0);
            mDynamicFragmentAdapter.loadFragment(0);
        }

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.printf("===>onPayFinish, errCode = " + baseResp.errCode);
        LogUtil.printf("===>onPayFinish, openID = " + baseResp.openId);
    }

    /**
     * @param index
     */
    public void setCurrent(int index) {
        if (index == 0) {
            mRadioGroup.check(R.id.rbtn_one);
        } else {
            mRadioGroup.check(R.id.rbtn_two);
        }

        //mDynamicFragmentAdapter.loadFragment(index);
    }
}
