package com.ipoxiao.idlenetwork.module.mine.domain;

import com.ipoxiao.idlenetwork.utils.DateUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/30.
 */
public class WithdrawRecord implements Serializable {

    private String id;
    private String totalmoney;
    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(String totalmoney) {
        this.totalmoney = totalmoney;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = DateUtil.getDateSeconds(create_time);
        //this.create_time = create_time;
    }
}
