package com.ipoxiao.idlenetwork.module.mine.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.common.Common;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.chat.ui.GroupChatActivity;
import com.ipoxiao.idlenetwork.module.chat.ui.PersonChatActivity;
import com.ipoxiao.idlenetwork.module.common.dialog.MessageDialog;
import com.ipoxiao.idlenetwork.module.common.domain.Friend;
import com.ipoxiao.idlenetwork.module.common.domain.FriendInfo;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IFriendBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.FriendBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IFriendController;
import com.ipoxiao.idlenetwork.module.mine.domain.FriendApply;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.view.CircleImageView;

import org.xutils.view.annotation.ViewInject;

import java.net.URI;
import java.util.List;

public class PersonalHomeActivity extends ActionBarOneActivity implements View.OnClickListener, IFriendController {

    public static final int OK_APPLY = 10;
    public static final int OK_DELETE = 20;


    @ViewInject(R.id.iv_portrait)
    private CircleImageView mImgPortrait;
    @ViewInject(R.id.tv_name)
    private TextView mTextName;

    @ViewInject(R.id.tv_action)
    private TextView mTextAction;
    @ViewInject(R.id.tv_send)
    private TextView mTextSend;

    @ViewInject(R.id.tv_area)
    private TextView mTextArea;
    @ViewInject(R.id.tv_industry)
    private TextView mTextIndustry;
    @ViewInject(R.id.tv_phone)
    private TextView mTextPhone;

    @ViewInject(R.id.layout_phone)
    private RelativeLayout mLayoutPhone;
    private MessageDialog mMessageDialog;

    private IFriendBusiness mFriendBusiness;
    private FriendInfo mFriendInfo;

    @Override
    protected int getCustomView() {
        return R.layout.activity_mine_personal_home;
    }

    @Override
    protected void initListener() {
        mTextAction.setOnClickListener(this);
        mTextSend.setOnClickListener(this);
        mMessageDialog.setYesClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFriendInfo.getIs_friends() == 0) {
                    //no
                    mFriendBusiness.applyFriend(mFriendInfo.getId());
                } else {
                    mFriendBusiness.deleteFriend(mFriendInfo.getId());
                }

                mMessageDialog.closeDialog();

            }
        });
    }

    private void init() {
        BitmapUtil.displayImage(mImgPortrait, mFriendInfo.getHead_link());
        mTextName.setText(mFriendInfo.getNickname());
        initTitle(mFriendInfo.getNickname() + "的主页");
        if (mFriendInfo.getIs_friends() == 0) {
            mTextAction.setText("添加好友");
            mMessageDialog.setContent("是否发送添加好友申请?");
        } else {
            mTextAction.setText("删除好友");
            mMessageDialog.setContent("是否删除好友?");
        }

        try {
            String[] dists = mFriendInfo.getCityindex_link().split("-");
            mTextArea.setText(dists[2]);
        } catch (Exception e) {

        }
        mTextIndustry.setText(mFriendInfo.getTrade_link());
        mTextPhone.setText(mFriendInfo.getMobile());
        mLayoutPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mFriendInfo.getMobile()));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initSubView() {
        mMessageDialog = new MessageDialog(this);
        mFriendBusiness = new FriendBusinessImpl(this);
        mFriendBusiness.getFriendInfo(getIntent().getStringExtra("id"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_action:
                mMessageDialog.startDialog();
                break;
            case R.id.tv_send:
                Intent intent = new Intent(this, PersonChatActivity.class);
                intent.putExtra("id", mFriendInfo.getId());
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onComplete(Action_Friend action, List<Friend> friends, int page) {

    }

    @Override
    public void onComplete(List<FriendInfo> friendInfos) {
        if (friendInfos != null && friendInfos.size() > 0) {
            FriendInfo info = friendInfos.get(0);
            this.mFriendInfo = info;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    init();
                }
            });
        }
    }

    @Override
    public void onComplete(Action_Friend action) {
        Intent intent = new Intent();
        switch (action) {
            case apply:
                intent.putExtra("STATUS", OK_APPLY);
                setResult(RESULT_OK, intent);
                break;
            case delete:
                intent.putExtra("STATUS", OK_DELETE);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }


    }

    @Override
    public void onComplete(List<FriendApply> applies, int page) {

    }
}
