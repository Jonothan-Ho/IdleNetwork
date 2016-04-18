package com.ipoxiao.idlenetwork.module.home.domain;

import java.io.Serializable;

/**
 *
 * chat room javaBean
 *
 * Created by Administrator on 2015/12/24.
 */
public class ChatSession implements Serializable {

    private String id;
    private String title;
    private String cid;
    private String category_title;
    private String cityindex;
    private String dist;
    private String imgpic;
    private String create_time;
    private String imgpic_link;
    private int is_home;
    private int module_type; //this type determines the type of chat room dynamic list display

    public int getModule_type() {
        return module_type;
    }

    public void setModule_type(int module_type) {
        this.module_type = module_type;
    }

    public int getIs_home() {
        return is_home;
    }

    public void setIs_home(int is_home) {
        this.is_home = is_home;
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public String getCityindex() {
        return cityindex;
    }

    public void setCityindex(String cityindex) {
        this.cityindex = cityindex;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getImgpic() {
        return imgpic;
    }

    public void setImgpic(String imgpic) {
        this.imgpic = imgpic;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getImgpic_link() {
        return imgpic_link;
    }

    public void setImgpic_link(String imgpic_link) {
        this.imgpic_link = imgpic_link;
    }
}
