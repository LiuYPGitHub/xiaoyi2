package com.xiaoyi.bis.user.utils;

import java.time.*;
import java.util.Date;

/**
 * @Description：时间工具类
 * @Author：kk
 * @Date：2019/9/27 10:26
 */
public class DateUtils {


    /**
     * DateToLocalDate
     */
    public static LocalDate UDateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        final LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }
}
