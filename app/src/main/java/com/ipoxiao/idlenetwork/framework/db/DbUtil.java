package com.ipoxiao.idlenetwork.framework.db;

import com.ipoxiao.idlenetwork.framework.app.IdleApplication;
import com.ipoxiao.idlenetwork.module.common.domain.FriendInfo;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * Created by Administrator on 2016/2/1.
 */
public class DbUtil {

    public static DbUtil instance;
    private DbManager manager;

    private DbUtil() {
        DbManager.DaoConfig config = new DbManager.DaoConfig();
        config.setAllowTransaction(true);
        config.setDbVersion(1);
        config.setDbName("IdleNetwork");
        manager = x.getDb(config);
    }

    /**
     * @return
     */
    public static DbUtil getInstance() {
        if (instance == null) {
            instance = new DbUtil();
        }

        return instance;
    }


    /**
     * @param object
     */
    public void save(Object object) {
        try {
            manager.save(object);
        } catch (DbException e) {
            e.printStackTrace();
            LogUtil.printf("save==>" + e.getMessage());
            ViewUtil.showToast(IdleApplication.getInstance(), "插入到数据库失败");
        }
    }

    /**
     * @param object
     */
    public void update(Object object) {
        try {
            manager.saveOrUpdate(object);
        } catch (DbException e) {
            e.printStackTrace();
            LogUtil.printf("update==>" + e.getMessage());
            ViewUtil.showToast(IdleApplication.getInstance(), "更新到数据库失败");
        }
    }


    /**
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T findById(String id, Class<T> clazz) {
        T obj = null;
        try {
            obj = manager.findById(clazz, id);
        } catch (DbException e) {
            e.printStackTrace();
            LogUtil.printf("find==>"+e.getMessage());
            ViewUtil.showToast(IdleApplication.getInstance(), "查询失败");
        }

        return obj;
    }

    /**
     * @param clazz
     */
    public void dropTable(Class<?> clazz) {
        try {
            manager.dropTable(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            LogUtil.printf("dropTable==>" + e.getMessage());
            ViewUtil.showToast(IdleApplication.getInstance(), "删除表失败");
        }
    }

}
