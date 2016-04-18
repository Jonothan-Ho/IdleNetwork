package com.ipoxiao.idlenetwork.module.red_envelope.ui;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.framework.adapter.decoration.SpaceItemDecoration;
import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.module.common.domain.ReceiveEnvelope;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IEnvelopeBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.EnvelopeBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IEnvelopeController;
import com.ipoxiao.idlenetwork.module.red_envelope.adapter.EnvelopeAdapter;
import com.ipoxiao.idlenetwork.module.red_envelope.adapter.RecordAdapter;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Envelope;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Record;
import com.ipoxiao.idlenetwork.utils.DensityUtil;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.view.AppSwipeRefreshLayout;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class SeparateActivity extends ActionBarOneActivity implements OnItemClickListener, IEnvelopeController {


    @ViewInject(R.id.refresh_layout)
    private AppSwipeRefreshLayout mRefreshLayout;

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    private RecordAdapter mRecordAdapter;

    @ViewInject(R.id.tv_record)
    private TextView mTextRecords;

    @ViewInject(R.id.tv_money)
    private TextView mTextMoney;
    @ViewInject(R.id.tv_status)
    private TextView mTextStatus;

    @ViewInject(R.id.layout_envelope)
    private LinearLayout mLayoutEnvelope;

    @ViewInject(R.id.tv_nickname)
    private TextView mTextViewNickname;
    private String mCurrentEnvelopeID;


    private IEnvelopeBusiness mEnvelopeBusiness;

    private int mNextPage = 1;
    private String mCountNum = "0";


    @Override
    protected int getCustomView() {
        return R.layout.activity_envelope_separate;
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnLoadListener(new AppSwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onPullUp() {
                mEnvelopeBusiness.getEnvelopeRecord(mCurrentEnvelopeID, mNextPage);
            }

            @Override
            public void onPullDown() {
                mNextPage = 1;
                mEnvelopeBusiness.getEnvelopeRecord(mCurrentEnvelopeID, mNextPage);
            }
        });
    }

    @Override
    protected void initSubView() {
        mRecyclerView.setPadding(16, 0, 16, 0);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecordAdapter = new RecordAdapter(this);
        mRecordAdapter.addOnItemClickListener(this);
        mRecyclerView.setAdapter(mRecordAdapter);

        Envelope envelope = (Envelope) getIntent().getSerializableExtra("object");
        mEnvelopeBusiness = new EnvelopeBusinessImpl(this);
        LogUtil.printf("envelope=====================================>)" + envelope);
        if (envelope == null) {
            mCurrentEnvelopeID = getIntent().getStringExtra("id");
            mEnvelopeBusiness.getEnvelopeByID(mCurrentEnvelopeID);
        } else {
            mCurrentEnvelopeID = envelope.getId();
            initEnvelopeInfo(envelope);
        }


        mEnvelopeBusiness.getEnvelopeRecord(mCurrentEnvelopeID, mNextPage);


    }

    private void initEnvelopeInfo(Envelope envelope) {
        String title = envelope.getTitle();
        if (!title.isEmpty() && title.length() > 10) {
            title = title.substring(0, 10) + "...";
        }
        initTitle(title);
        mCountNum = envelope.getNum();
        if (envelope.getUlist() == 0) {
            mTextMoney.setText("¥" + envelope.getMoney());
            mTextStatus.setText("领取红包");
            mLayoutEnvelope.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEnvelopeBusiness.receiveEnvelope(mCurrentEnvelopeID);
                }
            });
        } else {
            mTextMoney.setText("¥" + envelope.getMy_money());
            mTextStatus.setText("已领取");
        }
        mTextViewNickname.setText("发布人："+envelope.getNickname());
        mTextRecords.setText(String.format("已领取:%s/%s", envelope.getUcount() + "", mCountNum));
    }

    @Override
    public void click(View v, int position) {

    }

    @Override
    public void onComplete(Action_Envelope action, List<Envelope> envelopes, int page) {
        if (envelopes != null && envelopes.size() > 0) {
            final Envelope envelope = envelopes.get(0);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initEnvelopeInfo(envelope);
                }
            });
        }
    }

    @Override
    public void onCompleteRecord(final List<Record> records, int page) {
        mNextPage = page + 1;
        mRefreshLayout.stopLoading();

        if (page == 1) {
            mRecordAdapter.replaceData(records);
        } else {
            mRecordAdapter.addData(records);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextRecords.setText(String.format("已领取:%s/%s", mRecordAdapter.getItemCount(), mCountNum));
            }
        });
    }

    @Override
    public void onComplete(Action_Envelope action, Domain isSuccess) {

    }

    @Override
    public void onComplete(final ReceiveEnvelope envelope) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextMoney.setText("¥" + envelope.getMoney());
                mTextStatus.setText("已领取");
                mLayoutEnvelope.setOnClickListener(null);
            }
        });

    }
}
