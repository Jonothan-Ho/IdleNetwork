package com.ipoxiao.idlenetwork.common;

/**
 * Created by Administrator on 2015/10/15.
 */
public class Common {
    /****************************
     * Config Name
     **************************************/
    public static final String LOG_TAG_NAME = "log";

    /***************************
     * Cache Name
     **************************************/
    public static final String CACHE_IMAGE_NAME_DIR = "image_cache";
    public static final String CACHE_PHOTO_NAME_DIR = "photo_cache";


    /****************************
     * Cache Size
     **************************************/
    public static final int CACHE_IMAGE_MEMORY_SIZE = 1024 * 1024 * 10; //10M
    public static final int CACHE_IMAGE_DISK_SIZE = 1024 * 1024 * 100; //100M
    public static final int CACHE_BITMAP_MAX_SIZE = 150; //150kb
    public static int hx_count=0;//环信消息数量
    public static int jp_count=0;//极光消息数


    /**************
     * Dynamic Type {@link com.ipoxiao.idlenetwork.module.chat.ui.ChatHomeActivity},link the class;
     * ********************/
    public static final int TYPE_CHATHOME_DYNAMIC_CONSTRUCTION = 1;
    public static final int TYPE_CHATHOME_DYNAMIC_COMMENT = 2;
    public static final int TYPE_CHATHOME_DYNAMIC_GOODS = 3;

}
