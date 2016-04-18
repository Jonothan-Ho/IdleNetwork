package com.ipoxiao.idlenetwork.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


/**
 * Created by Administrator on 2015/11/30.
 */
public class AppSwipeRefreshLayout extends SwipeRefreshLayout {


    private int mTouchSlop;
    private RecyclerView mRecyclerView;
    private OnLoadListener mLoadListener;
    private int mYDown;
    private int mYLast;
    private View mViewFooter;
    private boolean isUpLoading = false;


    public AppSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public AppSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        // mViewFooter = LayoutInflater.from(context).inflate(R.layout.view_recyclerview_footer, null, false);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mRecyclerView == null) {
            initRecyclerView();
        }
    }

    private void initRecyclerView() {
        int childs = getChildCount();
        if (childs > 0) {
            View viewChild = getChildAt(0);
            if (viewChild instanceof RecyclerView) {
                mRecyclerView = (RecyclerView) viewChild;
                mRecyclerView.setOnScrollListener(new ScrollerListener());
            }
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mYLast = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (canLoad() && isEnough()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }


    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isUpLoading && isPullUp();
    }


    /**
     * @return
     */
    private boolean isEnough() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        int lastPosition = 0;
        int count = 0;
        int maxVisibleItems = 0;

        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            lastPosition = linearLayoutManager.findLastVisibleItemPosition();
            count = linearLayoutManager.getItemCount();
            maxVisibleItems = lastPosition - linearLayoutManager.findFirstVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            lastPosition = gridLayoutManager.findLastVisibleItemPosition();
            count = gridLayoutManager.getItemCount();
            maxVisibleItems = lastPosition - gridLayoutManager.findFirstVisibleItemPosition();
        }

        if (maxVisibleItems > count) {
            return false;
        }


        return true;

    }


    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {

        if (mRecyclerView != null && mRecyclerView.getAdapter() != null) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            return layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1;
        }
        return false;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mYLast) >= mTouchSlop;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (mLoadListener != null) {
            // 设置状态
            setUpLoading(true);
            //
            mLoadListener.onPullUp();
        }
    }

    /**
     * @param loading
     */
    public void setUpLoading(boolean loading) {
        isUpLoading = loading;
        if (isUpLoading) {

        } else {
            mYDown = 0;
            mYLast = 0;
        }
    }


    /**
     *
     */
    public void stopLoading() {

        if (isUpLoading) {
            setUpLoading(false);
        }

        if (isRefreshing()) {
            setRefreshing(false);
        }


    }


    /**
     * @param loadListener
     */
    public void setOnLoadListener(OnLoadListener loadListener) {
        this.mLoadListener = loadListener;
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLoadListener.onPullDown();
            }
        });
    }


    private class ScrollerListener extends OnScrollListener {


        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // 滚动时到了最底部也可以加载更多
           /* if (canLoad() && isEnough()) {
                loadData();
            }*/
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    }

    /**
     * @return
     */
    public RecyclerView getRecyclerView() {
        if (mRecyclerView == null) {
            initRecyclerView();
        }
        return mRecyclerView;
    }


    /***************************
     * Interface
     ********************************/
    public interface OnLoadListener {
        void onPullUp();

        void onPullDown();
    }


}
