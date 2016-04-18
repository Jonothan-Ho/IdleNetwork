package com.ipoxiao.idlenetwork.module.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.module.mine.domain.SystemMessage;

/**
 * Created by Administrator on 2016/1/20.
 */
public class SystemMessageAdapter extends BaseAdapter<SystemMessage, SystemMessageAdapter.ViewHolder> {

    public SystemMessageAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(SystemMessage bean, ViewHolder holder, final int position) {
        holder.mTextDate.setText(bean.getCreate_time());
        holder.mTextTitle.setText(bean.getTitle());

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
        View view = mLayoutInflater.inflate(R.layout.view_mine_system_message, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextDate;
        private RelativeLayout mLayout;
        private TextView mTextTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextDate = (TextView) itemView.findViewById(R.id.tv_date);
            mTextTitle = (TextView) itemView.findViewById(R.id.tv_content);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.layout);
        }
    }

}
