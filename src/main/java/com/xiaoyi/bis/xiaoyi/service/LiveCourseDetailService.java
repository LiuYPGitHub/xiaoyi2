package com.xiaoyi.bis.xiaoyi.service;


import com.github.pagehelper.Page;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourse;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourseDetail;
import com.xiaoyi.bis.xiaoyi.dto.PageRequest;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author CJ
 * @date 2019/10/14
 */
public interface LiveCourseDetailService {

    /**
     * 根据课程编号获取课节详情信息
     * @param courseId
     * @return
     */
    PageResponse queryCourseDetailList(Integer courseId,Integer pageIndex,Integer pageSize);

    /**
     * 根据课程编号获取直播课程详情信息
     * @param courseId 课程编号
     * @return
     */
    List<LiveCourseDetail> queryCourseDetailById(Integer courseId);

    /**
     * 根据课程编号获取直播课程详情信息
     * @param courseId 课程编号
     * @return
     */
    PageResponse queryCourseDetailById(PageRequest request, Integer courseId);

    /**
     * 新增课程详情信息
     * @param course 课程
     * @return
     */
    int saveXiaoYiCourseDetail(LiveCourse course);

    /**
     * 新增课程详情信息
     * @param course 课程
     * @return
     */
    int updateXiaoYiCourseDetail(LiveCourse course);

    /**
     * 新增|修改课程详情信息
     * @param detail 课节信息
     * @return
     */
    int updateXiaoYiCourseDetail(LiveCourseDetail detail,Integer courseId,String orgCode);

    /**
     * 删除课程详情信息
     * @param id 直播课节编号
     * @return
     */
    int deleteXiaoYiCourseDetail(Integer id);

}
