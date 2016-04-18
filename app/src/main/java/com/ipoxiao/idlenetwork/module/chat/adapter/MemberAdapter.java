package com.ipoxiao.idlenetwork.module.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.module.common.domain.Friend;
import com.ipoxiao.idlenetwork.module.home.domain.GroupUser;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.view.CircleImageView;

/**
 * Created by Administrator on 2015/12/31.
 */
public class MemberAdapter extends BaseAdapter<GroupUser, MemberAdapter.ViewHolder> {

    public MemberAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(GroupUser bean, ViewHolder holder, final int position) {
        BitmapUtil.displayImage(holder.mImgPortrait, bean.getHead_link());
        holder.mTextName.setText(bean.getNickname());
        if (mItemClickListener != null) {
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.click(v, position);
                }
            });
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_chat_member, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mLayout;
        public CircleImageView mImgPortrait;
        public TextView mTextName;

        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView.findViewById(R.id.layout);
            mImgPortrait = (CircleImageView) itemView.findViewById(R.id.iv_portrait);
            mTextName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
