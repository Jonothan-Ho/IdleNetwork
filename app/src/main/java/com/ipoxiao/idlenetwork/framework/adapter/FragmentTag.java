package com.ipoxiao.idlenetwork.framework.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2015/12/15.
 */
public class FragmentTag {

    private  String tag;
    private  Class<?> clss;
    private  Bundle args;
    private Fragment fragment;

    public FragmentTag(String tag, Class<?> clss, Bundle args) {
        this.tag = tag;
        this.clss = clss;
        this.args = args;
    }

    public Class<?> getClss() {
        return clss;
    }

    public void setClss(Class<?> clss) {
        this.clss = clss;
    }

    public Bundle getArgs() {
        return args;
    }

    public void setArgs(Bundle args) {
        this.args = args;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
