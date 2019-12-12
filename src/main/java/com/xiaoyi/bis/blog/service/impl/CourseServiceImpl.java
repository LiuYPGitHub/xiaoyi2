package com.xiaoyi.bis.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.blog.dao.CourseMapper;
import com.xiaoyi.bis.blog.domain.Course;
import com.xiaoyi.bis.blog.service.CourseService;
import com.xiaoyi.bis.common.utils.SnowflakeIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public PageInfo<Course> getById(String userId, String coTitle, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Course> list = baseMapper.getEventById(userId, coTitle);
        PageInfo<Course> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

    @Override
    public PageInfo<Course> finddraftById(String userId, String coTitle, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Course> list = baseMapper.selectdraftById(userId, coTitle);
        PageInfo<Course> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

    /**
     * 删除课程
     */
    @Override
    @Transactional
    public void updateEvent(String coId) {
        this.baseMapper.updateEvent(coId);
    }

    /**
     * 草稿箱活动发布
     *
     * @param course
     */
    @Override
    @Transactional
    public void releaseEvent(Course course) {
        course.setEvtRelTime(new Date());
        this.baseMapper.releaseEvent(course);
    }

    /**
     * 名师草稿箱-编辑-回显
     *
     * @param coId
     * @return
     */
    @Override
    @Transactional
    public Course finddEchoById(String coId) {
        return baseMapper.finddEchoById(coId);
    }

    /**
     * 名师课程-添加新课程
     *
     * @param course
     */
    @Override
    @Transactional
    public void addCourse(Course course) {
        course.setCoId(String.valueOf(snowflakeIdGenerator.nextId()));
        course.setIsDelete("1");
        course.setCreateTime(new Date());
        course.setEvtRelTime(new Date());
        if (course.getCoLinkUrl().substring(0, 4).equals("http")) {
        } else {
            course.setCoLinkUrl("http://" + course.getCoLinkUrl());
        }
        baseMapper.insertCourse(course);
    }

    /**
     * 修改
     *
     * @param course
     */
    @Override
    @Transactional
    public void updateCourse(Course course) {
        course.setUpdateTime(new Date());
        this.baseMapper.updateCourse(course);
    }

}
/* 添加字段格式
{
  "coLinkUrl": "课程链接",
  "coStatus": "0",
  "coTitle": "标题",
  "coType": "1",
  "createBy": "闯江湖",
  "imgUrl": "图片url",
  "userId": "11111111111"
}

修改
{
         "evtTitle": "活动标题11",
         "evtImgUrl": "qwertyusxcvbnm",
         "evtType": "活动类型11",
         "evtWay": 0,
         "evtStartTime": "2019-08-30 14:11:04",
         "evtEndTime": "2019-08-30 14:11:04",
         "evtContent": "活动111111111",
         "evtStatus": 0,
         "updateBy": "小绵羊1",
	     "evtId": "289276924860174336"
}
-----evtStatus 两个状态------         "isDelete": 1,	     "updateTime": "2019-08-30 14:11:04",
{
  "evtId": "289276924860174336"
  "evtAddress": "活动地址12313123",
  "evtCheckStatus": "0",
  "evtCoDdress": "活动链接地址231",
  "evtContent": "活动内容3232",
  "evtEndTime": "2019-09-05 01:56:41",
  "evtId": 0,
  "evtImgUrl": "string",
  "evtStartTime": "2019-09-05 01:56:47",
  "evtStatus": "1",
  "evtTitle": "活动标题",
  "evtType": "活动类型",
  "evtWay": "0",
  "isDelete": "1",
  "updateBy": "更新着",
  "updateTime": "2019-09-05 01:56:47"
}
*/
