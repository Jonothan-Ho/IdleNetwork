package com.ipoxiao.idlenetwork.module.account.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.account.mvc.business.IAccountBusiness;
import com.ipoxiao.idlenetwork.module.account.mvc.business.impl.AccountBusinessImpl;
import com.ipoxiao.idlenetwork.module.account.mvc.controller.IAccountController;
import com.ipoxiao.idlenetwork.module.common.dialog.MessageDialog;
import com.ipoxiao.idlenetwork.module.home.ui.MainActivity;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;

import org.xutils.view.annotation.ViewInject;

public class LoginActivity extends ActionBarOneActivity implements View.OnClickListener, IAccountController {

    @ViewInject(R.id.et_username)
    private EditText mEditUser;
    @ViewInject(R.id.et_password)
    private EditText mEditPasswd;

    @ViewInject(R.id.tv_forget)
    private TextView mTextForget;
    @ViewInject(R.id.tv_regist)
    private TextView mTextRegist;
    @ViewInject(R.id.btn_login)
    private Button mBtnLogin;

    private MessageDialog mMessageDialog;

    private IAccountBusiness mAccountBusiness;

    @Override
    protected int getCustomView() {
        return R.layout.activity_account_login;
    }

    @Override
    protected void initListener() {
        mTextForget.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mTextRegist.setOnClickListener(this);
        mMessageDialog.setYesClickListener(this);
        mMessageDialog.setYesClickListener(this);
        mMessageDialog.setNoClickListener(this);
    }

    @Override
    protected void initSubView() {
        initTitle("登录");
        String username = PreferencesUtil.getInstance(this).get("username");
        if (username != null && !username.isEmpty()) {
            mEditUser.setText(username);
            mEditPasswd.requestFocus();
        }
        mMessageDialog = new MessageDialog(this);
        mMessageDialog.setContent("登录账户或密码错误");
        mMessageDialog.setYesText("重新输入");
        mMessageDialog.setNoText("忘记密码");

        mAccountBusiness = new AccountBusinessImpl(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String username = mEditUser.getText().toString().trim();
                String passwd = mEditPasswd.getText().toString().trim();
                mAccountBusiness.login(username, passwd);
                break;
            case R.id.tv_regist:
                Intent registIntent = new Intent(this, RegistActivity.class);
                startActivity(registIntent);
                break;
            case R.id.tv_forget:
            case R.id.tv_no:
                if (mMessageDialog.isShow()) {
                    mMessageDialog.closeDialog();
                }
                Intent forgetIntent = new Intent(this, ForgetActivity.class);
                startActivity(forgetIntent);
                break;
            case R.id.tv_yes:
                mEditPasswd.setText("");
                mMessageDialog.closeDialog();
                break;
        }
    }

    @Override
    public void onComplete(User user, boolean isSuccess) {
        if (!isSuccess) {
            mMessageDialog.startDialog();
            return;
        }

        PreferencesUtil.getInstance(this).put("username", mEditUser.getText().toString().trim());
        PreferencesUtil.getInstance(this).put("password", mEditPasswd.getText().toString().trim());
        PreferencesUtil.getInstance(this).put(user, User.class);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
