package com.ipoxiao.idlenetwork.wxapi;


import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.NoBarActivity;
import com.ipoxiao.idlenetwork.module.mine.ui.GrantEnvelopeActivity;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.ipoxiao.idlenetwork.utils.WXChatCore;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import android.content.Intent;
import android.os.Bundle;

public class WXPayEntryActivity extends NoBarActivity implements IWXAPIEventHandler {

    @Override
    protected int getCustomView() {
        return R.layout.pay_result;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
            switch (code) {
                case 0:
                    ViewUtil.showToast(this, "充值成功");
                    Intent intent = new Intent(this,GrantEnvelopeActivity.class);
                    intent.putExtra("flag", true);
                    startActivity(intent);
                    finish();
                    break;
                case -1:
                    ViewUtil.showToast(this, "APPID错误");
                    break;
                case -2:
                    ViewUtil.showToast(this, "用户取消付款");
                    break;
            }
        }
        finish();
    }

    @Override
    protected void initSubView() {
        handleIntent(getIntent());
    }

    /**
     * @param intent
     */
    public void handleIntent(Intent intent) {
        WXChatCore.getInstance(this).getAPI().handleIntent(intent, this);
    }
}