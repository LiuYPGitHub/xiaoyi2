package com.xiaoyi.bis.xiaoyi.dao;

import com.xiaoyi.bis.xiaoyi.domain.LiveCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LiveCourseMapper {

    /**
     * 获取直播课程列表
     * @param course
     * @return
     */
    List<LiveCourse> selectCourse(LiveCourse course);

    /**
     * 根据直播课程编号获取直播课程信息
     * @param id
     * @return
     */
    LiveCourse selectById(@Param(value = "id") Integer id);

    /**
     * 修改直播课程信息
     * @param course
     * @return
     */
    int updateCourse(LiveCourse course);

    /**
     * 新增直播课程信息
     * @param course
     * @return
     */
    int insertCourse(LiveCourse course);

    /**
     * 根据用户编号获取机构代码
     * @param userId
     * @return
     */
    String getOrgIdByUserId(@Param(value = "userId")String userId);

    /**
     * 根据用户编号获取机构名称
     * @param userId
     * @return
     */
    String getSiteNameByUserId(@Param(value = "userId")String userId);

    /**
     * 根据用户编号获取机构昵称
     * @param userId
     * @return
     */
    String getNickNameByUserId(@Param(value = "userId")String userId);

    /**
     * 判断登陆用户是否为管理员权限
     * @param userId
     * @return
     */
    String isAdmin(@Param(value = "userId") String userId);

    /**
     * 删除直播课程信息
     * @param id
     * @return
     */
    int deleteCourse(@Param(value = "id")Integer id);

}
