package com.ipoxiao.idlenetwork.module.chat.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter;
import com.ipoxiao.idlenetwork.framework.adapter.decoration.DividerItemDecoration;
import com.ipoxiao.idlenetwork.module.chat.domain.DynamicComment;
import com.ipoxiao.idlenetwork.module.common.adapter.ImageAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hacker on 16-4-16.
 */
public class DynamicCommentAdapter extends BaseAdapter<DynamicComment, DynamicCommentAdapter.ViewHolder> {

    private String mImgs[] = {"http://s7.sinaimg.cn/mw690/005C4eaXzy6RUm8uGQma6&690", "http://img.zhishizhan.net/uploads_so/140713/1405212974228547.jpg", "http://img.zhishizhan.net/uploads_so/140713/1405212925680641.jpg", "http://img4.imgtn.bdimg.com/it/u=1682548970,1698021074&fm=206&gp=0.jpg", "http://static.i3.xywy.com/cms/20150318/03a69d730e38cee7f553ec075f0acc0653343.jpg"};
    private List<String> mImgsList;

    public DynamicCommentAdapter(Context context) {
        super(context);
        mImgsList = Arrays.asList(mImgs);
    }

    @Override
    public void onBindViewHolder(DynamicComment bean, ViewHolder holder, int position) {

        ImageAdapter adapter = (ImageAdapter) holder.mRecyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(mImgsList);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_chat_dynamic_comment_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImgPortrait;
        public TextView mTextName;
        public TextView mTextDate;
        public TextView mTextAddress;
        public TextView mTextContent;
        public RecyclerView mRecyclerView;
        public TextView mTextCall;
        public TextView mTextTalk;
        public TextView mTextComment;
        public LinearLayout mLayoutComments;


        public ViewHolder(View itemView) {
            super(itemView);
            mImgPortrait = (ImageView) itemView.findViewById(R.id.iv_portrait);
            mTextName = (TextView) itemView.findViewById(R.id.tv_name);
            mTextDate = (TextView) itemView.findViewById(R.id.tv_date);
            mTextAddress = (TextView) itemView.findViewById(R.id.tv_address);
            mTextContent = (TextView) itemView.findViewById(R.id.tv_content);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
            mTextCall = (TextView) itemView.findViewById(R.id.tv_call);
            mTextComment = (TextView) itemView.findViewById(R.id.tv_comment);
            mTextTalk = (TextView) itemView.findViewById(R.id.tv_talk);
            mLayoutComments = (LinearLayout) itemView.findViewById(R.id.layout_comments);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL_LIST));
            ImageAdapter imageAdapter = new ImageAdapter(context);
            imageAdapter.setMargin(0,0,10,0);
            mRecyclerView.setAdapter(imageAdapter);

        }
    }
}
