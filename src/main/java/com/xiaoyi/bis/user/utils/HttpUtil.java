package com.xiaoyi.bis.user.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private HttpUtil() {
        super();
    }

    /**
     * OkHttp 请求 post 请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Object params) {
        String content = null;
        // 创建 OkHttpClient 对象
        OkHttpClient okHttpClient = new OkHttpClient();
        if (params != null) {
            content = JSON.toJSONString(params);
        }

        // 创建 requestBody
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), content);
        // 创建 request 请求
        Request request = new Request.Builder().url(url).post(body).build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            // 判断请求是否成功
            if (response.isSuccessful()) {
                // 服务端返回结果
                return response.body().string();
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

}
