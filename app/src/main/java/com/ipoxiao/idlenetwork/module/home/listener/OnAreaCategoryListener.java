package com.ipoxiao.idlenetwork.module.home.listener;

import com.ipoxiao.idlenetwork.module.common.domain.Area;

/**
 * Created by Administrator on 2015/12/25.
 */
public interface OnAreaCategoryListener {

    public static final int AREA_PROVINCE = 0;
    public static final int AREA_CITY = 1;
    public static final int AREA_DISTRICT = 2;

    void onAreaSelected(int category, Area area);
}
