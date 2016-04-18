package com.ipoxiao.idlenetwork.module.mine.domain;

import com.ipoxiao.idlenetwork.utils.DateUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/20.
 */
public class SystemMessage implements Serializable {

    private String id;
    private String create_time;
    private String title;
    private int type;
    private String form_id;
    private String remark;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = DateUtil.getDateSeconds(create_time);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
