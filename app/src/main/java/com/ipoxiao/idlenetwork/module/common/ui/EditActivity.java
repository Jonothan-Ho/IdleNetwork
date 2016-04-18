package com.ipoxiao.idlenetwork.module.common.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarTwoActivity;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.view.annotation.ViewInject;

public class EditActivity extends ActionBarTwoActivity {

    @ViewInject(R.id.et_content)
    private EditText mEditText;

    public static final String EDIT_TITLE = "title";
    public static final String EDIT_DATA = "object";
    public static final String EDIT_TYPE = "type";

    public static final int TYPE_INTEGER = 1;
    public static final int TYPE_TEXT = 2;

    @Override
    protected void initSubView() {
        String title = getIntent().getStringExtra(EDIT_TITLE);
        initTitle(title);

        int type = getIntent().getIntExtra(EDIT_TYPE, TYPE_TEXT);
        switch (type) {
            case TYPE_INTEGER:
                mEditText.setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_VARIATION_NORMAL);
                break;
            case TYPE_TEXT:
                break;
        }


    }

    @Override
    protected void onActionEvent() {
        String content = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ViewUtil.showToast(getContext(), "编辑内容不能为空");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EDIT_DATA, content);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected int getCustomView() {
        return R.layout.activity_common_edit;
    }

    @Override
    protected void initListener() {

    }
}
