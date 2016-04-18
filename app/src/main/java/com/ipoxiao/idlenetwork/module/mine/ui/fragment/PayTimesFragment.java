package com.ipoxiao.idlenetwork.module.mine.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.fragment.BaseNormalFragment;
import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.common.domain.Order;
import com.ipoxiao.idlenetwork.module.common.domain.SystemConfig;
import com.ipoxiao.idlenetwork.module.common.domain.Unifiedorder;
import com.ipoxiao.idlenetwork.module.common.mvc.business.ICoreBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.CoreBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.ICoreController;
import com.ipoxiao.idlenetwork.module.common.ui.EditActivity;
import com.ipoxiao.idlenetwork.module.mine.domain.WithdrawRecord;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.ipoxiao.idlenetwork.utils.WXChatCore;

import org.xmlpull.v1.XmlPullParserException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayTimesFragment extends BaseNormalFragment implements View.OnClickListener, ICoreController {


    @ViewInject(R.id.tv_phone)
    private TextView mTextPhone;
    @ViewInject(R.id.tv_date)
    private TextView mTextTimes;
    @ViewInject(R.id.tv_money)
    private TextView mTextMoney;

    @ViewInject(R.id.btn_commit)
    private Button mBtnCommit;

    @ViewInject(R.id.layout_date)
    private RelativeLayout mLayoutTimes;

    private ICoreBusiness mCoreBusiness;

    private String mTimes;

    private String mOrderCreateTime;

    private SystemConfig config;


    @Override
    protected int getCustomView() {
        return R.layout.fragment_mine_pay_times;
    }

    @Override
    protected void initListener() {
        mBtnCommit.setOnClickListener(this);
        mLayoutTimes.setOnClickListener(this);
    }

    @Override
    public void initSubView(Bundle savedInstanceState) {
        mCoreBusiness = new CoreBusinessImpl(this);
        config = PreferencesUtil.getInstance(getActivity()).get(SystemConfig.class);
        User user = PreferencesUtil.getInstance(getActivity()).get(User.class);
        mTextPhone.setText(user.getMobile());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                if (TextUtils.isEmpty(mTimes)) {
                    ViewUtil.showToast(getContext(), "次数不能为空");
                    return;
                }
                mCoreBusiness.postPay(ICoreBusiness.TYPE_TIMES, mTimes);
                break;
            case R.id.layout_date:
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtra(EditActivity.EDIT_TITLE, "输入次数");
                intent.putExtra(EditActivity.EDIT_TYPE, EditActivity.TYPE_INTEGER);
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mTimes = data.getStringExtra(EditActivity.EDIT_DATA);
            mTextTimes.setText(mTimes + "次");
            try {
                if (config != null) {
                    int times = Integer.parseInt(mTimes);
                    mTextMoney.setText("¥" + config.getMember_switch_num() * times);
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onComplete(Action_Core action, boolean isSuccess,Domain domain) {

    }

    @Override
    public void onComplete(Order order) {
        String money = ((int) (order.getTotalmoney() * 100)) + "";
        mOrderCreateTime = order.getCreate_time();
        RequestParams params = WXChatCore.getInstance(getActivity()).getUnifiedOrder(order.getOrdercode(), money, order.getNotify_url());
        mCoreBusiness.postUnifiedorder(params);
    }

    @Override
    public void onComplete(Action_Core action, String content) {
        if (!TextUtils.isEmpty(content)) {
            Unifiedorder order = null;
            try {
                order = WXChatCore.getInstance(getActivity()).parseUnifiedorder(content);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (order == null) {
                ViewUtil.showToast(getContext(), "微信统一下单失败，请重试");
                return;
            }
            order.setTimestamp(mOrderCreateTime);
            WXChatCore.getInstance(getActivity()).startPay(order);

        }
    }

    @Override
    public void onComplete(List<WithdrawRecord> records,int page) {

    }

}
