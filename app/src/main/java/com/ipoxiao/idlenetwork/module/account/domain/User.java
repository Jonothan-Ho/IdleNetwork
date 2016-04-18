package com.ipoxiao.idlenetwork.module.account.domain;

import com.ipoxiao.idlenetwork.utils.DateUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/4.
 */
public class User implements Serializable {

    private String id;
    private String mobile;
    private String hx_password;
    private String cityindex;
    private String dist;
    private String trade;
    private float balance;
    private int fate;
    private int is_vip;
    private String end_time;
    private String head;
    private String head_link;
    private String nickname;
    private String app_token;
    private String cityindex_link;
    private String trade_link;
    private String openid;


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCityindex_link() {
        return cityindex_link;
    }

    public void setCityindex_link(String cityindex_link) {
        this.cityindex_link = cityindex_link;
    }

    public String getTrade_link() {
        return trade_link;
    }

    public void setTrade_link(String trade_link) {
        this.trade_link = trade_link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHx_password() {
        return hx_password;
    }

    public void setHx_password(String hx_password) {
        this.hx_password = hx_password;
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

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getFate() {
        return fate;
    }

    public void setFate(int fate) {
        this.fate = fate;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = DateUtil.getDate(end_time);
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getApp_token() {
        return app_token;
    }

    public void setApp_token(String app_token) {
        this.app_token = app_token;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", hx_password='" + hx_password + '\'' +
                ", cityindex='" + cityindex + '\'' +
                ", dist='" + dist + '\'' +
                ", trade='" + trade + '\'' +
                ", balance=" + balance +
                ", fate=" + fate +
                ", is_vip=" + is_vip +
                ", end_time='" + end_time + '\'' +
                ", head='" + head + '\'' +
                ", head_link='" + head_link + '\'' +
                ", nickname='" + nickname + '\'' +
                ", app_token='" + app_token + '\'' +
                ", cityindex_link='" + cityindex_link + '\'' +
                ", trade_link='" + trade_link + '\'' +
                ", openid='" + openid + '\'' +
                '}';
    }
}
