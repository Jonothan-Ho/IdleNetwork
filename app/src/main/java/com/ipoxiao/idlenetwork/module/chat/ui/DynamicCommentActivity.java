package com.ipoxiao.idlenetwork.module.chat.ui;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarTwoActivity;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.framework.popup.BasePopupWindow;
import com.ipoxiao.idlenetwork.module.chat.adapter.DynamicCommentAdapter;
import com.ipoxiao.idlenetwork.module.chat.domain.DynamicComment;
import com.ipoxiao.idlenetwork.module.chat.popupwindow.DynamicMorePopupwindow;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.view.AppSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic Activity for comment
 *
 * @see ChatHomeActivity
 */
public class DynamicCommentActivity extends ActionBarTwoActivity implements OnItemClickListener,BasePopupWindow.OnDismissListener {

    private RecyclerView mRecyclerView;

    private DynamicCommentAdapter mAdapter;
    private DynamicMorePopupwindow mMorePopupwindow;

    @ViewInject(R.id.refresh_layout)
    private AppSwipeRefreshLayout mRefreshLayout;

    @Override
    protected void initSubView() {
        initTitle("综合模块");
        initActionText("更多");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DynamicCommentAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        List<DynamicComment> comments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            comments.add(new DynamicComment());
        }

        mAdapter.addData(comments);

        LogUtil.printf("AppSwipeRefreshLayout==>"+mRefreshLayout);
    }

    @Override
    protected void onActionEvent() {
        if (mMorePopupwindow == null) {
            mMorePopupwindow = new DynamicMorePopupwindow(this);
            mMorePopupwindow.setItemClickListener(this);
            mMorePopupwindow.setOnDismissListener(this);
        }
        mMorePopupwindow.showAsDropDown(mTextAction);
        mTextAction.setTextColor(getResources().getColor(R.color.text_grey));
    }

    @Override
    protected int getCustomView() {
        return R.layout.layout_recyclerview_title_two;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onDismiss(PopupWindow popupWindow) {
        mTextAction.setTextColor(getResources().getColor(R.color.text_white));
    }

    @Override
    public void click(View v, int position) {
        mMorePopupwindow.closePopupWindow();
        switch (position) {
            case 0:
                //publish dynamic
                break;
        }
    }
}
