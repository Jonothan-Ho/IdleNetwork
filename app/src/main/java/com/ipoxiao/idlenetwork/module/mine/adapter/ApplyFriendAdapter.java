package com.ipoxiao.idlenetwork.module.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.module.mine.domain.FriendApply;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.view.CircleImageView;

/**
 * Created by Administrator on 2016/1/19.
 */
public class ApplyFriendAdapter extends BaseAdapter<FriendApply, ApplyFriendAdapter.ViewHolder> {

    private int mGreyColor;
    private int mRedColor;

    public ApplyFriendAdapter(Context context) {
        super(context);
        mGreyColor = context.getResources().getColor(R.color.text_grey);
        mRedColor = context.getResources().getColor(R.color.text_pink);
    }

    @Override
    public void onBindViewHolder(FriendApply bean, ViewHolder holder, final int position) {
        BitmapUtil.displayImage(holder.mImgPortrait, bean.getHead_link());
        holder.mTextName.setText(bean.getNickname());

        String dist = bean.getCityindex_link();
        try {
            String[] dists = dist.split("-");
            holder.mTextAddress.setText(dists[2]);
        } catch (Exception e) {

        }

        if (mItemClickListener != null) {
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.click(v, position);
                }
            });
        }

        switch (bean.getStatus()) {
            case 0:
                holder.mTextAction.setTextColor(mRedColor);
                holder.mTextAction.setText("同意");
                if (mItemClickListener != null) {
                    holder.mTextAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemClickListener.click(v, position);
                        }
                    });
                }
                break;
            case 1:
                holder.mTextAction.setTextColor(mGreyColor);
                holder.mTextAction.setText("已接受");
                break;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_mine_apply_friend, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView mImgPortrait;
        private TextView mTextName;
        private TextView mTextAddress;
        private RelativeLayout mLayout;
        private TextView mTextAction;

        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.layout);
            mImgPortrait = (CircleImageView) itemView.findViewById(R.id.iv_portrait);
            mTextName = (TextView) itemView.findViewById(R.id.tv_name);
            mTextAddress = (TextView) itemView.findViewById(R.id.tv_address);
            mTextAction = (TextView) itemView.findViewById(R.id.tv_action);
        }
    }

}
