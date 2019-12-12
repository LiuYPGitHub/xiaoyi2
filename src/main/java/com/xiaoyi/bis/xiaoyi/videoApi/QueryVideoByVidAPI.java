package com.xiaoyi.bis.xiaoyi.videoApi;

import java.security.NoSuchAlgorithmException;

/**
 * 根据指定Vid获取单个录制视频
 * @author CJ
 * @date 2019/11/19
 */
public interface QueryVideoByVidAPI {

    QueryVideoByVidResponse process(QueryVideoByVidRequest request) throws NoSuchAlgorithmException;

    QueryVideoByVidResponse processJson(String json);

}
