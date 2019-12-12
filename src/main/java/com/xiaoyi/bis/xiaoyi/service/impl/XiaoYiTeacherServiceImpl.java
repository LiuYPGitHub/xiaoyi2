package com.xiaoyi.bis.xiaoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.xiaoyi.dao.LiveTeacherMapper;
import com.xiaoyi.bis.xiaoyi.domain.XiaoYiTeacher;
import com.xiaoyi.bis.xiaoyi.service.XiaoYiTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author CJ
 * @date 2019/10/23
 */
@Transactional
@Service
public class XiaoYiTeacherServiceImpl extends ServiceImpl<LiveTeacherMapper, XiaoYiTeacher> implements XiaoYiTeacherService {

    @Autowired
    private LiveTeacherMapper liveTeacherMapper;

    @Override
    public XiaoYiTeacher queryTeacherByPhone(String phone) {
        QueryWrapper<XiaoYiTeacher> xiaoYiTeacherWrapper=new QueryWrapper<>();
        xiaoYiTeacherWrapper.eq("mobile",phone);
        return liveTeacherMapper.selectOne(xiaoYiTeacherWrapper);
    }

    @Override
    public int saveTeacher(XiaoYiTeacher teacher) {
        return liveTeacherMapper.insert(teacher);
    }

}
