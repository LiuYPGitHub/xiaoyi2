package com.xiaoyi.bis.xiaoyi.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaoyi.bis.xiaoyi.dao.LiveCourseMapper;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourse;
import com.xiaoyi.bis.xiaoyi.domain.RecordCourse;
import com.xiaoyi.bis.xiaoyi.domain.XiaoYiTeacher;
import com.xiaoyi.bis.xiaoyi.dto.PageRequest;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import com.xiaoyi.bis.xiaoyi.dto.QueryLiveCourseRequest;
import com.xiaoyi.bis.xiaoyi.service.LiveCourseService;
import com.xiaoyi.bis.xiaoyi.service.RecordCourseService;
import com.xiaoyi.bis.xiaoyi.service.XiaoYiTeacherService;
import com.xiaoyi.bis.xiaoyi.util.FindUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Transactional
@Service
@Slf4j
public class LiveCourseServiceImpl implements LiveCourseService {

    @Autowired
    private LiveCourseMapper liveCourseMapper;
    @Autowired
    private RecordCourseService recordCourseService;
    @Autowired
    private XiaoYiTeacherService xiaoYiTeacherService;
    //直播课程添翼申学访问前缀
    @Value(value = "${tysxService.liveCourseAccessPrefix}")
    private String liveCourseAccessPrefix;

    @Override
    public PageResponse selectByLike(QueryLiveCourseRequest request, LiveCourse course) {
        Page<LiveCourse> objects = PageHelper.startPage(request.getPageIndex(), request.getPageSize());
        liveCourseMapper.selectCourse(course);
        PageResponse response = new PageResponse();
        response.setPageIndex(objects.getPageNum());
        response.setPageSize(objects.getPageSize());
        response.setPageCount(objects.getPages());
        response.setTotalCount((int) objects.getTotal());
        List<LiveCourse> liveCourses = objects.getResult();
        for (LiveCourse liveCour : liveCourses) {
            String classTag = liveCour.getClassTag();
            if (!StringUtils.isEmpty(classTag)) {
                liveCour.setClassTags(classTag.split("&"));
            }
            if (!StringUtils.isEmpty(liveCour.getClassDifficult())) {
                liveCour.setClassDifficultCount(FindUtil.findCount(liveCour.getClassDifficult(), "★"));
            }
            if (!StringUtils.isEmpty(liveCour)) {
                String classId = liveCour.getClassId();
                if (!StringUtils.isEmpty(classId)) {
                    liveCour.setCopyUrl(liveCourseAccessPrefix + classId);
                }
            }
            liveCour.setCourseType(1);
        }
        response.setList(liveCourses);
        return response;
    }

    @Override
    public LiveCourse queryCourseById(Integer id) {
        return liveCourseMapper.selectById(id);
    }

    @Override
    public int updateCourse(LiveCourse course) {
        return liveCourseMapper.updateCourse(course);
    }

    @Override
    public int saveCourse(LiveCourse course) {
        //根据手机号码查询教师是否存在
        XiaoYiTeacher xiaoYiTeacher = xiaoYiTeacherService.queryTeacherByPhone(course.getAccount());
        if (StringUtils.isEmpty(xiaoYiTeacher)) {
            //添加教师
            xiaoYiTeacher = new XiaoYiTeacher();
            xiaoYiTeacher.setOrgCode(course.getOrgCode());
            xiaoYiTeacher.setMobile(course.getAccount());
            xiaoYiTeacher.setTeacherId("init");
            xiaoYiTeacherService.saveTeacher(xiaoYiTeacher);
            log.info("已新增" + course.getCourseName() + "用户信息");
        }
        xiaoYiTeacher = xiaoYiTeacherService.queryTeacherByPhone(course.getAccount());
        course.setTeacherId(xiaoYiTeacher.getTid());
        log.info("已新增" + course.getCourseName() + "课程信息");
        return liveCourseMapper.insertCourse(course);
    }

    @Override
    public int saveCourseInfo(LiveCourse course) {
        return liveCourseMapper.insertCourse(course);
    }

    @Override
    public String getOrgIdByUserId(String userId) {
        return liveCourseMapper.getOrgIdByUserId(userId);
    }

    @Override
    public String getSiteNameByUserId(String userId) {
        /*String name = liveCourseMapper.getSiteNameByUserId(userId);
        if(StringUtils.isEmpty(name)){
            name = liveCourseMapper.getNickNameByUserId(userId);
        }*/
        return liveCourseMapper.getSiteNameByUserId(userId);
    }

    @Override
    public String isAdmin(String userId) {
        return liveCourseMapper.isAdmin(userId);
    }

    @Override
    public int sumbitCourse(Integer id) {
        LiveCourse course = new LiveCourse();
        course.setId(id);
        course.setStatus(new Integer(1));
        return liveCourseMapper.updateCourse(course);
    }

    @Override
    public int dropCourse(Integer id) {
        return liveCourseMapper.deleteCourse(id);
    }

}
