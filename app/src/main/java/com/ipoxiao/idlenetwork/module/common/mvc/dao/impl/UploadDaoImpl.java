package com.ipoxiao.idlenetwork.module.common.mvc.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.http.ObjectRequestCallBack;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.framework.mvc.dao.impl.BaseDao;
import com.ipoxiao.idlenetwork.module.common.domain.LocalBitmap;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IUploadController;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IUploadDao;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.http.RequestParams;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 */
public class UploadDaoImpl extends BaseDao implements IUploadDao {

    private IUploadController mUploadController;

    public UploadDaoImpl(IBaseController baseController) {
        super(baseController);
        mUploadController = (IUploadController) baseController;
    }

    @Override
    public void uploadFiles(RequestParams params, final List<LocalBitmap> files) {
        HttpUtils.post(params, new ObjectRequestCallBack<String>(ObjectRequestCallBack.Json_parser.NULL, String.class) {
            @Override
            public void onResponse(String obj, Domain domain) {
                mUploadController.closeDialog();
                ViewUtil.showToast(mBaseController.getContext(), domain.getMsg());
                if (domain.isSuccess()) {
                    String data = domain.getData();
                    JSONArray array = JSON.parseArray(data);
                    StringBuffer buffer = new StringBuffer();
                    for (int arr = 0; arr < array.size(); arr++) {
                        buffer.append(array.getString(arr));
                        if (arr != array.size() - 1) {
                            buffer.append(",");
                        }
                    }
                    mUploadController.onUpload(buffer.toString());
                }
                for (LocalBitmap localBitmap:files){//图片上传成功删除 图片
                    if(localBitmap.getFile().exists()){
                        localBitmap.getFile().delete();
                    }
                }
            }
        });
    }
}
