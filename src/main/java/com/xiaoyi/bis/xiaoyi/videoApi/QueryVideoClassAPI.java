package com.xiaoyi.bis.xiaoyi.videoApi;

import java.security.NoSuchAlgorithmException;

/**
 * 获取所有录制视频分类信息
 * @author CJ
 * @date 2019/10/22
 */
public interface QueryVideoClassAPI {

    QueryVideoClassResponse process(QueryVideoClassRequest request) throws NoSuchAlgorithmException;

    QueryVideoClassResponse processJson(String json);

}
