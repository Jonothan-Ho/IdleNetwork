package com.ipoxiao.idlenetwork.framework.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;


import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.easeui.controller.EaseUI;
import com.easemob.easeui.domain.EaseUser;
import com.easemob.easeui.model.EaseNotifier;
import com.easemob.easeui.utils.EaseCommonUtils;
import com.easemob.exceptions.EaseMobException;
import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.common.Common;
import com.ipoxiao.idlenetwork.framework.db.DbUtil;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.chat.ui.GroupChatActivity;
import com.ipoxiao.idlenetwork.module.chat.ui.PersonChatActivity;
import com.ipoxiao.idlenetwork.module.common.domain.Friend;
import com.ipoxiao.idlenetwork.module.common.domain.FriendInfo;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IFriendBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.FriendBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IFriendController;
import com.ipoxiao.idlenetwork.module.mine.domain.FriendApply;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.ipoxiao.idlenetwork.widget.DemoHelper;
import com.pizidea.imagepicker.ui.A3App;

import org.xutils.x;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2015/12/23.
 */
public class IdleApplication extends Application implements IFriendController {

    private static IdleApplication application;
    private IFriendBusiness mFriendBusiness;

    /**
     * @return
     */
    public static IdleApplication getInstance() {
        return application;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //init xutils
        application = this;

        mFriendBusiness = new FriendBusinessImpl(this);

        initXUtils();

//        initIM();
        DemoHelper.getInstance().init(this);
        initJPush();
        A3App.initImageLoader(this);//图片预览插件

    }

    /**
     * init xutils
     */
    private void initXUtils() {
        x.Ext.init(this);
        x.Ext.setDebug(false); // 是否输出debug日志
    }

    /**
     *
     */
    private void initIM() {
        EMChat.getInstance().setAutoLogin(false);
        EaseUI.getInstance().init(this);
        EMChat.getInstance().init(this);

        /**
         * debugMode == true 时为打开，sdk 会在log里输入调试信息
         * @param debugMode
         * 在做代码混淆的时候需要设置成false
         */
        EMChat.getInstance().setDebugMode(true);//在做打包混淆时，要关闭debug模式，避免消耗不必要的资源
        setGlobalListeners(this);
//        registerEventListener(this);

        initUserProfileProvider();

        initNotifyClickListener();


    }


    /**
     *
     */
    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    /**
     * 设置全局事件监听
     */
    protected void setGlobalListeners(final Context appContext) {

        registerEventListener(appContext);
    }

    /**
     * 全局事件监听
     * 因为可能会有UI页面先处理到这个消息，所以一般如果UI页面已经处理，这里就不需要再次处理
     * activityList.size() <= 0 意味着所有页面都已经在后台运行，或者已经离开Activity Stack
     */
    public void registerEventListener(final Context appContext) {

        EMEventListener eventListener = new EMEventListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onEvent(EMNotifierEvent event) {
                EMMessage message = null;
                if (event.getData() instanceof EMMessage) {
                    message = (EMMessage) event.getData();
                }
                switch (event.getEvent()) {
                    case EventNewMessage:
                        if (message != null) {
                            try {
                                String id = message.getStringAttribute("id");
                                String url = message.getStringAttribute("url");
                                String nickname = message.getStringAttribute("nickname");
                                FriendInfo info = DbUtil.getInstance().findById(id, FriendInfo.class);
                                if (info != null) {
                                    if (!TextUtils.isEmpty(nickname) && !info.getNickname().equals(nickname)) {
                                        info.setNickname(nickname);
                                    }
                                    if (!TextUtils.isEmpty(url) && !info.getHead_link().equals(url)) {
                                        info.setHead_link(url);
                                    }
                                    DbUtil.getInstance().update(info);
                                }
                                if (message.isUnread()) {
                                    Common.hx_count++;
                                    Intent intent = new Intent();
                                    intent.setAction("com.byread.dynamic");
                                    sendBroadcast(intent);
                                }
                                LogUtil.printf(id + "==>" + url + "==>" + nickname);
                            } catch (EaseMobException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //应用在后台，不需要刷新UI,通知栏提示新消息
                        if (!EaseUI.getInstance().hasForegroundActivies()) {
                            EaseUI.getInstance().getNotifier().onNewMsg(message);

                        }
                        break;
                    case EventOfflineMessage:
                        List<EMMessage> listMsg = (List<EMMessage>) event.getData();
                        Common.hx_count = listMsg.size();
                        if (Common.hx_count > 0) {
                            Intent intent = new Intent();
                            intent.setAction("com.byread.dynamic");
                            sendBroadcast(intent);
                        }
                        if (!EaseUI.getInstance().hasForegroundActivies()) {
                        }
                        break;
                    // below is just giving a example to show a cmd toast, the app should not follow this
                    // so be careful of this
                    case EventNewCMDMessage: {

                        LogUtil.printf("收到透传消息");
                        //获取消息body
                        CmdMessageBody cmdMsgBody = (CmdMessageBody) message.getBody();
                        final String action = cmdMsgBody.action;//获取自定义action

                        //获取扩展属性 此处省略
                        //message.getStringAttribute("");
                        LogUtil.printf(String.format("透传消息：action:%s,message:%s", action, message.toString()));
                        final String str = appContext.getString(R.string.receive_the_passthrough);

                        final String CMD_TOAST_BROADCAST = "easemob.demo.cmd.toast";
                        IntentFilter cmdFilter = new IntentFilter(CMD_TOAST_BROADCAST);

                        if (broadCastReceiver == null) {
                            broadCastReceiver = new BroadcastReceiver() {

                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    // TODO Auto-generated method stub
                                    ViewUtil.showToast(appContext, intent.getStringExtra("cmd_value"));
                                }
                            };

                            //注册广播接收者
                            appContext.registerReceiver(broadCastReceiver, cmdFilter);
                        }

                        Intent broadcastIntent = new Intent(CMD_TOAST_BROADCAST);
                        broadcastIntent.putExtra("cmd_value", str + action);
                        appContext.sendBroadcast(broadcastIntent, null);

                        break;
                    }
                    case EventDeliveryAck:
                        message.setDelivered(true);
                        break;
                    case EventReadAck:
                        message.setAcked(true);
                        break;
                    // add other events in case you are interested in
                    default:
                        break;
                }

            }
        };

        EMChatManager.getInstance().registerEventListener(eventListener);
    }


