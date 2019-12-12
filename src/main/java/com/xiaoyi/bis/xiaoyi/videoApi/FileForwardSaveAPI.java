package com.xiaoyi.bis.xiaoyi.videoApi;

/**
 * 异步批量转存录制文件到点播
 * @author CJ
 * @date 2019/10/24
 */
public interface FileForwardSaveAPI {

    FileForwardSaveResponse process(FileForwardSaveRequest request);

    FileForwardSaveResponse processJson(String json);

    void checkParms(FileForwardSaveRequest request);

}
