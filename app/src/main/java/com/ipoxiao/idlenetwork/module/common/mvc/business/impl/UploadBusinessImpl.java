package com.ipoxiao.idlenetwork.module.common.mvc.business.impl;

import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.domain.LocalBitmap;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IUploadBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IUploadDao;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.UploadDaoImpl;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;

import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 */
public class UploadBusinessImpl extends BaseService implements IUploadBusiness {

    private IUploadDao dao;

    public UploadBusinessImpl(IBaseController controller) {
        super(controller);
        dao = new UploadDaoImpl(controller);
    }

    @Override
    public void upload(List<LocalBitmap> files,String type) {

        StringBuffer buffer = new StringBuffer(IUploadDao.URL_UPLOAD);
        buffer.append("?type=").append(type);

        RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(buffer.toString()));
        for (int i = 0; i < files.size(); i++) {
            params.addBodyParameter("file[]", files.get(i).getFile());
        }

        //params.addBodyParameter("type", type);
        params.setMultipart(true);

        mController.openDialog();

        dao.uploadFiles(params,files);

    }
}
