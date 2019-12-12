package com.xiaoyi.bis.xiaoyi.videoApi;

import java.security.NoSuchAlgorithmException;

/**
 * 获取指定频道所有录制视频
 * @author CJ
 * @date 2019/10/22
 */
public interface QueryVideoListAPI {

    QueryVideoListResponse process(QueryVideoListRequest request) throws NoSuchAlgorithmException;

    void checkParms(QueryVideoListRequest request);

    QueryVideoListResponse processJson(String json);

}
