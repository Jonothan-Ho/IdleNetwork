package com.ipoxiao.idlenetwork.module.home.adapter;

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
 * Created by Administrator on 2015/12/24.
 */
public class SearchDynamicAdapter extends BaseAdapter<Dynamic, SearchDynamicAdapter.ViewHolder> {


    public SearchDynamicAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(Dynamic bean, ViewHolder holder, final int position) {

        BitmapUtil.displayImage(holder.mImgPortrait, bean.getHead_link());
        holder.mTextContent.setText(bean.getTitle());
        holder.mTextDate.setText(bean.getCreate_time());

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
        View view = mLayoutInflater.inflate(R.layout.view_home_search_dynamic, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView mImgPortrait;
        private TextView mTextDate;
        private TextView mTextContent;
        private RelativeLayout mLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgPortrait = (CircleImageView) itemView.findViewById(R.id.iv_portrait);
            mTextDate = (TextView) itemView.findViewById(R.id.tv_date);
            mTextContent = (TextView) itemView.findViewById(R.id.tv_content);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.layout);
        }
    }

}
