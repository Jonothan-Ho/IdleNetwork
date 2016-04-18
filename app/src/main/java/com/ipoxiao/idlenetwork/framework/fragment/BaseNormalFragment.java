package com.ipoxiao.idlenetwork.framework.fragment;

import android.os.Bundle;


import com.ipoxiao.idlenetwork.framework.mvc.control.impl.BaseControllerFragment;

import org.xutils.x;

/**
 * normal fragment extends the fragment
 * <p/>
 * Created by Administrator on 2015/10/26.
 */
public abstract class BaseNormalFragment extends BaseControllerFragment {


    @Override
    protected void initView(Bundle savedInstanceState) {
        x.view().inject(this, rootView);
        initSubView(savedInstanceState);
    }

    public abstract void initSubView(Bundle savedInstanceState);

}
