package com.ipoxiao.idlenetwork.module.common.mvc.business;

import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;
import com.ipoxiao.idlenetwork.module.common.domain.LocalBitmap;

import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 */
public interface IUploadBusiness extends IBaseService {

    public static final String TYPE_SINGLE = "1";
    public static final String TYPE_MULTI = "2";

    void upload(List<LocalBitmap> files,String type);
}
