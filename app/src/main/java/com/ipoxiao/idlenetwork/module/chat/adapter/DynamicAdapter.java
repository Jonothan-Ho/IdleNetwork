package com.ipoxiao.idlenetwork.module.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.module.common.domain.Dynamic;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.view.CircleImageView;

/**
 * Created by Administrator on 2015/12/31.
 */
public class DynamicAdapter extends BaseAdapter<Dynamic, DynamicAdapter.ViewHolder> {

    public DynamicAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(Dynamic bean, ViewHolder holder, final int position) {
        BitmapUtil.displayImage(holder.mImgPortrait, bean.getHead_link());
        holder.mTextDate.setText(bean.getCreate_time());
        holder.mTextContent.setText(bean.getTitle());
        if (mItemClickListener != null) {
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.click(v, position);
                }
            });
        }

        if (bean.getStatus() == 1) {
            holder.mTextStatus.setVisibility(View.VISIBLE);
        } else {
            holder.mTextStatus.setVisibility(View.GONE);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_chat_dynamic, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView mImgPortrait;
        public TextView mTextDate;
        public TextView mTextContent;
        private RelativeLayout mLayout;
        private TextView mTextStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgPortrait = (CircleImageView) itemView.findViewById(R.id.iv_portrait);
            mTextDate = (TextView) itemView.findViewById(R.id.tv_date);
            mTextContent = (TextView) itemView.findViewById(R.id.tv_content);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.layout);
            mTextStatus = (TextView) itemView.findViewById(R.id.tv_status);
        }
    }

}
