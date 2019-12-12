package com.xiaoyi.bis.xiaoyi.util;


import com.xiaoyi.bis.xiaoyi.constant.APIConstant;

import java.util.Arrays;
import java.util.Map;

/**
 * 保利威接口工具类
 * @author CJ
 * @date 2019/10/23
 */
public class MD5HexUtil {

    public static String Ksort(Map<String, String> paramMap){
        //对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);

//拼接有序的参数串
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(APIConstant.API_APP_SECRET_KEY);
        for (String key : keyArray)
        {
            stringBuilder.append(key).append(paramMap.get(key));
        }

        stringBuilder.append(APIConstant.API_APP_SECRET_KEY);
        String signSource = stringBuilder.toString();

        return org.apache.commons.codec.digest.DigestUtils.md5Hex(signSource).toUpperCase();
    }

}
