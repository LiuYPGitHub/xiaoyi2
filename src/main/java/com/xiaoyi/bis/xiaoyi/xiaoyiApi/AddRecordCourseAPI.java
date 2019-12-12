package com.xiaoyi.bis.xiaoyi.xiaoyiApi;
/**
 * 添翼申学-添加录播接口
 * @author CJ
 * @date 2019/10/31
 */
public interface AddRecordCourseAPI {

    AddRecordCourseResponse process(AddRecordCourseRequest request);

    void checkParms(AddRecordCourseRequest request);

    AddRecordCourseResponse processJson(String json);

}
