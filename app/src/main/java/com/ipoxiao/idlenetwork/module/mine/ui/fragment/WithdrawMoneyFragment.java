package com.ipoxiao.idlenetwork.module.mine.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.fragment.BaseNormalFragment;
import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.common.domain.Order;
import com.ipoxiao.idlenetwork.module.common.domain.WXChatToken;
import com.ipoxiao.idlenetwork.module.common.mvc.business.ICoreBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.CoreBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.ICoreController;
import com.ipoxiao.idlenetwork.module.common.ui.EditActivity;
import com.ipoxiao.idlenetwork.module.mine.domain.WithdrawRecord;
import com.ipoxiao.idlenetwork.module.mine.ui.WithdrawActivity;
import com.ipoxiao.idlenetwork.utils.CommonUtil;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.ipoxiao.idlenetwork.utils.WXChatCore;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;

import org.xutils.view.annotation.ViewInject;

import java.lang.ref.SoftReference;
import java.util.List;


public class WithdrawMoneyFragment extends BaseNormalFragment implements ICoreController {

    @ViewInject(R.id.tv_money)
    private TextView mTextMoney;
    @ViewInject(R.id.tv_input_money)
    private TextView mTextInputMoney;
    @ViewInject(R.id.layout_money)
    private RelativeLayout mLayoutMoney;
    @ViewInject(R.id.btn_commit)
    private Button mBtnWithdraw;

    private ICoreBusiness mCoreBusiness;

    public static WXAuthHandler mHandler;
    private String openID;
    private String mBalance;


    @Override
    public void initSubView(Bundle savedInstanceState) {
        mHandler = new WXAuthHandler(this);
        mCoreBusiness = new CoreBusinessImpl(this);

        User user = PreferencesUtil.getInstance(getActivity()).get(User.class);
        mTextMoney.setText("¥" + user.getBalance());

    }

    @Override
    protected int getCustomView() {
        return R.layout.fragment_mine_withdraw_money;
    }

    @Override
    protected void initListener() {
        mLayoutMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtra(EditActivity.EDIT_TITLE, "输入金额");
                intent.putExtra(EditActivity.EDIT_TYPE, EditActivity.TYPE_INTEGER);
                startActivityForResult(intent, 0);
            }
        });

        mBtnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtil.isBindWXChat(getActivity())) {
                    WXChatCore.getInstance(getActivity()).getWXChatCode();
                    return;
                }

                //withdraw
                mCoreBusiness.postWithDraw(mBalance);

            }
        });

    }

    /**
     * @param content
     */
    public void showMsg(String content) {
        ViewUtil.showToast(getContext(), content);
    }


    /**
     * @param code
     */
    public void getOpenID(String code) {
        mCoreBusiness.getWXChatOpenID(code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String moneyStr = data.getStringExtra(EditActivity.EDIT_DATA);
            float money = Float.parseFloat(moneyStr);
            float balance = PreferencesUtil.getInstance(getActivity()).get(User.class).getBalance();
            if (money > balance) {
                ViewUtil.showToast(getContext(), "提现金额不能超过总金额");
                return;
            }

            mTextInputMoney.setText("¥" + money);
            mBalance = moneyStr;
        }
    }


    @Override
    public void onComplete(Action_Core action, boolean isSuccess,Domain domain) {
        switch (action) {
            case openid:
            case bind:
                if (isSuccess) {
                    User user = PreferencesUtil.getInstance(getActivity()).get(User.class);
                    user.setOpenid(openID);
                    PreferencesUtil.getInstance(getActivity()).put(user, User.class);
                }
                break;
            case withdraw:
                if (isSuccess) {
                    ((WithdrawActivity) getActivity()).setCurrent(1);
                }
                break;
        }

    }

    @Override
    public void onComplete(Order order) {

    }

    @Override
    public void onComplete(Action_Core action, String content) {
        LogUtil.printf(content + "==");
        if (TextUtils.isEmpty(content)) {
            ViewUtil.showToast(getContext(), "获取微信绑定ID失败，请重试");
            return;
        }

        WXChatToken token = JSON.parseObject(content, WXChatToken.class);
        openID = token.getOpenid();
        mCoreBusiness.bindOpenID(openID);
    }

    @Override
    public void onComplete(List<WithdrawRecord> records, int page) {

    }

    public static class WXAuthHandler extends Handler {

        private SoftReference<WithdrawMoneyFragment> instance;

        public WXAuthHandler(WithdrawMoneyFragment p_instance) {
            instance = new SoftReference<>(p_instance);
        }

        @Override
        public void handleMessage(Message msg) {
            WithdrawMoneyFragment fragment = instance.get();
            BaseResp resp = (BaseResp) msg.obj;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = ((SendAuth.Resp) resp).code; //即为所需的code
                    LogUtil.printf(code + "=========>");
                    fragment.getOpenID(code);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    fragment.showMsg("用户拒绝授权");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    fragment.showMsg("用户已取消授权");
                    break;
            }
        }
    }


}
