package com.ipoxiao.idlenetwork.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/1/19.
 */
public class DateUtil {


    /**
     * @param times
     * @return
     */
    public static String getDate(String times) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long time = Long.parseLong(times + "000");
            return df.format(time);
        } catch (Exception e) {

        }

        return times;

    }


    public static String getDateSeconds(String times) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time = Long.parseLong(times + "000");
            return df.format(time);
        } catch (Exception e) {

        }

        return times;

    }

    /**
     * 时间格式化
     * @return
     */
    public static String getDateFormat(String times,String formats){
        SimpleDateFormat df = new SimpleDateFormat(formats);
        try {
            long time = Long.parseLong(times + "000");
            return df.format(time);
        } catch (Exception e) {

        }
        return times;
    }
}
