package com.ipoxiao.idlenetwork.module.common.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/4.
 */
public class Ad implements Serializable {

    private String id;
    private String title;
    private String imgpic;
    private String imgpic_link;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public String getImgpic() {
        return imgpic;
    }

    public void setImgpic(String imgpic) {
        this.imgpic = imgpic;
    }

    public String getImgpic_link() {
        return imgpic_link;
    }

    public void setImgpic_link(String imgpic_link) {
        this.imgpic_link = imgpic_link;
    }
}
