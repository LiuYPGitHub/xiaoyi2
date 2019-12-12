package com.xiaoyi.bis.xiaoyi.util;


import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.util.StringUtils;

/**
 * @author CJ
 * @date 2019/10/15
 */
public class TimesTaskUtil {

    /**
     * 计算开始时间结束时间的毫秒数
     * @param millis
     * @return
     */
    public static String process(long start,long end) {
        long hm = end - start;
        return millisToStringShort(hm);
    }

    /**
     * 把毫秒数转换成时分秒
     * @param millis
     * @return
     */
    public static String millisToStringShort(long millis) {
        StringBuffer strBuilder = new StringBuffer();
        long temp = millis;
        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        long sper = 1000;
        if (temp / hper > 0) {
            strBuilder.append(temp / hper).append("时 ");
        }
        temp = temp % hper;

        if (temp / mper > 0) {
            strBuilder.append(temp / mper).append("分 ");
        }
        temp = temp % mper;
        if (temp / sper > 0) {
            strBuilder.append(temp / sper).append("秒 ");
        }
        if(!StringUtils.isEmpty(strBuilder.toString())){
            return "成功 总用时:"+strBuilder.toString();
        }else{
            return "成功";
        }
    }

}
