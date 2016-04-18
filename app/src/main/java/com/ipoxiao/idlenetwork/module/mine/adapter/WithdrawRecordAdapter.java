package com.ipoxiao.idlenetwork.module.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.module.mine.domain.WithdrawRecord;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

/**
 * Created by Administrator on 2015/12/30.
 */
public class WithdrawRecordAdapter extends BaseAdapter<WithdrawRecord, WithdrawRecordAdapter.ViewHolder> /*implements StickyRecyclerHeadersAdapter<WithdrawRecordAdapter.HeaderViewHolder>*/ {


    public WithdrawRecordAdapter(Context context) {
        super(context);
    }

  /*  @Override
    public long getHeaderId(int position) {
      *//*  if (position > 0) {
            WithdrawRecord lastBean = mDataSource.get(position-1);
            if (lastBean.getCreate_time().equals(mDataSource.get(position).getCreate_time())) {
                return 0;
            }
        }*//*
        return position;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.view_mine_withdraw_record_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int position) {
        holder.mTextDate.setText(mDataSource.get(position).getCreate_time());
    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_mine_withdraw_record, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(WithdrawRecord bean, ViewHolder holder, int position) {
        holder.mTextMoney.setText(bean.getTotalmoney());
        holder.mTextDate.setText(bean.getCreate_time());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextMoney;
        public TextView mTextDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextMoney = (TextView) itemView.findViewById(R.id.tv_money);
            mTextDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextDate;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mTextDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }


}
