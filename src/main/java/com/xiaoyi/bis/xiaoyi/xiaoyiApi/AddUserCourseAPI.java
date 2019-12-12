package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

/**
 * 添翼申学-添加用户分享接口
 * @author CJ
 * @date 2019/10/31
 */
public interface AddUserCourseAPI {

    AddUserCourseResponse process(AddUserCourseRequest request);

    AddUserCourseResponse processJson(String json);

}
