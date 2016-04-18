package com.ipoxiao.idlenetwork.module.chat.ui;


import android.content.Intent;
import android.os.Bundle;


import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.easeui.EaseConstant;

import com.ipoxiao.idlenetwork.R;

import com.ipoxiao.idlenetwork.common.Common;
import com.ipoxiao.idlenetwork.framework.activity.NoBarActivity;
import com.ipoxiao.idlenetwork.module.chat.ui.fragment.LocalChatFragment;


public class GroupChatActivity extends NoBarActivity {
    String toChatUsername;

    @Override
    protected int getCustomView() {
        return R.layout.activity_chat_group;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initSubView() {
        toChatUsername = getIntent().getStringExtra("id");
        //new出EaseChatFragment或其子类的实例
        LocalChatFragment chatFragment = new LocalChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        args.putString(EaseConstant.EXTRA_USER_ID, toChatUsername);
        chatFragment.setArguments(args);
        EMConversation conversation = EMChatManager.getInstance().getConversation(toChatUsername);
        if (Common.hx_count > 0) {
            Common.hx_count = Common.hx_count - conversation.getUnreadMsgCount();
            conversation.markAllMessagesAsRead();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.layout_frame, chatFragment).commit();


    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
}
