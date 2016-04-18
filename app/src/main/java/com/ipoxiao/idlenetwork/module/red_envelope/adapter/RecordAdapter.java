package com.ipoxiao.idlenetwork.module.red_envelope.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Record;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.view.CircleImageView;

/**
 * Created by Administrator on 2015/12/28.
 */
public class RecordAdapter extends BaseAdapter<Record, RecordAdapter.ViewHolder> {

    public RecordAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(Record bean, ViewHolder holder, int position) {
        BitmapUtil.displayImage(holder.mImgPortrait, bean.getHead_link());
        holder.mTextAddress.setText(bean.getCityindex_link());
        holder.mTextName.setText(bean.getNickname());
        holder.mTextDate.setText(bean.getUpdate_time());
        holder.mTextMoney.setText(bean.getMoney() + "元");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_envelope_records, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImgPortrait;
        private TextView mTextName;
        private TextView mTextMoney;
        private TextView mTextAddress;
        private TextView mTextDate;


        public ViewHolder(View itemView) {
            super(itemView);
            mImgPortrait = (ImageView) itemView.findViewById(R.id.iv_portrait);
            mTextName = (TextView) itemView.findViewById(R.id.tv_name);
            mTextMoney = (TextView) itemView.findViewById(R.id.tv_money);
            mTextAddress = (TextView) itemView.findViewById(R.id.tv_address);
            mTextDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

}
