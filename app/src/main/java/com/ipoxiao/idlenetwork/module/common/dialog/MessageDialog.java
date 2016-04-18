package com.ipoxiao.idlenetwork.module.common.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.dialog.BaseDialog;
import com.ipoxiao.idlenetwork.utils.LogUtil;


/**
 * Created by Administrator on 2015/12/24.
 */
public class MessageDialog extends BaseDialog {

    private TextView mTextYes;
    private TextView mTextNo;
    private TextView mTextContent;

    public MessageDialog(Activity context) {
        super(context);
    }

    @Override
    public int getCustomView() {
        return R.layout.dialog_common_message;
    }

    @Override
    public void initView() {
        mTextYes = (TextView) findViewById(R.id.tv_yes);
        mTextNo = (TextView) findViewById(R.id.tv_no);
        mTextContent = (TextView) findViewById(R.id.tv_content);

        mTextNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });


    }

    /**
     * @param content
     */
    public void setContent(String content) {
        mTextContent.setText(content);
    }

    /**
     * @param yesClickListener
     */
    public void setYesClickListener(View.OnClickListener yesClickListener) {
        this.mTextYes.setOnClickListener(yesClickListener);
    }

    public void setNoClickListener(View.OnClickListener noClickListener) {
        this.mTextNo.setOnClickListener(noClickListener);
    }

    /**
     * @param text
     */
    public void setYesText(String text) {
        mTextYes.setText(text);
    }

    /**
     * @param text
     */
    public void setNoText(String text) {
        mTextNo.setText(text);
    }


}
