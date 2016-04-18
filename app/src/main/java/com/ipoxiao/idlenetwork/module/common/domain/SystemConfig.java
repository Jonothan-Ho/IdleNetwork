package com.ipoxiao.idlenetwork.module.common.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/19.
 */
public class SystemConfig implements Serializable {

    private float member_redpaper_revenue;
    private float member_switch_num;
    private float member_vip_price;
    private float member_cash_revenue;
    private float member_cash_money;
    private String app_help;
    private int member_count;
    public void setMember_count(int member_count) {
        this.member_count = member_count;
    }

    public int getMember_count() {
        return member_count;
    }


    public String getApp_help() {
        return app_help;
    }

    public void setApp_help(String app_help) {
        this.app_help = app_help;
    }

    public float getMember_redpaper_revenue() {
        return member_redpaper_revenue;
    }

    public void setMember_redpaper_revenue(float member_redpaper_revenue) {
        this.member_redpaper_revenue = member_redpaper_revenue;
    }

    public float getMember_switch_num() {
        return member_switch_num;
    }

    public void setMember_switch_num(float member_switch_num) {
        this.member_switch_num = member_switch_num;
    }

    public float getMember_vip_price() {
        return member_vip_price;
    }

    public void setMember_vip_price(float member_vip_price) {
        this.member_vip_price = member_vip_price;
    }

    public float getMember_cash_revenue() {
        return member_cash_revenue;
    }

    public void setMember_cash_revenue(float member_cash_revenue) {
        this.member_cash_revenue = member_cash_revenue;
    }

    public float getMember_cash_money() {
        return member_cash_money;
    }

    public void setMember_cash_money(float member_cash_money) {
        this.member_cash_money = member_cash_money;
    }

    @Override
    public String toString() {
        return "SystemConfig{" +
                "member_redpaper_revenue=" + member_redpaper_revenue +
                ", member_switch_num=" + member_switch_num +
                ", member_vip_price=" + member_vip_price +
                ", member_cash_revenue=" + member_cash_revenue +
                ", member_cash_money=" + member_cash_money +
                '}';
    }
}
