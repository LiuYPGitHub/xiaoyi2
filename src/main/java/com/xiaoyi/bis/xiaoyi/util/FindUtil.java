package com.xiaoyi.bis.xiaoyi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 搜索工具类
 * @author CJ
 * @date 2019/11/12
 */
public class FindUtil {

    /**
     * 获取指定字符串出现的次数
     * @param srcText 源字符串
     * @param findText 要查找的字符串
     * @return
     */
    public static int findCount(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

}
