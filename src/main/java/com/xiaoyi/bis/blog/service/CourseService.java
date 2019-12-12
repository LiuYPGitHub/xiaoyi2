package com.xiaoyi.bis.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.blog.domain.Course;
import com.xiaoyi.bis.blog.domain.Event;

public interface CourseService extends IService<Course> {

    /**
     * 通过用id查找活动
     *
     * @return
     */
    PageInfo<Course> getById(String userId, String coTitle, int pageNum, int pageSize);

    /**
     * 通过用id查找用户 草稿箱
     *
     * @return
     */
    PageInfo<Course> finddraftById(String userId, String coTitle, int pageNum, int pageSize);

    /**
     * 删除动态
     *
     * @param coId
     */
    void updateEvent(String coId);

    /**
     * 名师草稿箱-课程发布
     */
    void releaseEvent(Course course);

    //  回显
    Course finddEchoById(String coId);

    /**
     * 添加
     *
     * @param course
     */
    void addCourse(Course course);

    /**
     * 修改
     *
     * @param course
     */
    void updateCourse(Course course);

}
