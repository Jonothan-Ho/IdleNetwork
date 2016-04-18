package com.ipoxiao.idlenetwork.module.chat.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.common.Common;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.common.dialog.MessageDialog;
import com.ipoxiao.idlenetwork.module.home.domain.ChatCategory;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSessionInfo;
import com.ipoxiao.idlenetwork.module.home.mvc.business.ICategoryBusiness;
import com.ipoxiao.idlenetwork.module.home.mvc.business.impl.CategoryBusinessImpl;
import com.ipoxiao.idlenetwork.module.home.mvc.controller.ICategoryController;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.view.CircleImageView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * chat room home activity
 *
 * */

public class ChatHomeActivity extends ActionBarOneActivity implements View.OnClickListener, ICategoryController {




    @ViewInject(R.id.iv_portrait)
    private CircleImageView mImgPortrait;
    @ViewInject(R.id.tv_name)
    private TextView mTextName;

    @ViewInject(R.id.tv_action)
    private TextView mTextAction;
    @ViewInject(R.id.tv_send)
    private TextView mTextSend;

    @ViewInject(R.id.tv_chat_name)
    private TextView mTextChat;
    @ViewInject(R.id.tv_industry)
    private TextView mTextIndustry;
    @ViewInject(R.id.tv_area)
    private TextView mTextArea;
    @ViewInject(R.id.tv_dongtai)
    private TextView mTextDongtai;

    @ViewInject(R.id.layout_member)
    private RelativeLayout mLayoutMember;
    @ViewInject(R.id.layout_dynamic)
    private RelativeLayout mLayoutDynamic;

    private ICategoryBusiness mCategoryBusiness;
    private ChatSessionInfo mInfo;


    @Override
    protected int getCustomView() {
        return R.layout.activity_chat_home;
    }

    @Override
    protected void initListener() {
        mTextAction.setOnClickListener(this);
        mTextSend.setOnClickListener(this);
        mLayoutDynamic.setOnClickListener(this);
        mLayoutMember.setOnClickListener(this);
    }


    /**
     *
     */
    private void init() {
        BitmapUtil.displayImage(mImgPortrait, mInfo.getImgpic_link());
        mTextName.setText(mInfo.getTitle());
        mTextChat.setText(mInfo.getCategory_title());
        mTextDongtai.setText(mInfo.getTitle()+"动态");
        String dist = mInfo.getCityindex_link();
        try {
            String[] dists = dist.split("-");
            mTextArea.setText(dists[2]);
        } catch (Exception e) {
        }

        if (mInfo.getIs_join() == 0) {
            //join
            mTextAction.setText("加入该群");
        } else {
            //exit
            mTextAction.setText("退出该群");
        }
    }

    @Override
    protected void initSubView() {
        initTitle("聊天室主页");
        mCategoryBusiness = new CategoryBusinessImpl(this);
        mCategoryBusiness.getChatSessionInfo(getIntent().getStringExtra("id"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_action:
                if (mInfo != null) {
                    if (mInfo.getIs_join() == 0) {
                        //join
                        mCategoryBusiness.postJoinGroup(mInfo.getId());
                    } else {
                        //exit
                        mCategoryBusiness.deleteExitGroup(mInfo.getId());
                    }
                }
                break;
            case R.id.tv_send:
                if (mInfo != null) {
                    if (mInfo.getIs_join() == 0) {
                        //join
                        mCategoryBusiness.postJoinGroup(mInfo.getId());
                    } else {
                        //chat
                        Intent intent = new Intent(this, GroupChatActivity.class);
                        intent.putExtra("id", mInfo.getGroup_id());
//                        intent.putExtra("title",mInfo.getTitle());
                        startActivity(intent);
                    }
                }
                break;
            case R.id.layout_member:
                Intent memberIntent = new Intent(this, MemberActivity.class);
                memberIntent.putParcelableArrayListExtra("object", (ArrayList<? extends Parcelable>) mInfo.getGroupUsers());
                startActivity(memberIntent);
                break;
            case R.id.layout_dynamic:
                //dynamic
                checkDynamicActivityByType();

              /*  Intent dynamicIntent = new Intent(this, ChatDynamicActivity.class);
                dynamicIntent.putExtra("id", mInfo.getId());
                dynamicIntent.putExtra("isJoin", mInfo.getIs_join());
                startActivity(dynamicIntent);*/

                break;
        }
    }

    @Override
    public void onCategoryList(List<ChatCategory> categories) {

    }

    @Override
    public void onChatSessionInfo(final ChatSessionInfo info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mInfo = info;
                init();
            }
        });
    }

    @Override
    public void onChatSessionList(List<ChatSession> sessions, Domain domain) {

    }

    @Override
    public void onComplete(Category_Action action) {
        switch (action) {
            case Group:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mInfo.setIs_join(1);
                        mTextAction.setText("退出该群");
                    }
                });
                break;
            case Exit:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mInfo.setIs_join(0);
                        mTextAction.setText("加入该群");
                    }
                });
                break;
        }
    }


    /**
     * check DynamicActivity by ChatSession of Module_Type
     */
    private void checkDynamicActivityByType() {
        int moduleType = getIntent().getIntExtra("module_type", Common.TYPE_CHATHOME_DYNAMIC_COMMENT);
        Intent dynamicIntent = new Intent();
        switch (moduleType) {
            case Common.TYPE_CHATHOME_DYNAMIC_COMMENT:
                dynamicIntent.setClass(this,DynamicCommentActivity.class);
                break;
           /* case Common.TYPE_CHATHOME_DYNAMIC_CONSTRUCTION:
                break;
            case Common.TYPE_CHATHOME_DYNAMIC_GOODS:
                break;*/
            default:
                dynamicIntent.setClass(this,DynamicCommentActivity.class);
                break;
        }
        startActivity(dynamicIntent);
    }

}
