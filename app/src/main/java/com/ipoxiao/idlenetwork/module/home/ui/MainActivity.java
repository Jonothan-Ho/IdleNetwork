package com.ipoxiao.idlenetwork.module.home.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.easeui.utils.EaseCommonUtils;
import com.easemob.exceptions.EaseMobException;
import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.common.Common;
import com.ipoxiao.idlenetwork.framework.activity.NoBarActivity;
import com.ipoxiao.idlenetwork.framework.adapter.DynamicFragmentAdapter;
import com.ipoxiao.idlenetwork.framework.adapter.FragmentTag;
import com.ipoxiao.idlenetwork.framework.common.ActivityManager;
import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.account.ui.LoginActivity;
import com.ipoxiao.idlenetwork.module.common.dialog.MessageDialog;
import com.ipoxiao.idlenetwork.module.common.domain.Ad;
import com.ipoxiao.idlenetwork.module.common.domain.Area;
import com.ipoxiao.idlenetwork.module.common.domain.Order;
import com.ipoxiao.idlenetwork.module.common.domain.ReceiveEnvelope;
import com.ipoxiao.idlenetwork.module.common.domain.SystemConfig;
import com.ipoxiao.idlenetwork.module.common.domain.Version;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IConfigBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IEnvelopeBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.ConfigBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.EnvelopeBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IConfigController;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IEnvelopeController;
import com.ipoxiao.idlenetwork.module.home.listener.OnDistListener;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IAdBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IAreaBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.ICoreBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.AdBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.AreaBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.CoreBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IAdController;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IAreaController;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.ICoreController;
import com.ipoxiao.idlenetwork.module.common.ui.AreaActivity;
import com.ipoxiao.idlenetwork.module.common.ui.WebViewActivity;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;
import com.ipoxiao.idlenetwork.module.home.listener.OnObserverListener;
import com.ipoxiao.idlenetwork.module.home.listener.OnSearchDynamicListener;
import com.ipoxiao.idlenetwork.module.home.ui.fragment.ChatRoomFragment;
import com.ipoxiao.idlenetwork.module.home.ui.fragment.SearchChatSessionFragment;
import com.ipoxiao.idlenetwork.module.home.ui.fragment.SearchDynamicFragment;
import com.ipoxiao.idlenetwork.module.mine.domain.SystemMessage;
import com.ipoxiao.idlenetwork.module.mine.domain.WithdrawRecord;
import com.ipoxiao.idlenetwork.module.mine.ui.MineActivity;
import com.ipoxiao.idlenetwork.module.mine.ui.PayActivity;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Envelope;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Record;
import com.ipoxiao.idlenetwork.module.red_envelope.ui.EnvelopeMainActivity;
import com.ipoxiao.idlenetwork.module.red_envelope.ui.SeparateActivity;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.utils.CommonUtil;
import com.ipoxiao.idlenetwork.utils.DateUtil;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.ipoxiao.idlenetwork.view.AutoScrollViewPager;
import com.ipoxiao.idlenetwork.view.CircleImageView;
import com.ipoxiao.idlenetwork.widget.Constant;
import com.ipoxiao.idlenetwork.widget.DemoHelper;

import org.xutils.view.annotation.ViewInject;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;


/**
 * 来接手的兄弟，不好意思，代码好像很乱并且没有注释。
 * <p/>
 * 接口回调太多了，看着很乱，这套模式我自己想出来的，第一次用在这个项目上，好像有点问题，一个服务接口定义太多的回调方法、
 */

public class MainActivity extends NoBarActivity implements IEnvelopeController, IConfigController, OnDistListener, ICoreController, IAreaController, AutoScrollViewPager.OnImageViewClickListener, ChatRoomFragment.OnTabLayout, IAdController {

    public static final int REQUEST_SWITCH = 10;

    @ViewInject(R.id.viewpager)
    private AutoScrollViewPager mViewPager;
    @ViewInject(R.id.tv_area)
    private TextView mTextArea;
    @ViewInject(R.id.radioGroup)
    private RadioGroup mRadioGroup;
    @ViewInject(R.id.layout_search)
    private LinearLayout mLayoutSearch;

    @ViewInject(R.id.iv_portrait)
    private CircleImageView mImgPortrait;
    @ViewInject(R.id.tabLayout)
    private TabLayout mTabLayout;
    private DynamicFragmentAdapter mDynamicFragmentAdapter;

    @ViewInject(R.id.btn_envelope)
    private FloatingActionButton mBtnEnvelope;
    @ViewInject(R.id.et_search)
    private EditText mEditSearch;
    @ViewInject(R.id.tv_red)
    private TextView mTextTvred;


    private MessageDialog mMessageDialog;
    private IAdBusiness mAdBusiness;
    private IAreaBusiness mAreaBusiness;
    private List<Ad> ads;

