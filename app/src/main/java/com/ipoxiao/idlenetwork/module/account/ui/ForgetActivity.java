package com.ipoxiao.idlenetwork.module.account.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.account.mvc.business.IAccountBusiness;
import com.ipoxiao.idlenetwork.module.account.mvc.business.impl.AccountBusinessImpl;
import com.ipoxiao.idlenetwork.module.account.mvc.controller.IAccountController;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IValidateCodeBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.ValidateCodeBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IValidateCodeController;

import org.xutils.view.annotation.ViewInject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ForgetActivity extends ActionBarOneActivity implements View.OnClickListener, IAccountController, IValidateCodeController {

    @ViewInject(R.id.et_username)
    private EditText mEditUsername;
    @ViewInject(R.id.et_verify_code)
    private EditText mEditVerify;
    @ViewInject(R.id.et_password)
    private EditText mEditPasswd;
    @ViewInject(R.id.et_password_confirm)
    private EditText mEditPasswdConfirm;

    @ViewInject(R.id.tv_verify)
    private TextView mTextVerify;

    @ViewInject(R.id.btn_commit)
    private Button mBtnCommit;

    private IAccountBusiness mAccountBusiness;
    private IValidateCodeBusiness mValidateCodeBusiness;

    private ScheduledExecutorService mExecutorService; //timing service
    private Runnable mSecondTask;
    private int mCountDown;

    @Override
    protected int getCustomView() {
        return R.layout.activity_account_forget;
    }

    @Override
    protected void initListener() {
        mTextVerify.setOnClickListener(this);
        mBtnCommit.setOnClickListener(this);
    }

    @Override
    protected void initSubView() {
        initTitle("重置登录密码");
        mAccountBusiness = new AccountBusinessImpl(this);
        mValidateCodeBusiness = new ValidateCodeBusinessImpl(this);

        mSecondTask = new SecondTask();
        mExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                String username = mEditUsername.getText().toString().trim();
                String passwd = mEditPasswd.getText().toString().trim();
                String rePasswd = mEditPasswdConfirm.getText().toString().trim();
                mAccountBusiness.modify(username, passwd, rePasswd, mEditVerify.getText().toString().trim());
                break;
            case R.id.tv_verify:
                String localMobile = mEditUsername.getText().toString().trim();
                mValidateCodeBusiness.getCode(localMobile, IValidateCodeBusiness.TYPE_FORGET);
                break;
        }
    }

    @Override
    public void onComplete(User user, boolean isSuccess) {
        if (isSuccess) {
            finish();
        }
    }

    @Override
    public void onCode(String code) {
        if (!TextUtils.isEmpty(code)) {
            mEditVerify.setText(code);

            mCountDown = 60;
            mExecutorService.scheduleAtFixedRate(mSecondTask, 1, 1, TimeUnit.SECONDS);
            mTextVerify.setEnabled(false);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mExecutorService.isShutdown()) {
            mExecutorService.shutdown();
        }

        mExecutorService = null;
    }

    private class SecondTask implements Runnable {

        @Override
        public void run() {
            mCountDown--;
            if (mCountDown == 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextVerify.setText("获取验证码");
                    }
                });

                if (!mExecutorService.isShutdown()) {
                    mExecutorService.shutdown();
                }

                mTextVerify.setEnabled(true);

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextVerify.setText(mCountDown + "秒");
                    }
                });
            }
        }
    }
}
