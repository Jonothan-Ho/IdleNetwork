package com.ipoxiao.idlenetwork.module.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.framework.adapter.decoration.SpaceItemDecoration;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.module.chat.ui.ChatHomeActivity;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSessionGroup;
import com.ipoxiao.idlenetwork.utils.CommonUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class ChatSessionGroupAdapter extends BaseAdapter<ChatSessionGroup, ChatSessionGroupAdapter.ViewHolder> {

    private int mTouchSlop;
    private int mYDown = 0;
    private int mYLast = 0;

    private onLoadMoreListener mLoadMoreListener;

    public ChatSessionGroupAdapter(Context context, onLoadMoreListener loadMoreListener) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mLoadMoreListener = loadMoreListener;
    }

    @Override
    public void onBindViewHolder(ChatSessionGroup bean, ViewHolder holder, int position) {
        List<ChatSession> chatSessions = bean.getChatSessions();
        ChatSessionAdapter adapter = (ChatSessionAdapter) holder.mRecyclerView.getAdapter();
        adapter.replaceData(chatSessions);

        /*if (position == getItemCount() - 1) {
            mLoadMoreListener.loadMore();
        }*/

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_recyclerview_one, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView mRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position == 5 ? 2 : 1;
                }
            });
            mRecyclerView.setLayoutManager(gridLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
            ChatSessionAdapter mSessionAdapter = new ChatSessionAdapter((Activity) context);
            mSessionAdapter.addOnItemClickListener(new ChatSessionItemListener(mSessionAdapter));
            mRecyclerView.setAdapter(mSessionAdapter);
        }

            /*mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();

                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // 按下
                            mYDown = (int) event.getRawY();
                            LogUtil.printf(mYDown + "==>down");
                            break;

                        case MotionEvent.ACTION_MOVE:
                            // 移动
                            mYLast = (int) event.getRawY();
                            LogUtil.printf(mYLast + "==>move");
                            break;

                        case MotionEvent.ACTION_UP:
                            // 抬起
                           *//* if (isBottom() && ((mYDown - mYLast) >= mTouchSlop)) {
                                mLoadMoreListener.loadMore();
                            }*//*

                            if (isBottom()) {
                               // mLoadMoreListener.loadMore();
                            }

                            break;
                        case MotionEvent.ACTION_CANCEL:
                            *//*if (isBottom() && ((mYDown - mYLast) >= mTouchSlop)) {
                                mLoadMoreListener.loadMore();
                            }*//*

                            if (isBottom() && ((mYDown - mYLast) >= 10)) {
                                //mLoadMoreListener.loadMore();
                            }

                            break;
                        default:
                            break;
                    }

                    // LogUtil.printf(mYDown + "==>" + mYLast);

                    return false;
                }
            });
        }

        private boolean isBottom() {
            if (mRecyclerView != null && mRecyclerView.getAdapter() != null) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                return layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1;
            }
            return false;
        }*/

    }

    private class ChatSessionItemListener implements OnItemClickListener {

        public ChatSessionAdapter adapter;

        public ChatSessionItemListener(ChatSessionAdapter adapter) {
            this.adapter = adapter;
        }


        @Override
        public void click(View v, int position) {

            if (!CommonUtil.isLogged(context, true))
                return;

            //into the chatHomeActivity
            Intent intent = new Intent(context, ChatHomeActivity.class);
            intent.putExtra("id", adapter.getItem(position).getId());
            intent.putExtra("module_type",adapter.getItem(position).getModule_type());
            context.startActivity(intent);
        }
    }


    public interface onLoadMoreListener {
        void loadMore();
    }

}
