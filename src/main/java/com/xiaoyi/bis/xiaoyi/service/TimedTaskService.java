package com.xiaoyi.bis.xiaoyi.service;

import java.security.NoSuchAlgorithmException;

public interface TimedTaskService {


    /**
     * 更新智能录播-精编版课节视频解码状态
     * @return
     */
    String updateVideoStatus() throws NoSuchAlgorithmException;

    /**
     * 更新智能录播-完整版课节视频解码状态
     * @return
     */
    String updateVideoAllStatus() throws NoSuchAlgorithmException;

}
