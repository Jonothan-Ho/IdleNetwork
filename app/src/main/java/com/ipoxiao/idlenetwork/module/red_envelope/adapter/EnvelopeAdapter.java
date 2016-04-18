package com.ipoxiao.idlenetwork.module.red_envelope.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Envelope;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;

/**
 * Created by Administrator on 2015/12/28.
 */
public class EnvelopeAdapter extends BaseAdapter<Envelope, EnvelopeAdapter.ViewHolder> {

    public EnvelopeAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(Envelope bean, ViewHolder holder, final int position) {
//        BitmapUtil.displayImage(holder.mImgView, bean.getImgpic_link());
        holder.mTextDate.setText(bean.getCreate_time());
        holder.mTextName.setText("发布人："+bean.getNickname());
        holder.mTextTitle.setText(bean.getTitle());

        if (mItemClickListener != null) {
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.click(v, position);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_envelope_main, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
//        public ImageView mImgView;
        public TextView mTextTitle;
        public TextView mTextName;
        public TextView mTextDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
//            mImgView = (ImageView) itemView.findViewById(R.id.iv_image);
            mTextName = (TextView) itemView.findViewById(R.id.tv_name);
            mTextTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTextDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }


}
