package com.xiaoyi.bis.xiaoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetail;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetailShare;
import com.xiaoyi.bis.xiaoyi.domain.ShareStudent;

public interface RecordDetailShareService extends IService<RecordDetailShare> {

    /**
     * 添加智能录播课程分享信息
     * @param detailShare
     * @return
     */
    int saveRecordDetailShare(RecordDetailShare detailShare);

    /**
     * 智能录播课程分享信息的学生信息是否存在
     * @param courseId 课程编号
     * @param recordDetail 课节信息
     * @param student 分享的学生信息
     * @return true:不存在 false:存在
     */
    boolean checkShareStudent(Integer courseId, RecordDetail recordDetail, ShareStudent student);

}
