package com.ipoxiao.idlenetwork.module.red_envelope.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Envelope;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.view.AutoScrollViewPager;

import org.xutils.view.annotation.ViewInject;

import java.lang.ref.SoftReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GrabRedEnvelopeActivity extends ActionBarOneActivity {


    @ViewInject(R.id.viewpager)
    private AutoScrollViewPager mViewPager;
    @ViewInject(R.id.tv_second)
    private TextView mTextSeconds;

    private ScheduledExecutorService mExecutorService; //timing service
    private int mAdTime = 5;
    private AdTask mTask;
    private static TaskHandler mTaskHandler;

    @Override
    protected int getCustomView() {
        return R.layout.activity_envelope_grab;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mExecutorService != null && !mExecutorService.isShutdown()) {
            mExecutorService.shutdown();
            mExecutorService = null;
        }
    }

    public void setCurrentSeconds() {
        mTextSeconds.setText("还有" + mAdTime + "S");
    }

    @Override
    protected void initSubView() {
        initTitle("抢红包");
        //initialize timing server
        mTask = new AdTask();
        mTaskHandler = new TaskHandler(this);
        mExecutorService = Executors.newSingleThreadScheduledExecutor();
        mExecutorService.scheduleWithFixedDelay(mTask, 1, 1, TimeUnit.SECONDS);

        Envelope envelope = (Envelope) getIntent().getSerializableExtra("object");
        buildNativeViewPager(envelope.getImgpiclist_link());
    }


    private class AdTask implements Runnable {

        @Override
        public void run() {
            synchronized (GrabRedEnvelopeActivity.class) {

                if ((mAdTime--) == 0 && !mExecutorService.isShutdown()) {
                    mExecutorService.shutdown();
                    mExecutorService = null;
                    Intent intent = new Intent(GrabRedEnvelopeActivity.this, SeparateActivity.class);
                    intent.putExtra("object", getIntent().getSerializableExtra("object"));
                    startActivity(intent);
                    finish();
                } else {
                    mTaskHandler.obtainMessage().sendToTarget();
                }
            }
        }
    }

    private void buildNativeViewPager(String[] urls) {
        mViewPager.setUrls(urls);
        mViewPager.startAutoScroll(10);
    }

    private class TaskHandler extends Handler {

        private SoftReference<GrabRedEnvelopeActivity> reference;

        public TaskHandler(GrabRedEnvelopeActivity instance) {
            reference = new SoftReference<>(instance);
        }

        @Override
        public void dispatchMessage(Message msg) {
            GrabRedEnvelopeActivity instance = reference.get();
            instance.setCurrentSeconds();
        }
    }

}