    private IConfigBusiness mConfigBusiness;
    private String mCurrentAreaID = "";
    private ICoreBusiness mCoreBusiness;
    private boolean isSwitchCity = false;

    private ScheduledExecutorService mExecutorService; //timing service
    private RedEnvelopeTask mTask;
    private IEnvelopeBusiness mEnvelopeBusiness;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onComplete(Action_Envelope action, List<Envelope> envelopes, int page) {
        if (envelopes != null && envelopes.size() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mBtnEnvelope.setVisibility(View.VISIBLE);
                    mExecutorService.shutdown();
                    mExecutorService = null;
                    mTask = null;
                    IS_RED = true;
                }
            });
        } else {
            IS_RED = false;
        }
    }

    @Override
    public void onCompleteRecord(List<Record> records, int page) {

    }

    @Override
    public void onComplete(Action_Envelope action, Domain domain) {

    }

    @Override
    public void onComplete(ReceiveEnvelope envelope) {

    }

    private class RedEnvelopeTask implements Runnable {
        @Override
        public void run() {
            synchronized (MainActivity.class) {
                LogUtil.printf("distID======>" + getDistID());
                if (CommonUtil.isLogged(MainActivity.this, false) && !TextUtils.isEmpty(HttpUtils.sToken)) {
                    mEnvelopeBusiness.getEnvelopeList(getDistID(), 1);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mExecutorService != null && !mExecutorService.isShutdown()) {
            mExecutorService.shutdown();
            mExecutorService = null;
            mTask = null;
        }
        unregisterBroadcastReceiver();
//        unregisterReceiver(mReceiver);
    }

    @Override
    protected void initSubView() {
        mMessageDialog = new MessageDialog(this);
        mAdBusiness = new AdBusinessImpl(this);
        mConfigBusiness = new ConfigBusinessImpl(this);

        mAreaBusiness = new AreaBusinessImpl(this);
        if (CommonUtil.isLogged(this, false)) {
            User user = PreferencesUtil.getInstance(this).get(User.class);
            IMLogin(user.getId(), user.getHx_password().trim());
            //BitmapUtil.displayImage(mImgPortrait, user.getHead_link());
            HttpUtils.sToken = user.getApp_token();
            LogUtil.printf("token===>" + HttpUtils.sToken);
            String area = user.getCityindex_link();
            try {
                if (!TextUtils.isEmpty(area)) {
                    String areaList[] = area.split("-");
                    mTextArea.setText(areaList[2]);
                }
            } catch (Exception e) {
                mTextArea.setText("失败");
            }
            mCurrentAreaID = user.getDist();
            if (mCurrentAreaID == null) {
                mCurrentAreaID = "";
            }
            PreferencesUtil.getInstance(this).put("dist", mCurrentAreaID);
            initFragment();
            registerBroadcastReceiver();
        } else {
            mImgPortrait.setImageResource(R.mipmap.icon_portrait);
            mAreaBusiness.getAreaListByDefault();
        }

        mCoreBusiness = new CoreBusinessImpl(this);
        mConfigBusiness.getSystemConfig();
        mConfigBusiness.updateVersion();

        mAdBusiness.getAd(getDistID());

        //
        //mBtnEnvelope.setVisibility(View.GONE);
        mTask = new RedEnvelopeTask();
        mExecutorService = Executors.newSingleThreadScheduledExecutor();
        mEnvelopeBusiness = new EnvelopeBusinessImpl(this);
        mExecutorService.scheduleWithFixedDelay(mTask, 1, 50, TimeUnit.SECONDS);


    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        setMsgCount();//设置消息
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setMsgCount();
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        if (broadcastReceiver != null) {
            broadcastManager.unregisterReceiver(broadcastReceiver);
        }
    }

    private void setMsgCount() {
        Common.hx_count = EMChatManager.getInstance().getUnreadMsgsCount();
        int count = Common.hx_count + Common.jp_count;
        if (count > 0 && count < 100) {
            mTextTvred.setText(count + "");
            mTextTvred.setVisibility(View.VISIBLE);
        } else if (count >= 100) {
            mTextTvred.setText("99+");
            mTextTvred.setVisibility(View.VISIBLE);
        } else {
            mTextTvred.setVisibility(View.GONE);
        }
    }

    /**
     *
     */
    private void initFragment() {
        List<Fragment> oldFragments = getSupportFragmentManager().getFragments();
        if (oldFragments != null && oldFragments.size() > 0) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            for (Fragment fragment :
                    oldFragments) {
                transaction.remove(fragment);
            }
            transaction.commitAllowingStateLoss();
        }

        ArrayList<FragmentTag> tags = new ArrayList<>();
        tags.add(new FragmentTag(null, ChatRoomFragment.class, null));
        tags.add(new FragmentTag(null, SearchDynamicFragment.class, null));
        tags.add(new FragmentTag(null, SearchChatSessionFragment.class, null));
        mDynamicFragmentAdapter = new DynamicFragmentAdapter(this, tags, R.id.layout_frame, getSupportFragmentManager());
        mDynamicFragmentAdapter.loadFragment(0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        User user = PreferencesUtil.getInstance(this).get(User.class);
        HttpUtils.sToken = user.getApp_token();
        IMLogin(user.getId(), user.getHx_password());
        String area = user.getCityindex_link();
        try {
            if (!TextUtils.isEmpty(area)) {
                String areaList[] = area.split("-");
                mTextArea.setText(areaList[2]);
            }
            mCurrentAreaID = user.getDist();
            initFragment();
        } catch (Exception e) {
            mTextArea.setText("失败");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isSwitchCity) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                if (fragment instanceof OnObserverListener) {
                    ((OnObserverListener) fragment).onUpdate();
                }
            }

            isSwitchCity = false;
        }

        JPushInterface.onResume(this);


        User user = PreferencesUtil.getInstance(this).get(User.class);
        if (user != null) {
            BitmapUtil.displayImage(mImgPortrait, user.getHead_link());
        }
        setMsgCount();
    }


    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected int getCustomView() {
        return R.layout.activity_home_main;
    }

    @Override
    protected void initListener() {
        mBtnEnvelope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtil.isLogged(MainActivity.this, true))
                    return;

                Intent intent = new Intent(MainActivity.this, EnvelopeMainActivity.class);
                intent.putExtra("dist", getDistID());
                startActivity(intent);
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_one:

                        if (mDynamicFragmentAdapter != null) {

                            mDynamicFragmentAdapter.loadFragment(0);
                            mTabLayout.setVisibility(View.VISIBLE);
                            mLayoutSearch.setVisibility(View.GONE);
                        }
                        break;
                    case R.id.rbtn_two:
                        if (mDynamicFragmentAdapter != null) {
                            mDynamicFragmentAdapter.loadFragment(2);
                            mTabLayout.setVisibility(View.GONE);
                            mLayoutSearch.setVisibility(View.VISIBLE);
                        }
                        break;

                }
            }
        });

        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mDynamicFragmentAdapter.loadFragment(1);

                    List<Fragment> fragments = getSupportFragmentManager().getFragments();
                    if (fragments != null) {
                        for (int i = 0; i < fragments.size(); i++) {
                            Fragment fragment = fragments.get(i);
                            if (fragment instanceof OnSearchDynamicListener) {
                                ((OnSearchDynamicListener) fragment).onSearch(mEditSearch.getText().toString().trim());
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        mTextArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.isLogged(MainActivity.this, true)) {
                    User user = PreferencesUtil.getInstance(MainActivity.this).get(User.class);
                    if (user != null) {
                        if (user.getIs_vip() == 1) {
                            mCoreBusiness.switchCity();
                        } else {
                            mMessageDialog.startDialog();
                        }
                    } else {
                        mMessageDialog.startDialog();
                    }
                }
            }
        });
        mMessageDialog.setYesClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageDialog.closeDialog();
                mCoreBusiness.switchCity();
            }
        });

        mImgPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.isLogged(MainActivity.this, true)) {
                    Intent intent = new Intent(MainActivity.this, MineActivity.class);
                    startActivity(intent);

                }
            }
        });

    }

    private void buildNativeViewPager(String[] urls) {
        mViewPager.setUrls(urls);
        mViewPager.setOnImageClickListener(this);
        LinearLayout layoutIndictor = new LinearLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutIndictor.setGravity(Gravity.RIGHT);
        layoutIndictor.setPadding(0, 10, 0, 10);
        mViewPager.setIndicatorLayout(layoutIndictor, params, 25, R.drawable.selector_shape_oval_style_one);
        mViewPager.startAutoScroll(10);
    }

    @Override
    public void onImageClick(int position, View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.FLAG_URL, ads.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    @Override
    public void onAd(List<Ad> ads) {
        this.ads = ads;
        String urls[] = new String[ads.size()];
        for (int i = 0; i < ads.size(); i++) {
            urls[i] = ads.get(i).getImgpic_link();
        }

        if (urls == null || urls.length == 0) {
//            ViewUtil.showToast(this, "没有广告");
            return;
        }

        buildNativeViewPager(urls);
    }

    @Override
    public void onArea(List<Area> areas) {
        if (areas != null && areas.size() > 0) {
            mCurrentAreaID = areas.get(0).getId();
            mTextArea.setText(areas.get(0).getName());
            PreferencesUtil.getInstance(this).put("dist", mCurrentAreaID);
            User user = PreferencesUtil.getInstance(this).get(User.class);
            if (user != null) {
                user.setDist(mCurrentAreaID);
                user.setCityindex_link(areas.get(0).getCityindex_link());
                PreferencesUtil.getInstance(this).put(user, User.class);
            }
            initFragment();
        }
    }

    @Override
    public void onIndustry(List<ChatSession> chatSessions) {

    }

    @Override
    public String getDistID() {
        return mCurrentAreaID;
    }


    @Override
    public void onComplete(Action_Core action, boolean isSuccess, Domain domain) {
        if (isSuccess) {
            if (domain.getStatus() == 202) {//如果返回202跳转到支付页面
                Intent intent = new Intent(MainActivity.this, PayActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, AreaActivity.class);
                startActivityForResult(intent, REQUEST_SWITCH);
            }
        }
    }

    @Override
    public void onComplete(Order order) {

    }

    @Override
    public void onComplete(Action_Core action, String content) {

    }

    @Override
    public void onComplete(List<WithdrawRecord> records, int page) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SWITCH:
                    Area area = (Area) data.getSerializableExtra("object");
                    mCurrentAreaID = area.getId();
                    mTextArea.setText(area.getName());
                    PreferencesUtil.getInstance(this).put("dist", mCurrentAreaID);
                    isSwitchCity = true;

                    User user = PreferencesUtil.getInstance(this).get(User.class);
                    if (user != null) {
                        user.setDist(mCurrentAreaID);
                        user.setCityindex_link(area.getCityindex_link());
                        LogUtil.printf("===>" + area.getCityindex_link());
                        PreferencesUtil.getInstance(this).put(user, User.class);
                    }

                    initNewData();

                    break;
            }
        }
    }


    private void IMLogin(String username, String passwd) {
        if (DemoHelper.getInstance().isLoggedIn()) {
            if (savedInstanceState != null && savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED, false)) {
                // 防止被移除后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
                // 三个fragment里加的判断同理
                ViewUtil.showToast(MainActivity.this, "登录已经过期，请重新登录！");
                ActivityManager.getInstance().clearAll(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.this.startActivity(intent);
            } else if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
                // 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
                // 三个fragment里加的判断同理
                ViewUtil.showToast(MainActivity.this, "登录已经过期，请重新登录！");
                ActivityManager.getInstance().clearAll(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.this.startActivity(intent);
            }
            return;
        }
        EMChatManager.getInstance().login(username, passwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        EMGroupManager.getInstance().loadAllGroups();
                        EMChatManager.getInstance().loadAllConversations();
                        EMChatManager.getInstance().addConnectionListener(new MyConnectionListener());
                        ViewUtil.showToast(getContext(), "登录聊天服务器成功");
                    }
                });

               /* try {
                    List<EMGroup> groups = EMGroupManager.getInstance().getGroupsFromServer();
                    EMGroupManager.getInstance().getAllGroups().addAll(groups);
                } catch (EaseMobException e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void onError(int i, String s) {
                ViewUtil.showToast(getContext(), s + "");
                LogUtil.printf("IM==>" + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            try {
                List<EMGroup> emGroupList = EMGroupManager.getInstance().getGroupsFromServer();
                if (emGroupList != null) {
                    for (EMGroup emGroup : emGroupList) {
                        EMGroupManager.getInstance().createOrUpdateLocalGroup(emGroup);
                    }
                }
                // in case that logout already before server returns, we should return immediately
                if (!EMChat.getInstance().isLoggedIn()) {
                    return;
                }

            } catch (EaseMobException e) {

            }
            //已连接到服务器
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.CONNECTION_CONFLICT) {
                        ViewUtil.showToast(MainActivity.this, "您的帐号已经在其他设备登录，请重新登录！");
                        ActivityManager.getInstance().clearAll(MainActivity.this);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainActivity.this.startActivity(intent);
                        // 显示帐号在其他设备登陆
                    } else {

                    }
                }
            });
        }
    }

    @Override
    public void onComplete(SystemConfig config) {
        PreferencesUtil.getInstance(this).put(config, SystemConfig.class);
    }

    @Override
    public void onSystemNotify(List<SystemMessage> messages, int page) {

    }

    @Override
    public void onUpdateVersion(final Version version) {
        MessageDialog updateDialog = new MessageDialog(this);
        updateDialog.setContent(version.getDescription());
        updateDialog.setYesClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(version.getFiles_link());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        updateDialog.startDialog();
    }


    /**
     *
     */
    private void initNewData() {
        mAdBusiness.getAd(getDistID());
        mBtnEnvelope.setVisibility(View.GONE);
        if (mExecutorService == null) {
            mTask = new RedEnvelopeTask();
            mExecutorService = Executors.newSingleThreadScheduledExecutor();
            mEnvelopeBusiness = new EnvelopeBusinessImpl(this);
            mExecutorService.scheduleWithFixedDelay(mTask, 1, 50, TimeUnit.SECONDS);
        }
    }

}
