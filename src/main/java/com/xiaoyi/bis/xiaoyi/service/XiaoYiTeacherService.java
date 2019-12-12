package com.xiaoyi.bis.xiaoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.xiaoyi.domain.XiaoYiTeacher;

public interface XiaoYiTeacherService extends IService<XiaoYiTeacher> {

    /**
     * 根据手机号码获取教师信息
     * @param phone
     * @return
     */
    XiaoYiTeacher queryTeacherByPhone(String phone);

    /**
     * 保存教师信息
     * @param teacher
     * @return
     */
    int saveTeacher(XiaoYiTeacher teacher);

}
