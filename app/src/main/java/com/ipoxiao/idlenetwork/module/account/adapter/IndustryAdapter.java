package com.ipoxiao.idlenetwork.module.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;

/**
 * Created by Administrator on 2015/12/25.
 */
public class IndustryAdapter extends BaseAdapter<ChatSession, IndustryAdapter.ViewHolder> {


    public IndustryAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(ChatSession bean, ViewHolder holder, final int position) {

        holder.mTextName.setText(bean.getTitle());

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
        View view = mLayoutInflater.inflate(R.layout.view_common_item_text_one, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextName;
        private LinearLayout mLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            mTextName = (TextView) itemView.findViewById(R.id.tv_name);
            mLayout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }

}
