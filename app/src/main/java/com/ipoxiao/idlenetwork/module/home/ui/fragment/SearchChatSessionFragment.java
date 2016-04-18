package com.ipoxiao.idlenetwork.module.home.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.decoration.SpaceItemDecoration;
import com.ipoxiao.idlenetwork.framework.fragment.BaseNormalFragment;
import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.module.chat.ui.ChatHomeActivity;
import com.ipoxiao.idlenetwork.module.home.adapter.ChatSessionGroupAdapter;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSessionGroup;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSessionInfo;
import com.ipoxiao.idlenetwork.module.home.listener.OnDistListener;
import com.ipoxiao.idlenetwork.module.home.adapter.ChatSessionAdapter;
import com.ipoxiao.idlenetwork.module.home.domain.ChatCategory;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;
import com.ipoxiao.idlenetwork.module.home.listener.OnObserverListener;
import com.ipoxiao.idlenetwork.module.home.mvc.business.ICategoryBusiness;
import com.ipoxiao.idlenetwork.module.home.mvc.business.impl.CategoryBusinessImpl;
import com.ipoxiao.idlenetwork.module.home.mvc.controller.ICategoryController;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchChatSessionFragment extends BaseNormalFragment implements ChatSessionGroupAdapter.onLoadMoreListener, OnObserverListener, OnItemClickListener, ICategoryController {

    @ViewInject(R.id.recycler_view)
    private RecyclerViewPager mRecyclerView;
    private ChatSessionGroupAdapter mSessionAdapter;
    private ICategoryBusiness mCategoryBusiness;
    @ViewInject(R.id.tv_count)
    private TextView mTextCount;

    private int mNextPage = 1;


    @Override
    public void initSubView(Bundle savedInstanceState) {
        mSessionAdapter = new ChatSessionGroupAdapter(getActivity(), this);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setTriggerOffset(0.15f);
        mRecyclerView.setFlingFactor(0.25f);
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setAdapter(mSessionAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mCategoryBusiness = new CategoryBusinessImpl(this);
        loadMore();
    }

    @Override
    protected int getCustomView() {
        return R.layout.fragment_home_chat_session;
    }

    @Override
    protected void initListener() {
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                mPositionText.setText("First: " + mRecyclerViewPager.getFirstVisiblePosition());
                int childCount = mRecyclerView.getChildCount();
                int width = mRecyclerView.getChildAt(0).getWidth();
                int padding = (mRecyclerView.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                    }
                }
            }
        });

        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mRecyclerView.getChildCount() < 3) {
                    if (mRecyclerView.getChildAt(1) != null) {
                        View v1 = mRecyclerView.getChildAt(1);
                        v1.setScaleY(0.9f);
                    }
                } else {
                    if (mRecyclerView.getChildAt(0) != null) {
                        View v0 = mRecyclerView.getChildAt(0);
                        v0.setScaleY(0.9f);
                    }
                    if (mRecyclerView.getChildAt(2) != null) {
                        View v2 = mRecyclerView.getChildAt(2);
                        v2.setScaleY(0.9f);
                    }
                }

            }
        });

        mRecyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int i, int i1) {
                if (i1 == mSessionAdapter.getItemCount() - 1) {
                    loadMore();
                }
            }
        });
    }

    @Override
    public void click(View v, int position) {
      /*  Intent intent = new Intent(getActivity(), ChatHomeActivity.class);
        startActivity(intent);*/
    }

    @Override
    public void onCategoryList(List<ChatCategory> sessions) {

    }

    @Override
    public void onChatSessionInfo(ChatSessionInfo info) {

    }

    @Override
    public void onChatSessionList(List<ChatSession> sessions, Domain domain) {
        if (domain.isSuccess() && sessions != null && sessions.size() > 0) {
            mNextPage = domain.getPage() + 1;

            for (int i = 0; i < sessions.size(); i++) {
                ChatSession chatSession = sessions.get(i);
                if (chatSession.getIs_home() == 1) {
                    ChatSession session = sessions.remove(i);
                    sessions.add(5, session);
                    break;
                }
            }

            ChatSessionGroup group = new ChatSessionGroup();
            group.setChatSessions(sessions);
            mSessionAdapter.addData(group);
            mTextCount.setText("当前：" + domain.getTotalCount());


            if (mSessionAdapter.getItemCount() == 1) {
                loadMore();
            }

        }
    }

    @Override
    public void onComplete(Category_Action action) {

    }

    @Override
    public void onUpdate() {
        mSessionAdapter.removeAllData();
        OnDistListener distListener = (OnDistListener) getActivity();
        mNextPage = 1;
        mCategoryBusiness.getChatSessionByID(distListener.getDistID(), ICategoryBusiness.TYPE_SEARCH, mNextPage);
    }

    @Override
    public void loadMore() {
        OnDistListener distListener = (OnDistListener) getActivity();
        mCategoryBusiness.getChatSessionByID(distListener.getDistID(), ICategoryBusiness.TYPE_SEARCH, mNextPage);
    }
}
