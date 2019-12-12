package com.xiaoyi.bis.xiaoyi.videoApi;
/**
 * 获取保利威视频播放token
 * @author CJ
 * @date 2019/11/25
 */
public interface QueryVideoTokenAPI {

    QueryVideoTokenResponse process(QueryVideoTokenRequest request);

    QueryVideoTokenResponse processJson(String json);

}
