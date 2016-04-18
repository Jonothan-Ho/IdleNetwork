package com.ipoxiao.idlenetwork.module.common.mvc.dao.impl;

import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.http.ObjectRequestCallBack;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.framework.mvc.dao.impl.BaseDao;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IValidateCodeController;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IValidateCodeDao;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/1/5.
 */
public class ValidateCodeDaoImpl extends BaseDao implements IValidateCodeDao {

    private IValidateCodeController mCodeController;

    public ValidateCodeDaoImpl(IBaseController baseController) {
        super(baseController);
        mCodeController = (IValidateCodeController) baseController;
    }

    @Override
    public void getCode(RequestParams params) {
        HttpUtils.post(params, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mCodeController.closeDialog();
                ViewUtil.showToast(mCodeController.getContext(), domain.getMsg());
                mCodeController.onCode(domain.getData());
            }
        });
    }
}
