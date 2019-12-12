package com.xiaoyi.bis.xiaoyi.util;

import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author CJ
 * @date 2019/11/6
 */
public class DateUtil {

    /**
     * 获取当前日期的毫秒数
     * @return
     */
    public static String getNowDate(){
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        return String.valueOf(instance.getTimeInMillis());
    }

    /**
     * 判断两个日期时间是否在某个指定时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return   
     */
    public static boolean checkDate(Date nowTime, Date beginTime, Date endTime) {
        //设置当前时间
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        //设置开始时间
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        //设置结束时间
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        //处于开始时间之后，和结束时间之前的判断
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回日时分秒
     *
     * @param second
     * @return
     */
    public static String secondToTime(long second) {
        long days = second / 86400;
        second = second % 86400;
        long hours = second / 3600;
        second = second % 3600;
        long minutes = second / 60;
        second = second % 60;
        if (0 < days) {
            return days + "天" + hours + ":" + minutes + ":" + second + ":";
        } else {
            return hours + ":" + minutes + ":" + second;
        }
    }

    public static Long getRecordHour(long startTime, long endTime) {
        if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime) && startTime != 0 && endTime != 0) {
            return startTime - endTime;
        } else {
            return Long.valueOf(1);
        }
    }

}
