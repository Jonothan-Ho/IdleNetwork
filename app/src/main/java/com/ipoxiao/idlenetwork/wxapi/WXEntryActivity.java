package com.ipoxiao.idlenetwork.wxapi;

import android.content.Intent;
import android.os.Message;

import com.ipoxiao.idlenetwork.framework.activity.NoBarActivity;
import com.ipoxiao.idlenetwork.module.mine.ui.fragment.WithdrawMoneyFragment;
import com.ipoxiao.idlenetwork.utils.WXChatCore;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends NoBarActivity implements IWXAPIEventHandler {


    @Override
    protected void initSubView() {
        handleIntent(getIntent());
    }

    @Override
    protected int getCustomView() {
        return 0;
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
        Message message = WithdrawMoneyFragment.mHandler.obtainMessage();
        message.obj = resp;
        WithdrawMoneyFragment.mHandler.sendMessage(message);
        finish();

       /* switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) resp).code; //即为所需的code
                LogUtil.printf(code + "=========>");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ViewUtil.showSnackbar(getRootView(), "用户拒绝授权");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ViewUtil.showSnackbar(getRootView(), "用户已取消授权");
                break;
        }*/
    }

    private void handleIntent(Intent intent) {
        WXChatCore.getInstance(this).getAPI().handleIntent(intent, this);
    }


}