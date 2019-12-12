package com.xiaoyi.bis.xiaoyi.videoApi;
/**
 * 合并直播录制文件
 * @author CJ
 * @date 2019/10/22
 */
public interface MergeRecordFileAPI {

    MergeRecordFileResponse process(MergeRecordFileRequest request);

    MergeRecordFileResponse processJson(String json);

    void checkParms(MergeRecordFileRequest request);

}
