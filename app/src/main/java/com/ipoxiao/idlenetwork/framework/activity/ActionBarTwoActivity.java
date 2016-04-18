package com.ipoxiao.idlenetwork.framework.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.mvc.control.impl.BaseControllerActivity;

import org.xutils.x;


/**
 * Activity Layout file with view_action_bar_one can extends the class
 * Created by Administrator on 2015/10/20.
 */
public abstract class ActionBarTwoActivity extends BaseControllerActivity {

    protected TextView mTextTitle;
    protected ImageButton mImgBack;
    protected TextView mTextAction;

    @Override
    protected void initView() {
        x.view().inject(this);
        mTextTitle = (TextView) findViewById(R.id.tv_title);
        mTextAction = (TextView) findViewById(R.id.tv_action);
        mImgBack = (ImageButton) findViewById(R.id.ibtn_back);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackEvent();
            }
        });

        mTextAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionEvent();
            }
        });

        initSubView();
    }

    protected abstract void initSubView();


    /**
     * @param textTitle
     */
    protected void initTitle(String textTitle) {
        mTextTitle.setText(textTitle);
    }

    /**
     * @param text
     */
    protected void initActionText(String text) {
        mTextAction.setText(text);
    }

    /**
     *
     */
    protected void onBackEvent() {
        finish();
    }

    protected abstract void onActionEvent();


    /**
     * @param visibility
     */
    protected void setActionTextVisibility(int visibility) {
        mTextAction.setVisibility(visibility);
    }

}
