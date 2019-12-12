package com.xiaoyi.bis.xiaoyi.videoApi;

import java.security.NoSuchAlgorithmException;

/**
 * 获取单个视频信息
 * @author CJ
 * @date 2019/12/5
 */
public interface GetVideoOneInfoAPI {

    GetVideoOneInfoResopnse process(GetVideoOneInfoRequest request) throws NoSuchAlgorithmException;

    GetVideoOneInfoResopnse processJson(String json);

}
