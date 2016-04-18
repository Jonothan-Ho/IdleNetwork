package com.ipoxiao.idlenetwork.module.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.utils.DensityUtil;


/**
 * Created by Administrator on 2015/12/24.
 */
public class ChatSessionAdapter extends BaseAdapter<ChatSession, RecyclerView.ViewHolder> {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_RECOMMEND = 1;

    private int mItemViewHeight = 0;

    public ChatSessionAdapter(Activity context) {
        super(context);
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        int mRecycleViewHeight = screenHeight - DensityUtil.dip2px(context, 120) - DensityUtil.dip2px(context, 56) - DensityUtil.dip2px(context, 48) - DensityUtil.dip2px(context, 38);
        mItemViewHeight = mRecycleViewHeight / 3 - DensityUtil.dip2px(context, 15);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_RECOMMEND:
                view = mLayoutInflater.inflate(R.layout.view_home_chat_session_recommend, parent, false);
                viewHolder = new RecommendViewHolder(view);
                break;
            case TYPE_NORMAL:
                view = mLayoutInflater.inflate(R.layout.view_home_chat_session, parent, false);
                viewHolder = new ViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 5) {
            return TYPE_RECOMMEND;
        }
        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(ChatSession bean, RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_NORMAL:
                ViewHolder viewHolder = (ViewHolder) holder;
                BitmapUtil.getInstance(context).displayImageByOptions(viewHolder.mImgView,bean.getImgpic_link());
                viewHolder.mTextName.setText(bean.getTitle());
                if (mItemClickListener != null) {
                    viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemClickListener.click(v, position);
                        }
                    });
                }

                break;
            case TYPE_RECOMMEND:
                RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
                BitmapUtil.displayImage(recommendViewHolder.mImgView, bean.getImgpic_link());
                if (mItemClickListener != null) {
                    recommendViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemClickListener.click(v, position);
                        }
                    });
                }
                break;
        }


    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public ImageView mImgView;
        public TextView mTextName;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mTextName = (TextView) itemView.findViewById(R.id.tv_name);
            mImgView = (ImageView) itemView.findViewById(R.id.iv_image);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemViewHeight);
            mCardView.setLayoutParams(params);
        }
    }


    public class RecommendViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public ImageView mImgView;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mImgView = (ImageView) itemView.findViewById(R.id.iv_image);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemViewHeight);
            mCardView.setLayoutParams(params);
        }
    }


}
