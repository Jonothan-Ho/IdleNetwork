package com.ipoxiao.idlenetwork.module.common.mvc.business.impl;

import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IValidateCodeBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IValidateCodeDao;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.ValidateCodeDaoImpl;
import com.ipoxiao.idlenetwork.utils.TextUtil;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/1/5.
 */
public class ValidateCodeBusinessImpl extends BaseService implements IValidateCodeBusiness {

    private IValidateCodeDao dao;

    public ValidateCodeBusinessImpl(IBaseController controller) {
        super(controller);
        dao = new ValidateCodeDaoImpl(controller);
    }

    @Override
    public void getCode(String mobile, String type) {
        if (!TextUtil.isValidPhone(mobile, mController.getContext()))
            return;

        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(IValidateCodeDao.URL_CODE));
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("type", type);

        mController.openDialog();
        dao.getCode(params);
    }
}
