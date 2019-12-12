package com.xiaoyi.bis.xiaoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.xiaoyi.domain.RecordCourseShare;
import com.xiaoyi.bis.xiaoyi.domain.ShareStudent;

public interface RecordCourseShareService extends IService<RecordCourseShare> {

    /**
     * 检查分享学生
     * @param classId
     * @param student
     * @return true:不存在 false:存在
     */
    boolean checkShareStudent(String classId, ShareStudent student);

    /**
     * 保存分享学生
     * @param student
     * @return
     */
    int saveShareStudent(RecordCourseShare student);

}
