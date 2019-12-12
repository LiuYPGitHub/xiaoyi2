package com.xiaoyi.bis.xiaoyi.videoApi;

import java.security.NoSuchAlgorithmException;

/**
 * 获取指定频道单个录制视频
 * @author CJ
 * @date 2019/10/23
 */
public interface QueryVideoOneAPI {

    QueryVideoOneResponse process(QueryVideoOneRequest request) throws NoSuchAlgorithmException;

    QueryVideoOneResponse processJson(String json);

}
