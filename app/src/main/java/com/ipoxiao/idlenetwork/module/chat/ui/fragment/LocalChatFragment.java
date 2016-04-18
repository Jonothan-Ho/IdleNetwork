package com.ipoxiao.idlenetwork.module.chat.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.easemob.easeui.widget.chatrow.EaseChatRow;
import com.easemob.easeui.widget.emojicon.EaseEmojiconMenu;
import com.ipoxiao.idlenetwork.R;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMCmdManager;
import com.easemob.chat.EMMessage;
import com.easemob.easeui.ui.EaseChatFragment;
import com.easemob.easeui.widget.EaseChatMessageList;
import com.easemob.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.chat.ui.ChatHomeActivity;
import com.ipoxiao.idlenetwork.module.common.ui.VideoCallActivity;
import com.ipoxiao.idlenetwork.module.common.ui.VoiceCallActivity;
import com.ipoxiao.idlenetwork.module.mine.ui.MineDataActivity;
import com.ipoxiao.idlenetwork.module.mine.ui.PersonalHomeActivity;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.widget.ChatRowVoiceCall;
import com.ipoxiao.idlenetwork.widget.Constant;

import java.util.Map;

/**
 * Created by Administrator on 2016/2/17.
 */
public class LocalChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentListener {

    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;

    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        user = PreferencesUtil.getInstance(getActivity()).get(User.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
        super.setUpView();
    }

    @Override
    protected void sendMessage(EMMessage message) {
        if (EMChatManager.getInstance().isConnected()) {
            LogUtil.printf("未连接。。。。。。。。。。。。。。。。。。。。。。。。。");

        }
        message.setAttribute("id", user.getId());
        message.setAttribute("nickname", user.getNickname());
        message.setAttribute("url", user.getHead_link());
        super.sendMessage(message);
    }

    @Override
    protected void registerExtendMenuItem() {
        //demo这里不覆盖基类已经注册的item,item点击listener沿用基类的
        super.registerExtendMenuItem();
        //增加扩展item
//        inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
//        inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);
        if (chatType == Constant.CHATTYPE_SINGLE) {
            inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.em_chat_voice_call_selector, ITEM_VOICE_CALL, extendMenuItemClickListener);
//            inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.em_chat_video_call_selector, ITEM_VIDEO_CALL, extendMenuItemClickListener);
        }
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {

    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {
        User user = PreferencesUtil.getInstance(getActivity()).get(User.class);
        Intent intent = new Intent();
        if (user.getId().equals(username)) {
            intent.setClass(getActivity(), MineDataActivity.class);
        } else {
            intent.setClass(getActivity(), PersonalHomeActivity.class);
        }
        intent.putExtra("id", username);
        startActivity(intent);
    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        LogUtil.printf("-------------------------->");
        switch (itemId) {
            case ITEM_VIDEO: //视频
//                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
                break;
            case ITEM_FILE: //一般文件
                //demo这里是通过系统api选择文件，实际app中最好是做成qq那种选择发送文件
//                selectFileFromLocal();
                break;
            case ITEM_VOICE_CALL: //音频通话
                startVoiceCall();
                break;
            case ITEM_VIDEO_CALL: //视频通话
                startVideoCall();
                break;

            default:
                break;
        }
        //不覆盖已有的点击事件
        return false;

    }

    /**
     * 拨打语音电话
     */
    protected void startVoiceCall() {
        if (!EMChatManager.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, 0).show();
        } else {
            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // voiceCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * 拨打视频电话
     */
    protected void startVideoCall() {
        if (!EMChatManager.getInstance().isConnected())
            Toast.makeText(getActivity(), R.string.not_connect_to_server, 0).show();
        else {
            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }

    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //音、视频通话发送、接收共4种
            return 4;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                //语音通话类型
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    //视频通话
                    return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                // 语音通话,  视频通话
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                        message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
                }
            }
            return null;
        }
    }


}
