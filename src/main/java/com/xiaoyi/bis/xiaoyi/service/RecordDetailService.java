package com.xiaoyi.bis.xiaoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.xiaoyi.dao.RecordDetailMapper;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetail;
import com.xiaoyi.bis.xiaoyi.dto.PageRequest;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordDetailService extends IService<RecordDetail> {

    /**
     * 添加精编版录播关联的课节信息
     * @param courseId
     * @param recordFiles
     */
    void saveRecordDetail(Integer courseId,List<APIRecordFile> recordFiles);

    /**
     * 根据录播课程编号获取智能录播课节详情信息(分页)
     * @param courseId
     * @return
     */
    PageResponse queryRecordDetailByCourseId(Integer pageIndex, Integer pageSize, Integer courseId);

    /**
     * 根据录播课程编号获取智能录播课节详情信息(普通)
     * @param courseId
     * @return
     */
    List<RecordDetail> queryRecordDetailByCourseId(Integer courseId);

    /**
     * 根据录播课程编号获取智能录播课节详情信息(普通)
     * @param courseId
     * @return
     */
    List<RecordDetail> queryRecordDetailVidNotNull(Integer courseId);

    PageResponse queryRecordDetail(PageRequest request, Integer courseId);

    /**
     * 根据主键获取智能录播课节详情信息
     * @param id
     * @return
     */
    RecordDetail queryRecordDetailById(@Param(value = "id")Integer id);

}
