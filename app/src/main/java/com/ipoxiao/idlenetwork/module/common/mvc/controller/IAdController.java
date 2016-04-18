package com.ipoxiao.idlenetwork.module.common.mvc.controller;

import com.ipoxiao.idlenetwork.framework.mvc.control.IBaseController;
import com.ipoxiao.idlenetwork.module.common.domain.Ad;

import java.util.List;

/**
 * Created by Administrator on 2016/1/4.
 */
public interface IAdController extends IBaseController {

    void onAd(List<Ad> ads);

}
