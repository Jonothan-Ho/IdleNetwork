package com.ipoxiao.idlenetwork.module.mine.ui;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;

import org.xutils.view.annotation.ViewInject;

public class MonthCheckedActivity extends ActionBarOneActivity implements View.OnClickListener {

    @ViewInject(R.id.tv_one)
    private TextView mTextOne;
    @ViewInject(R.id.tv_two)
    private TextView mTextTwo;
    @ViewInject(R.id.tv_three)
    private TextView mTextThree;
    @ViewInject(R.id.tv_four)
    private TextView mTextFour;


    @Override
    protected int getCustomView() {
        return R.layout.activity_mine_month_checked;
    }

    @Override
    protected void initListener() {
        mTextOne.setOnClickListener(this);
        mTextTwo.setOnClickListener(this);
        mTextThree.setOnClickListener(this);
        mTextFour.setOnClickListener(this);
    }

    @Override
    protected void initSubView() {
        initTitle("选择月份");
    }

    @Override
    public void onClick(View v) {
        int month = 0;
        switch (v.getId()) {
            case R.id.tv_one:
                month = 1;
                break;
            case R.id.tv_two:
                month = 3;
                break;
            case R.id.tv_three:
                month = 6;
                break;
            case R.id.tv_four:
                month = 12;
                break;
        }

        Intent intent = new Intent();
        intent.putExtra("object", month);
        setResult(RESULT_OK, intent);
        finish();
    }
}
