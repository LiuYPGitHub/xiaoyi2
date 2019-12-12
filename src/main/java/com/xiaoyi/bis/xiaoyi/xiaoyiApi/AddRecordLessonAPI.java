package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

/**
 * 添翼申学-添加录播课节接口
 * @author CJ
 * @date 2019/10/31
 */
public interface AddRecordLessonAPI {

    AddRecordLessonResponse process(AddRecordLessonRequest request);

    AddRecordLessonResponse processJson(String json);

}
