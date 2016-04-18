package com.ipoxiao.idlenetwork.module.common.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/25.
 */
public class Area implements Serializable {

    private String id;
    private String pid;
    private String name;
    private int is_trade;
    private String cityindex;
    private String cityindex_link;

    public String getCityindex_link() {
        return cityindex_link;
    }

    public void setCityindex_link(String cityindex_link) {
        this.cityindex_link = cityindex_link;
    }

    public String getCityindex() {
        return cityindex;
    }

    public void setCityindex(String cityindex) {
        this.cityindex = cityindex;
    }

    public int getIs_trade() {
        return is_trade;
    }

    public void setIs_trade(int is_trade) {
        this.is_trade = is_trade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
