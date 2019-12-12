package com.xiaoyi.bis.xiaoyi.videoApi;

import java.security.NoSuchAlgorithmException;

/**
 * 异步批量转存录制文件到点播回调函数
 * @author CJ
 * @date 2019/10/24
 */
public interface FileForwardSaveCallBackAPI {

    /**
     * 智能录播同步点播之后根据保利威回调修改内部数据
     * @param request
     * @return
     */
    void process(FileForwardSaveCallBackRequest request) throws NoSuchAlgorithmException;

}
