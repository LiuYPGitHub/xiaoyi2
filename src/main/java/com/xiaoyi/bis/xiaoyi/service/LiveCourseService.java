package com.xiaoyi.bis.xiaoyi.service;

import com.xiaoyi.bis.xiaoyi.dto.PageRequest;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourse;
import com.xiaoyi.bis.xiaoyi.dto.QueryLiveCourseRequest;

import java.util.List;

public interface LiveCourseService {

    /**
     * @param request 分页参数
     * @param course 课程
     * @return 直播课程分页数据
     */
    PageResponse selectByLike(QueryLiveCourseRequest request, LiveCourse course);

    /**
     * @param id 课程编号
     * @return 课程
     */
    LiveCourse queryCourseById(Integer id);

    /**
     * @param course 直播课程
     * @return 影响行数
     */
    int updateCourse(LiveCourse course);

    /**
     * @param course 直播课程(添加教师)
     * @return 影响行数
     */
    int saveCourse(LiveCourse course);

    /**
     * @param course 直播课程(添加教师)
     * @return 影响行数
     */
    int saveCourseInfo(LiveCourse course);

    /**
     * 根据用户编号获取机构代码
     * @param userId
     * @return
     */
    String getOrgIdByUserId(String userId);

    /**
     * 根据用户编号获取机构名称
     * @param userId
     * @return
     */
    String getSiteNameByUserId(String userId);
    /**
     * 根据用户编号判断是否为管理员权限
     * @param userId
     * @return
     */
    String isAdmin(String userId);

    /**
     * 将直播课程由'未提交状态'修改为'提交状态'
     * @param id
     * @return
     */
    int sumbitCourse(Integer id);

    /**
     * 伪删除课程信息
     * @param id
     * @return
     */
    int dropCourse(Integer id);

}
