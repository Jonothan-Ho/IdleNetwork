package com.ipoxiao.idlenetwork.module.common.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/15.
 */
public class Order implements Serializable {

    private String id;
    private String type;
    private String create_time;
    private String args;
    private float totalmoney;
    private String ordercode;
    private String notify_url;

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public float getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(float totalmoney) {
        this.totalmoney = totalmoney;
    }
}
