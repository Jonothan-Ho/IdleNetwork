package com.ipoxiao.idlenetwork.module.common.mvc.dao;

import com.easemob.EMCallBack;
import com.ipoxiao.idlenetwork.framework.mvc.dao.IBaseDao;

/**
 * Created by Administrator on 2016/1/14.
 */
public interface IMDao extends IBaseDao {

    void IMlogin(String username, String passwd, EMCallBack emCallBack);

}
