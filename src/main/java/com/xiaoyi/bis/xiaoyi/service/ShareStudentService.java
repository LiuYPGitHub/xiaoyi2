package com.xiaoyi.bis.xiaoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.xiaoyi.bean.ShareStudentBean;
import com.xiaoyi.bis.xiaoyi.bean.StudentBean;
import com.xiaoyi.bis.xiaoyi.dao.ShareStudentMapper;
import com.xiaoyi.bis.xiaoyi.domain.ShareStudent;
import com.xiaoyi.bis.xiaoyi.dto.QueryShareStudentRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShareStudentService extends IService<ShareStudent> {

    /**
     * 导入添加课程分享课节学生
     * @param students
     */
    void saveShareStudent(Integer recordId,List<StudentBean> students);

    /**
     * 正在添加课程分享课节学生
     * @param request 用户输入信息
     */
    int saveShareStudent(QueryShareStudentRequest request);

    /**
     * 正在添加课程分享课节学生
     * @param request 用户输入信息
     */
    int saveShareStudent(ShareStudent request);

    /**
     * 正在添加课程分享课节学生
     * @param request 用户输入信息
     */
    int saveShareStudent(ShareStudentBean request);


    /**
     * 获取智能录播分享学生列表
     * @param request
     * @return
     */
    List<ShareStudent> queryShareStudentByLike(QueryShareStudentRequest request);

    /**
     * 根据分享学生编号获取信息
     * @param id
     * @return
     */
    ShareStudent queryShareStudentById(@Param(value = "id")Integer id);

}
