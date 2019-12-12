package com.xiaoyi.bis.xiaoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.common.utils.StringUtils;
import com.xiaoyi.bis.xiaoyi.bean.ShareStudentBean;
import com.xiaoyi.bis.xiaoyi.bean.StudentBean;
import com.xiaoyi.bis.xiaoyi.dao.ShareStudentMapper;
import com.xiaoyi.bis.xiaoyi.domain.ShareStudent;
import com.xiaoyi.bis.xiaoyi.dto.QueryShareStudentRequest;
import com.xiaoyi.bis.xiaoyi.service.ShareStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author CJ
 * @date 2019/11/6
 */
@Transactional
@Service
@Slf4j
public class ShareStudentServiceImpl extends ServiceImpl<ShareStudentMapper,ShareStudent> implements ShareStudentService {

    @Autowired
    private ShareStudentMapper shareStudentMapper;

    /**
     * 导入添加课程分享课节学生
     * @param students
     */
    @Override
    public void saveShareStudent(Integer recordId,List< StudentBean > students){
        for (StudentBean student : students) {
            log.info("开始添加[课程"+recordId+"]分享课节学生:"+student.getStudentName());
            ShareStudent shareStudent=new ShareStudent();
            shareStudent.setMobile(student.getStudentPhone());
            shareStudent.setName(student.getStudentName());
            shareStudent.setRecordId(recordId);
            shareStudentMapper.insert(shareStudent);
            log.info("添加[课程"+recordId+"]分享课节学生完成");
        }
    }

    /**
     * 正在添加课程分享课节学生
     * @param request 用户输入信息
     */
    @Override
    public int saveShareStudent(QueryShareStudentRequest request) {
        ShareStudent shareStudent=new ShareStudent();
        shareStudent.setMobile(request.getMobile());
        shareStudent.setName(request.getNameKey());
        shareStudent.setRecordId(request.getCourseId());
        return shareStudentMapper.insert(shareStudent);
    }

    @Override
    public int saveShareStudent(ShareStudent shareStudent) {
        return shareStudentMapper.insert(shareStudent);
    }

    @Override
    public int saveShareStudent(ShareStudentBean shareStudent) {
        ShareStudent student=new ShareStudent();
        student.setName(shareStudent.getName());
        student.setMobile(shareStudent.getMobile());
        student.setRecordId(shareStudent.getCourseId());
        return shareStudentMapper.insert(student);
    }

    @Override
    public List<ShareStudent> queryShareStudentByLike(QueryShareStudentRequest request) {
        QueryWrapper<ShareStudent> queryWrapper=new QueryWrapper<>();
        if(request.getCourseId()!=null && request.getCourseId()!=0){
            queryWrapper.eq("record_id",request.getCourseId());
        }
        if(!StringUtils.isEmpty(request.getNameKey())){
            queryWrapper.like("name",request.getNameKey());
        }
        if(!StringUtils.isEmpty(request.getMobile())){
            queryWrapper.eq("mobile",request.getMobile());
        }
        return shareStudentMapper.selectList(queryWrapper);
    }

    @Override
    public ShareStudent queryShareStudentById(Integer id) {
        return shareStudentMapper.selectById(id);
    }

}
