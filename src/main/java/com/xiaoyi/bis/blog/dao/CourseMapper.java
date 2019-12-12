package com.xiaoyi.bis.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyi.bis.blog.domain.Course;
import com.xiaoyi.bis.blog.domain.Event;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    List<Course> getEventById(String userId, String coTitle);

    List<Course> selectdraftById(String userId, String coTitle);

    // 删除课程
    int updateEvent(String coId);

    // 草稿箱活动发布
    int releaseEvent(Course course);

    // 回显
    Course finddEchoById(String coId);

    // add
    void insertCourse(Course course);

    // deit
    void updateCourse(Course course);

}
