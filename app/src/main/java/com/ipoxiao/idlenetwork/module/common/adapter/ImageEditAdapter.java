package com.ipoxiao.idlenetwork.module.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.module.common.domain.LocalBitmap;

/**
 * Created by Administrator on 2015/12/30.
 */
public class ImageEditAdapter extends BaseAdapter<LocalBitmap, ImageEditAdapter.ViewHolder> {

    public ImageEditAdapter(Context context) {
        super(context);
    }
    public ImageEditAdapter(Context context, ImageButton imageButton) {
        super(context);
        this.mImageButton = imageButton;
    }
    private ImageButton mImageButton = null;

    @Override
    public void onBindViewHolder(LocalBitmap bean, final ViewHolder holder, final int position) {
        holder.mImgView.setImageBitmap(bean.getBitmap());
        holder.mTextDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageEditAdapter.this.removeData(position);
                if (ImageEditAdapter.this.mImageButton != null) {
                    if (ImageEditAdapter.this.getItemCount() == 9) {
                        ImageEditAdapter.this.mImageButton.setVisibility(View.GONE);
                    } else {
                        ImageEditAdapter.this.mImageButton.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_common_edit_image, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextDelete;
        public ImageView mImgView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            mImgView = (ImageView) itemView.findViewById(R.id.iv_image);

        }
    }

}
