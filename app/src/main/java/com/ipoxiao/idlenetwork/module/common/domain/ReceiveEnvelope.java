package com.ipoxiao.idlenetwork.module.common.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class ReceiveEnvelope implements Serializable {

    private String id;
    private String money;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
