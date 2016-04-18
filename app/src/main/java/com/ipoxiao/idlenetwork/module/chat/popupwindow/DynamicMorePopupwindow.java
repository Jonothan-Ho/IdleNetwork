package com.ipoxiao.idlenetwork.module.chat.popupwindow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.popup.BasePopupWindow;


/**
 * Created by Administrator on 2015/11/9.
 */
public class DynamicMorePopupwindow extends BasePopupWindow implements View.OnClickListener {

    private TextView mTextPublish;


    public DynamicMorePopupwindow(Activity activity) {
        super(activity);
    }

    @Override
    protected int getCustomView() {
        return R.layout.popupwindow_chat_dynamic_more;
    }

    @Override
    protected int getWindowWidth() {
        return 250;
    }

    @Override
    protected int getWindowHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected void initView(View rootView) {
        mTextPublish = (TextView) rootView.findViewById(R.id.tv_publish);
        mTextPublish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.tv_publish:
                mItemClickListener.click(v, 0);
                break;
        }
    }
}
