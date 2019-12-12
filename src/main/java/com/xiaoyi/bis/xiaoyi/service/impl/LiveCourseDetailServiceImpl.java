package com.xiaoyi.bis.xiaoyi.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaoyi.bis.xiaoyi.dao.LiveCourseDetailMapper;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourse;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourseDetail;
import com.xiaoyi.bis.xiaoyi.domain.RecordCourse;
import com.xiaoyi.bis.xiaoyi.dto.PageRequest;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import com.xiaoyi.bis.xiaoyi.service.LiveCourseDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author CJ
 * @date 2019/10/14
 */
@Transactional
@Service
@Slf4j
public class LiveCourseDetailServiceImpl implements LiveCourseDetailService {

    @Autowired
    private LiveCourseDetailMapper liveCourseDetailMapper;

    @Override
    public PageResponse queryCourseDetailList(Integer courseId,Integer pageIndex,Integer pageSize) {
        Page<LiveCourse> objects = PageHelper.startPage(pageIndex,pageSize);
        liveCourseDetailMapper.selectByCourseId(courseId);
        PageResponse response=new PageResponse();
        response.setPageIndex(objects.getPageNum());
        response.setPageSize(objects.getPageSize());
        response.setPageCount(objects.getPages());
        response.setTotalCount((int) objects.getTotal());
        response.setList(objects.getResult());
        return response;
    }

    @Override
    public List<LiveCourseDetail> queryCourseDetailById(Integer courseId) {
        return liveCourseDetailMapper.selectByCourseId(courseId);
    }

    @Override
    public PageResponse queryCourseDetailById(PageRequest request, Integer courseId) {
        Page<LiveCourseDetail> objects = PageHelper.startPage(request.getPageIndex(), request.getPageSize());
        liveCourseDetailMapper.selectByCourseId(courseId);
        PageResponse response=new PageResponse();
        response.setPageIndex(objects.getPageNum());
        response.setPageSize(objects.getPageSize());
        response.setPageCount(objects.getPages());
        response.setTotalCount((int) objects.getTotal());
        response.setList(objects.getResult());
        return response;
    }

    @Override
    public int saveXiaoYiCourseDetail(LiveCourse course) {
        for (LiveCourseDetail detail : course.getLiveCourseDetails()) {
            detail.setCourseId(course.getId());
            detail.setCode(UUID.randomUUID().toString());
            detail.setOrgCode(course.getOrgCode());
            liveCourseDetailMapper.insertXiaoYiCourseDetail(detail);
            log.info("已新增"+detail.getLessonName()+"课节信息");
            detail.setId(detail.getId());
        }
        return 0;
    }

    @Override
    public int updateXiaoYiCourseDetail(LiveCourse course) {
        for (LiveCourseDetail detail : course.getLiveCourseDetails()) {
            detail.setCourseId(course.getId());
            detail.setCode(UUID.randomUUID().toString());
            detail.setOrgCode(course.getOrgCode());
            saveOrUpdateLiveCourseDetail(detail);
            detail.setId(detail.getId());
        }
        return 0;
    }

    @Override
    public int updateXiaoYiCourseDetail(LiveCourseDetail detail,Integer courseId,String orgCode) {
        detail.setCourseId(courseId);
        detail.setCode(UUID.randomUUID().toString());
        detail.setOrgCode(orgCode);
        saveOrUpdateLiveCourseDetail(detail);
        detail.setId(detail.getId());
        return 0;
    }

    private void saveOrUpdateLiveCourseDetail(LiveCourseDetail detail){
        if(!StringUtils.isEmpty(detail.getId())){
            liveCourseDetailMapper.updateXiaoYiCourseDetail(detail);
            log.info("已修改"+detail.getLessonName()+"课节信息");
        }else{
            liveCourseDetailMapper.insertXiaoYiCourseDetail(detail);
            log.info("已新增"+detail.getLessonName()+"课节信息");
            detail.setId(detail.getId());
        }
    }

    @Override
    public int deleteXiaoYiCourseDetail(Integer id) {
        return liveCourseDetailMapper.deleteXiaoYiCourseDetail(id);
    }

}
