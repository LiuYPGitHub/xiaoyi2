package com.xiaoyi.bis.xiaoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.xiaoyi.dao.RecordCourseShareMapper;
import com.xiaoyi.bis.xiaoyi.domain.RecordCourseShare;
import com.xiaoyi.bis.xiaoyi.domain.ShareStudent;
import com.xiaoyi.bis.xiaoyi.service.RecordCourseShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author CJ
 * @date 2019/11/7
 */
@Transactional
@Service
public class RecordCourseShareServiceImpl extends ServiceImpl<RecordCourseShareMapper, RecordCourseShare> implements RecordCourseShareService {

    @Autowired
    private RecordCourseShareMapper recordCourseShareMapper;

    @Override
    public boolean checkShareStudent(String classId, ShareStudent student) {
        QueryWrapper<RecordCourseShare> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("class_id",classId);
        //queryWrapper.eq("student_name",student.getName());
        queryWrapper.eq("mobile",student.getMobile());
        List<RecordCourseShare> recordCourses = recordCourseShareMapper.selectList(queryWrapper);
        return recordCourses.size()<=0?true:false;
    }

    @Override
    public int saveShareStudent(RecordCourseShare student) {
        return recordCourseShareMapper.insert(student);
    }

}
