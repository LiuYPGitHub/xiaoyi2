package com.xiaoyi.bis.common.utils;

import com.alibaba.fastjson.JSON;

/**
 * @Description：机构关注
 * @Author：kk
 * @Date：2019/8/29 11:54
 */
public class JsonMapper {

    private JsonMapper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * json方式数据转换
     *
     * @param source           原始对象
     * @param destinationClass 目标类型
     * @return
     * @author kk 2018年2月2日 下午2:52:42
     * @date 2018年2月2日 下午2:52:42
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return JSON.parseObject(JSON.toJSONString(source), destinationClass);
    }

}
