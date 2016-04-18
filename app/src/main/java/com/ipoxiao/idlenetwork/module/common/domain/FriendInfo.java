package com.ipoxiao.idlenetwork.module.common.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/11.
 */
@Table(name = "USER")
public class FriendInfo implements Serializable {

    @Column(name = "id",isId = true,autoGen = false)
    private String id;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "hx_password")
    private String hx_password;
    @Column(name = "cityindex")
    private String cityindex;
    @Column(name = "dist")
    private String dist;
    @Column(name = "trade")
    private String trade;
    @Column(name = "balance")
    private String balance;
    @Column(name = "fate")
    private String fate;
    @Column(name = "is_vip")
    private String is_vip;
    @Column(name = "end_time")
    private String end_time;
    @Column(name = "head")
    private String head;
    @Column(name = "head_link")
    private String head_link;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "app_token")
    private String app_token;
    @Column(name = "is_friends")
    private int is_friends;
    @Column(name = "trade_link")
    private String trade_link;
    @Column(name = "cityindex_link")
    private String cityindex_link;

    public String getTrade_link() {
        return trade_link;
    }

    public void setTrade_link(String trade_link) {
        this.trade_link = trade_link;
    }

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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFate() {
        return fate;
    }

    public void setFate(String fate) {
        this.fate = fate;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
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
        return nickname.isEmpty()?"匿名":nickname;
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

    public int getIs_friends() {
        return is_friends;
    }

    public void setIs_friends(int is_friends) {
        this.is_friends = is_friends;
    }

    @Override
    public String toString() {
        return "FriendInfo{" +
                "id='" + id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", hx_password='" + hx_password + '\'' +
                ", cityindex='" + cityindex + '\'' +
                ", dist='" + dist + '\'' +
                ", trade='" + trade + '\'' +
                ", balance='" + balance + '\'' +
                ", fate='" + fate + '\'' +
                ", is_vip='" + is_vip + '\'' +
                ", end_time='" + end_time + '\'' +
                ", head='" + head + '\'' +
                ", head_link='" + head_link + '\'' +
                ", nickname='" + nickname + '\'' +
                ", app_token='" + app_token + '\'' +
                ", is_friends=" + is_friends +
                ", trade_link='" + trade_link + '\'' +
                ", cityindex_link='" + cityindex_link + '\'' +
                '}';
    }
}
