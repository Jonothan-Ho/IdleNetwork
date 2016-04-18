package com.ipoxiao.idlenetwork.module.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;

/**
 * ImageAdapter for RecyclerView Horizontal
 *
 * Created by hacker on 16-4-18.
 */
public class ImageAdapter extends BaseAdapter<String,ImageAdapter.ViewHolder>{

    private int leftMargin;
    private int topMargin;
    private int rightMargin;
    private int bottomMargin;

    public ImageAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(String bean, ViewHolder holder, int position) {
        BitmapUtil.displayImage(holder.mImgView,bean);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_common_image,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImgView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgView = (ImageView) itemView.findViewById(R.id.iv_image);

            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(itemView.getLayoutParams());
            params.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
            itemView.setLayoutParams(params);

        }
    }


    /**
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setMargin(int left, int top, int right, int bottom) {
        this.leftMargin = left;
        this.rightMargin = right;
        this.topMargin = top;
        this.bottomMargin = bottom;
    }


}
