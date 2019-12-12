package com.xiaoyi.bis.xiaoyi.xiaoyiApi;
/**
 * 添翼申学-添加教师接口
 * @author CJ
 * @date 2019/10/12
 */
public interface AddTeacherAPI {

    AddTeacherResponse process(AddTeacherRequest request) throws Exception;

    void checkParms(AddTeacherRequest request);

    String processJson(String json);

}
