package com.ipoxiao.idlenetwork.module.mine.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.easemob.chat.EMConversation;
import com.easemob.easeui.EaseConstant;

import com.easemob.easeui.ui.EaseConversationListFragment;

import com.ipoxiao.idlenetwork.R;

import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;

import com.ipoxiao.idlenetwork.module.chat.ui.GroupChatActivity;
import com.ipoxiao.idlenetwork.module.chat.ui.PersonChatActivity;

import com.ipoxiao.idlenetwork.widget.Constant;


public class MyMessageActivity extends ActionBarOneActivity {

    private EaseConversationListFragment conversationListFragment;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    @Override
    protected int getCustomView() {
        return R.layout.activity_mine_my_message;
    }

    @Override
    protected void initListener() {
        conversationListFragment = (EaseConversationListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_conversation);
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent talkIntent = new Intent();
                if (conversation.getIsGroup()) {
                    talkIntent.setClass(MyMessageActivity.this, GroupChatActivity.class);
                } else {
                    talkIntent.setClass(MyMessageActivity.this, PersonChatActivity.class);
                }
                talkIntent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName());
                startActivity(talkIntent);
            }
        });
        registerBroadcastReceiver();

    }

    @Override
    protected void initSubView() {
        initTitle("我的消息");
    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(conversationListFragment!=null){
                    conversationListFragment.refresh();
                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        conversationListFragment.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

}
