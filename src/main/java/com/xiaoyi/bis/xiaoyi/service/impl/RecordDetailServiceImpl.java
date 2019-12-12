package com.xiaoyi.bis.xiaoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaoyi.bis.xiaoyi.dao.RecordDetailMapper;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourse;
import com.xiaoyi.bis.xiaoyi.domain.RecordCourse;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetail;
import com.xiaoyi.bis.xiaoyi.dto.PageRequest;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import com.xiaoyi.bis.xiaoyi.service.RecordDetailService;
import com.xiaoyi.bis.xiaoyi.util.DateUtil;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author CJ
 * @date 2019/11/6
 */
@Transactional
@Service
@Slf4j
public class RecordDetailServiceImpl extends ServiceImpl<RecordDetailMapper, RecordDetail> implements RecordDetailService{

    @Autowired
    private RecordDetailMapper recordDetailMapper;

    @Override
    public void saveRecordDetail(Integer courseId,List<APIRecordFile> recordFiles) {
        log.info("开始添加"+courseId+"课节信息");
        RecordDetail recordDetail = null;
        for (APIRecordFile record : recordFiles) {
            recordDetail = new RecordDetail();
            recordDetail.setCourseId(courseId);
            recordDetail.setLessonName(record.getFileName());
            recordDetail.setMp4(record.getMp4());
            recordDetail.setRecordHour(Integer.valueOf(record.getDuration()));
            log.info("正在添加"+courseId+"课节"+record.getFileName()+"信息");
            recordDetailMapper.insert(recordDetail);
        }
    }

    @Override
    public PageResponse queryRecordDetailByCourseId(Integer pageIndex, Integer pageSize, Integer courseId) {
        Page<RecordDetail> objects = PageHelper.startPage(pageIndex,pageSize);
        QueryWrapper<RecordDetail> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        recordDetailMapper.selectList(queryWrapper);
        PageResponse response=new PageResponse();
        response.setPageIndex(objects.getPageNum());
        response.setPageSize(objects.getPageSize());
        response.setPageCount(objects.getPages());
        response.setTotalCount((int) objects.getTotal());
        List<RecordDetail> recordDetails = objects.getResult();
        for (RecordDetail recordDetail : recordDetails) {
            if(StringUtils.isEmpty(recordDetail.getIsRevised())){
                if(recordDetails.size()>1){
                    recordDetail.setIsRevised(2);
                }else{
                    recordDetail.setIsRevised(1);
                }
            }
        }
        response.setList(recordDetails);
        return response;
    }

    @Override
    public List<RecordDetail> queryRecordDetailByCourseId(Integer courseId) {
        QueryWrapper<RecordDetail> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<RecordDetail> details = recordDetailMapper.selectList(queryWrapper);
        for (int i = 0,j=1; i < details.size(); i++,j++) {
            details.get(i).setLessonNum(String.valueOf(j));
        }
        return details;
    }

    @Override
    public List<RecordDetail> queryRecordDetailVidNotNull(Integer courseId) {
        QueryWrapper<RecordDetail> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.isNotNull("vid");
        List<RecordDetail> details = recordDetailMapper.selectList(queryWrapper);
        for (int i = 0,j=1; i < details.size(); i++,j++) {
            details.get(i).setLessonNum(String.valueOf(j));
        }
        return details;
    }

    @Override
    public PageResponse queryRecordDetail(PageRequest request, Integer courseId) {
        Page<RecordDetail> objects = PageHelper.startPage(request.getPageIndex(), request.getPageSize());
        QueryWrapper<RecordDetail> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<RecordDetail> details = recordDetailMapper.selectList(queryWrapper);
        for (int i = 0,j=1; i < details.size(); i++,j++) {
            details.get(i).setLessonNum(String.valueOf(j));
        }
        PageResponse response=new PageResponse();
        response.setPageIndex(objects.getPageNum());
        response.setPageSize(objects.getPageSize());
        response.setPageCount(objects.getPages());
        response.setTotalCount((int) objects.getTotal());
        response.setList(objects.getResult());
        return response;
    }

    @Override
    public RecordDetail queryRecordDetailById(Integer id) {
        return recordDetailMapper.selectById(id);
    }

}
