package com.ipoxiao.idlenetwork.framework.listener;

import android.view.View;

/**
 * {@link android.support.v7.widget.RecyclerView} and {@link com.ipoxiao.idlenetwork.framework.adapter.BaseAdapter}
 * <p/>
 * Created by Administrator on 2015/10/27.
 */
public interface OnItemClickListener {

    void click(View v, int position);
}
