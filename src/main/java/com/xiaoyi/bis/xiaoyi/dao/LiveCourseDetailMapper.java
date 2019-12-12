package com.xiaoyi.bis.xiaoyi.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourseDetail;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LiveCourseDetailMapper {

    /**
     * 根据课程编号获取课节详情信息
     * @param courseId
     * @return
     */
    List<LiveCourseDetail> selectByCourseId(@Param(value = "courseId") Integer courseId);

    /**
     * 修改课节详情信息
     * @param detail
     * @return
     */
    int updateXiaoYiCourseDetail(LiveCourseDetail detail);

    /**
     * 添加课节详情信息
     * @param detail
     * @return
     */
    int insertXiaoYiCourseDetail(LiveCourseDetail detail);

    /**
     * 删除课程详情信息
     * @param id 直播课节编号
     * @return
     */
    int deleteXiaoYiCourseDetail(@Param(value = "id")Integer id);

}
