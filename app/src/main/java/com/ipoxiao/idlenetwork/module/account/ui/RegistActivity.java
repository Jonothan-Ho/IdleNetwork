package com.ipoxiao.idlenetwork.module.account.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.account.mvc.business.IAccountBusiness;
import com.ipoxiao.idlenetwork.module.account.mvc.business.impl.AccountBusinessImpl;
import com.ipoxiao.idlenetwork.module.account.mvc.controller.IAccountController;
import com.ipoxiao.idlenetwork.module.common.domain.Area;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IValidateCodeBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.ValidateCodeBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IValidateCodeController;
import com.ipoxiao.idlenetwork.module.common.ui.AreaActivity;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;
import com.ipoxiao.idlenetwork.module.home.ui.MainActivity;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.view.annotation.ViewInject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RegistActivity extends ActionBarOneActivity implements View.OnClickListener, IValidateCodeController, IAccountController {

    public static final int REQUEST_AREA = 10;
    public static final int REQUEST_INDUSTRY = 11;

    @ViewInject(R.id.layout_area)
    private RelativeLayout mLayoutArea;
    @ViewInject(R.id.layout_industry)
    private RelativeLayout mLayoutIndustry;

    @ViewInject(R.id.tv_area)
    private TextView mTextArea;
    @ViewInject(R.id.tv_industry)
    private TextView mTextIndustry;

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
    @ViewInject(R.id.btn_regist)
    private Button mBtnRegist;

    private Area area;
    private ChatSession industry;

    private IValidateCodeBusiness mValidateCodeBusiness;
    private IAccountBusiness mAccountBusiness;

    private ScheduledExecutorService mExecutorService; //timing service
    private Runnable mSecondTask;
    private int mCountDown;

    @Override
    protected int getCustomView() {
        return R.layout.activity_account_regist;
    }

    @Override
    protected void initListener() {
        mLayoutArea.setOnClickListener(this);
        mLayoutIndustry.setOnClickListener(this);
        mTextVerify.setOnClickListener(this);
        mBtnRegist.setOnClickListener(this);
    }

    @Override
    protected void initSubView() {
        initTitle("注册");
        mValidateCodeBusiness = new ValidateCodeBusinessImpl(this);
        mAccountBusiness = new AccountBusinessImpl(this);
        mSecondTask = new SecondTask();
        mExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_area:
                Intent areaIntent = new Intent(this, AreaActivity.class);
                startActivityForResult(areaIntent, REQUEST_AREA);
                break;
            case R.id.layout_industry:
                Intent industryIntent = new Intent(this, IndustryActivity.class);
                if (area == null) {
                    ViewUtil.showToast(getContext(), "区域不能为空");
                    return;
                }
                industryIntent.putExtra("id", area.getId());
                startActivityForResult(industryIntent, REQUEST_INDUSTRY);
                break;
            case R.id.tv_verify:
                String localMobile = mEditUsername.getText().toString().trim();
                mValidateCodeBusiness.getCode(localMobile, IValidateCodeBusiness.TYPE_REGIST);
                break;
            case R.id.btn_regist:

                if (area == null) {
                    ViewUtil.showToast(getContext(), "地区不能为空");
                    return;
                }

                if (industry == null) {
                    ViewUtil.showToast(getContext(), "行业不能为空");
                    return;
                }

                String mobile = mEditUsername.getText().toString().trim();
                String passwd = mEditPasswd.getText().toString().trim();
                String rePasswd = mEditPasswdConfirm.getText().toString().trim();
                String code = mEditVerify.getText().toString().trim();

                mAccountBusiness.register(mobile, passwd, rePasswd, code, area.getCityindex(), area.getId(), industry.getId());

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_AREA:
                    area = (Area) data.getSerializableExtra("object");
                    mTextArea.setText(area.getName());
                    break;
                case REQUEST_INDUSTRY:
                    industry = (ChatSession) data.getSerializableExtra("object");
                    mTextIndustry.setText(industry.getTitle());
                    break;
            }
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
    public void onComplete(User user, boolean isSuccess) {
        if (isSuccess) {
            PreferencesUtil.getInstance(this).put("username", mEditUsername.getText().toString().trim());
            PreferencesUtil.getInstance(this).put("password", mEditPasswd.getText().toString().trim());
            PreferencesUtil.getInstance(this).put(user, User.class);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
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
