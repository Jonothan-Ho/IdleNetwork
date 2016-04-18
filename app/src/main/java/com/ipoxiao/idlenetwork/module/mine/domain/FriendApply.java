package com.ipoxiao.idlenetwork.module.mine.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/19.
 */
public class FriendApply implements Serializable {

    private String id;
    private String uid;
    private int status;
    private String nickname;
    private String head;
    private String head_link;
    private String create_time;
    private String cityindex_link;

    public String getCityindex_link() {
        return cityindex_link;
    }

    public void setCityindex_link(String cityindex_link) {
        this.cityindex_link = cityindex_link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
