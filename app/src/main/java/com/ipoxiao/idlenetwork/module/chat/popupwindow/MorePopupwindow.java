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
public class MorePopupwindow extends BasePopupWindow implements View.OnClickListener {

    private TextView mTextTop;
    private TextView mTextDelete;


    public MorePopupwindow(Activity activity) {
        super(activity);
    }

    @Override
    protected int getCustomView() {
        return R.layout.popupwindow_chat_more;
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
        mTextTop = (TextView) rootView.findViewById(R.id.tv_top);
        mTextDelete = (TextView) rootView.findViewById(R.id.tv_delete);

        mTextTop.setOnClickListener(this);
        mTextDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.tv_top:
                mItemClickListener.click(v, 0);
                break;
            case R.id.tv_delete:
                mItemClickListener.click(v, 1);
                break;
        }
    }
}
