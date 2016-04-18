package com.ipoxiao.idlenetwork.module.common.mvc.business.impl;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ipoxiao.idlenetwork.framework.http.HttpUtils;
import com.ipoxiao.idlenetwork.framework.mvc.business.impl.BaseService;
import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IConfigBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.IConfigDao;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.impl.ConfigDaoImpl;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/1/19.
 */
public class ConfigBusinessImpl extends BaseService implements IConfigBusiness {

    private IConfigDao dao;

    public ConfigBusinessImpl(IBaseController controller) {
        super(controller);
        dao = new ConfigDaoImpl(controller);
    }

    @Override
    public void getSystemConfig() {
        dao.getConfig(IConfigDao.URL_CONFIG);
    }

    @Override
    public void getSystemNotify(int page) {
        StringBuffer buffer = new StringBuffer(IConfigDao.URL_MESSAGE);
        buffer.append("?p=").append(page);
        dao.getNotify(buffer.toString());
    }

    @Override
    public void updateVersion() {
        PackageManager manager;
        PackageInfo info = null;
        manager = mController.getContext().getPackageManager();

        try {
            info = manager.getPackageInfo(mController.getContext().getPackageName(), 0);
            RequestParams params = new RequestParams(HttpUtils.getAbsoluteUrl(IConfigDao.URL_VERSION));
            params.addBodyParameter("version", info.versionName);
            dao.updateVersion(params);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}