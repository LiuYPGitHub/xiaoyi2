package com.xiaoyi.bis.xiaoyi.videoApi;

import java.security.NoSuchAlgorithmException;

/**
 * 合并直播录制文件回调接口
 * @author CJ
 * @date 2019/10/22
 */
public interface MergeRecordFileCallBackAPI {
    /**
     * 合并智能录播视频之后根据保利威回调修改内部数据
     * @param request
     * @return
     */
    MergeRecordFileCallBackResopnse process(MergeRecordFileCallBackRequest request) throws NoSuchAlgorithmException;

}
