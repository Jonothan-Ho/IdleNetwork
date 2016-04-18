package com.ipoxiao.idlenetwork.module.common.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ipoxiao.idlenetwork.utils.DateUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/24.
 */
public class Dynamic implements Serializable {

    private String id;
    private String uid;
    private String gid;
    private int status;
    private String nickname;
    private String head;
    private String title;
    private String create_time;
    private String link;
    private String head_link;

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

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = DateUtil.getDate(create_time);
    }

    public String getLink() {
        return link;
    }

    @JSONField(name = "_link")
    public void setLink(String _link) {
        this.link = _link;
    }

    public String getHead_link() {
        return head_link;
    }

    public void setHead_link(String head_link) {
        this.head_link = head_link;
    }
}
