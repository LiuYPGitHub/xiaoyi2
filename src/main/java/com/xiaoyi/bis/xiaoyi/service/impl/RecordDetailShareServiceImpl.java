package com.xiaoyi.bis.xiaoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.xiaoyi.dao.RecordDetailShareMapper;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetail;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetailShare;
import com.xiaoyi.bis.xiaoyi.domain.ShareStudent;
import com.xiaoyi.bis.xiaoyi.service.RecordDetailShareService;
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
public class RecordDetailShareServiceImpl extends ServiceImpl<RecordDetailShareMapper, RecordDetailShare> implements RecordDetailShareService {

    @Autowired
    private RecordDetailShareMapper recordDetailShareMapper;

    @Override
    public int saveRecordDetailShare(RecordDetailShare detailShare) {
        return recordDetailShareMapper.insert(detailShare);
    }

    @Override
    public boolean checkShareStudent(Integer courseId, RecordDetail recordDetail, ShareStudent student) {
        QueryWrapper<RecordDetailShare> recordDetailShares=new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseId)){
            recordDetailShares.eq("course_id",courseId);
        }
        if(!StringUtils.isEmpty(recordDetail.getLessonId())){
            recordDetailShares.eq("lesson_id",recordDetail.getLessonId());
        }
        if(!StringUtils.isEmpty(recordDetail.getLessonName())){
            recordDetailShares.eq("lesson_name",recordDetail.getLessonName());
        }
        if(!StringUtils.isEmpty(recordDetail.getVid())){
            recordDetailShares.eq("vid",recordDetail.getVid());
        }
        if(!StringUtils.isEmpty(student.getName())){
            recordDetailShares.eq("student_name",student.getName());
        }
        if(!StringUtils.isEmpty(student.getMobile())){
            recordDetailShares.eq("mobile",student.getMobile());
        }
        List<RecordDetailShare> detailShares = recordDetailShareMapper.selectList(recordDetailShares);
        return detailShares.size()<=0?true:false;
    }

}
