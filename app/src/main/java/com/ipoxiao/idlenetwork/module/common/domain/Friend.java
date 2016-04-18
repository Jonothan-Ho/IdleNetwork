package com.ipoxiao.idlenetwork.module.common.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/30.
 */
public class Friend implements Serializable {

    private String id;
    private String dist;
    private String cityindex;
    private String cityindex_link;
    private String nickname;
    private String head;
    private String head_link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getCityindex() {
        return cityindex;
    }

    public void setCityindex(String cityindex) {
        this.cityindex = cityindex;
    }

    public String getCityindex_link() {
        return cityindex_link;
    }

    public void setCityindex_link(String cityindex_link) {
        this.cityindex_link = cityindex_link;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHead_link() {
        return head_link;
    }

    public void setHead_link(String head_link) {
        this.head_link = head_link;
    }
}
