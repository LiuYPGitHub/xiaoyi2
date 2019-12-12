package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

/**
 * 添翼申学-添加直播课程接口
 * @author CJ
 * @date 2019/10/12
 */
public interface AddLiveCourseAPI {

    AddLiveCourseResponse process(AddLiveCourseRequest request) throws Exception;

    void checkParms(AddLiveCourseRequest request);

    String processJson(String json);

}
