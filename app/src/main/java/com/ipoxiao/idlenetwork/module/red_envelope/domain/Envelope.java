package com.ipoxiao.idlenetwork.module.red_envelope.domain;

import com.ipoxiao.idlenetwork.utils.DateUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/28.
 */
public class Envelope implements Serializable {

    private String id;
    private String uid;
    private String nickname;
    private int ulist;
    private float money;
    private String ucount;
    private String cityindex;
    private String title;
    private String imgpic;
    private String create_time;
    private String imgpic_link;
    private String num;
    private String[] imgpiclist_link;
    private String my_money;

    public String getUcount() {
        return ucount;
    }

    public void setUcount(String ucount) {
        this.ucount = ucount;
    }

    public String getMy_money() {
        return my_money;
    }

    public void setMy_money(String my_money) {
        this.my_money = my_money;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String[] getImgpiclist_link() {
        return imgpiclist_link;
    }

    public void setImgpiclist_link(String[] imgpiclist_link) {
        this.imgpiclist_link = imgpiclist_link;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUlist() {
        return ulist;
    }

    public void setUlist(int ulist) {
        this.ulist = ulist;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getCityindex() {
        return cityindex;
    }

    public void setCityindex(String cityindex) {
        this.cityindex = cityindex;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = DateUtil.getDateFormat(create_time, "yyyy-MM-dd HH:mm");
    }

    public String getImgpic_link() {
        return imgpic_link;
    }

    public void setImgpic_link(String imgpic_link) {
        this.imgpic_link = imgpic_link;
    }
}
