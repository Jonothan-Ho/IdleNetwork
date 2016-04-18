package com.ipoxiao.idlenetwork.module.home.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/24.
 */
public class ChatCategory implements Serializable {

    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