    private void initUserProfileProvider() {
        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                FriendInfo info = DbUtil.getInstance().findById(username, FriendInfo.class);
                EaseUser user = null;
                if (info != null) {
                    User users = PreferencesUtil.getInstance(getContext()).get(User.class);
                    if (users != null && users.getId().equals(username)) {//如果是自己就去users值
                        info.setHead_link(users.getHead_link());
                        info.setNickname(users.getNickname());
                    }
                    LogUtil.printf("setUserProfileProvider_info===>" + info);
                    user = new EaseUser(username);
                    user.setAvatar(info.getHead_link());
                    user.setNick(info.getNickname());
                } else {
                    mFriendBusiness.getFriendInfo(username);
                }
                return user;
            }
        });

    }

    private void initNotifyClickListener() {
        //不设置，则使用easeui默认的
        EaseUI.getInstance().getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //修改标题,这里使用默认
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //设置小图标，这里为默认
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = EaseCommonUtils.getMessageDigest(message, IdleApplication.getInstance());
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = getUserInfo(message.getFrom());
                if (user != null) {
                    return getUserInfo(message.getFrom()).getNick() + ": " + ticker;
                } else {
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {

//                return null;
                return getUserInfo(message.getFrom()).getNick() + "发来" + messageNum + "条消息";
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                //设置点击通知栏跳转事件
                Intent intent = new Intent();
                EMMessage.ChatType chatType = message.getChatType();
                if (chatType == EMMessage.ChatType.Chat) { // 单聊信息
                    intent.setClass(IdleApplication.getInstance(), PersonChatActivity.class);
                    intent.putExtra("id", message.getFrom());
                } else { // 群聊信息
                    intent.setClass(IdleApplication.getInstance(), GroupChatActivity.class);
                    intent.putExtra("id", message.getTo());
                }
                return intent;
            }
        });
    }


    @Override
    public void onComplete(Action_Friend action, List<Friend> friends, int page) {

    }

    @Override
    public void onComplete(List<FriendInfo> friendInfos) {
        if (friendInfos != null && friendInfos.size() > 0) {
            FriendInfo info = friendInfos.get(0);
            synchronized (IdleApplication.class) {
                if (DbUtil.getInstance().findById(info.getId(), FriendInfo.class) == null) {
                    DbUtil.getInstance().save(info);
                }
            }
        }
    }

    @Override
    public void onComplete(Action_Friend action) {

    }

    @Override
    public void onComplete(List<FriendApply> applies, int page) {

    }

    @Override
    public View getRootView() {
        return null;
    }

    @Override
    public void openDialog() {

    }

    @Override
    public void closeDialog() {

    }

    @Override
    public Context getContext() {
        return this;
    }


    private EaseUser getUserInfo(String username) {
        //获取user信息，demo是从内存的好友列表里获取，
        //实际开发中，可能还需要从服务器获取用户信息,
        //从服务器获取的数据，最好缓存起来，避免频繁的网络请求
        FriendInfo info = DbUtil.getInstance().findById(username, FriendInfo.class);
        LogUtil.printf("setUserProfileProvider===>" + info);
        EaseUser user = null;
        if (info != null) {
            user = new EaseUser(username);
            user.setAvatar(info.getHead_link());
            user.setNick(info.getNickname());
        } else {
            mFriendBusiness.getFriendInfo(username);
        }

        return user;
    }

}
