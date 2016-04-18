package com.ipoxiao.idlenetwork.module.common.mvc.dao;

import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;
import com.ipoxiao.idlenetwork.module.common.domain.LocalBitmap;

import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 */
public interface IUploadDao extends IBaseDao {

    public static final String URL_UPLOAD = "files/image/upload";


    void uploadFiles(RequestParams params,List<LocalBitmap> files);

}
