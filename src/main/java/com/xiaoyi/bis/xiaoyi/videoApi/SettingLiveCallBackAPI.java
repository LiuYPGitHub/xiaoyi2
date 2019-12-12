package com.xiaoyi.bis.xiaoyi.videoApi;

/**
 * 设置录制回调通知url
 * @author CJ
 * @date 2019/10/31
 */
public interface SettingLiveCallBackAPI {

    SettingLiveCallBackResponse process(SettingLiveCallBackRequest request);

    SettingLiveCallBackResponse processJson(String json);

}
