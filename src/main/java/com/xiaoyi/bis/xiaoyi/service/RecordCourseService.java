package com.xiaoyi.bis.xiaoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.xiaoyi.domain.RecordCourse;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import com.xiaoyi.bis.xiaoyi.dto.QueryRecordCourseRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordCourseService extends IService<RecordCourse> {

    /**
     * 课程标签处理
     * @param classTag
     * @return
     */
    List<String> classTagsProcess(String classTag);

    /**
     * 获取视频播放token
     * @param vid 保利威视频编号
     * @return
     */
    String getToken(String vid);

    /**
     * 添加录播课程
     * @param recordCourse
     * @return
     */
    int saveRecordCourse(RecordCourse recordCourse);

    /**
     * 修改录播课程
     * @param recordCourse
     * @return
     */
    int updateRecordCourse(RecordCourse recordCourse);

    /**
     * 根据智能录播编号获取数据
     * @param recordId
     * @return
     */
    RecordCourse selectByRecordId(@Param(value = "recordId")Integer recordId);

    /**
     * 根据编号获取数据
     * @param id
     * @return
     */
    RecordCourse selectById(@Param(value = "id")Integer id);

    /**
     * 根据用户编号获取手机号码
     * @param uId
     * @return
     */
    String getMobileByUid(@Param(value = "uId")String uId);

    /**
     * 获取录播课程列表
     * @param request
     * @return
     */
    PageResponse queryRecordCourseByLike(QueryRecordCourseRequest request);

}
