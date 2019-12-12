package com.xiaoyi.bis.xiaoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoyi.bis.xiaoyi.dao.IntelligentRecordMapper;
import com.xiaoyi.bis.xiaoyi.dao.RecordDetailMapper;
import com.xiaoyi.bis.xiaoyi.domain.IntelligentRecord;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetail;
import com.xiaoyi.bis.xiaoyi.service.TimedTaskService;
import com.xiaoyi.bis.xiaoyi.util.TimesTaskUtil;
import com.xiaoyi.bis.xiaoyi.videoApi.GetVideoOneInfoAPI;
import com.xiaoyi.bis.xiaoyi.videoApi.GetVideoOneInfoRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.GetVideoOneInfoResopnse;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIVideoOneInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author CJ
 * @date 2019/12/5
 */
@Service
@Slf4j
public class TimedTaskServiceImpl implements TimedTaskService {

    @Autowired
    private IntelligentRecordMapper intelligentRecordMapper;
    @Autowired
    private RecordDetailMapper recordDetailMapper;
    @Autowired
    private GetVideoOneInfoAPI getVideoOneInfoAPI;
    /**
     * 更新智能录播-精编版课节视频解码状态
     * @return
     */
    @Override
    public String updateVideoStatus() throws NoSuchAlgorithmException {
        long startTime = System.currentTimeMillis();
        log.info("------------------------- 开始执行更新智能录播-精编版课节视频解码状态定时任务 -------------------------");
        LambdaQueryWrapper<RecordDetail> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(RecordDetail::getStatus,"1");
        queryWrapper.isNotNull(RecordDetail::getVid);
        log.info("正在获取精编版课节信息");
        List<RecordDetail> details = recordDetailMapper.selectList(queryWrapper);
        for (RecordDetail detail : details) {
            log.info("已获取["+detail.getLessonName()+"]精编版课节信息");
            if (!StringUtils.isEmpty(detail)&&!StringUtils.isEmpty(detail.getVid())) {
                GetVideoOneInfoResopnse resopnse = getVideoOneInfoAPI.process(new GetVideoOneInfoRequest(detail.getVid()));
                if(!StringUtils.isEmpty(resopnse)&&!StringUtils.isEmpty(resopnse.getVideoOneInfo())){
                    APIVideoOneInfo videoOneInfo = resopnse.getVideoOneInfo();
                    if(!StringUtils.isEmpty(videoOneInfo.getStatus())){
                        if(videoOneInfo.getStatus() == 60 || videoOneInfo.getStatus() == 61){
                            detail.setStatus(2);
                            int update = recordDetailMapper.updateById(detail);
                            if(update>0){
                                log.info("更新["+detail.getLessonName()+"]课节信息成功！");
                            }else{
                                log.error("更新["+detail.getLessonName()+"]课节信息失败！");
                            }
                        }else{
                            log.error("已跳过更新["+detail.getLessonName()+"]课节信息！");
                        }
                    }else{
                        log.error("更新vid为["+detail.getVid()+"]课节信息状态码为空！");
                    }
                }else{
                    log.error("更新vid为["+detail.getVid()+"]课节信息接口异常！");
                }
            }else{
                log.error("["+detail.getLessonName()+"]课节不存在");
            }
        }
        long endTime = System.currentTimeMillis();
        String result = TimesTaskUtil.process(startTime, endTime);
        log.info("------------------------- 更新智能录播-精编版课节视频解码状态执行完毕 -------------------------");
        log.info(result);
        return result;
    }
    /**
     * 更新智能录播-完整版课节视频解码状态
     * @return
     */
    @Override
    public String updateVideoAllStatus() throws NoSuchAlgorithmException {
        long startTime = System.currentTimeMillis();
        log.info("------------------------- 开始执行更新智能录播-完整版课节视频解码状态定时任务 -------------------------");
        LambdaQueryWrapper<IntelligentRecord> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.notIn(IntelligentRecord::getVideoStatus,"3");
        queryWrapper.isNotNull(IntelligentRecord::getVid);
        log.info("正在获取完整版课程信息");
        List<IntelligentRecord> intelligentRecords = intelligentRecordMapper.selectList(queryWrapper);
        for (IntelligentRecord record : intelligentRecords) {
            log.info("已获取["+record.getCourseName()+"]完整版课程信息");
            if (!StringUtils.isEmpty(record)&&!StringUtils.isEmpty(record.getVid())) {
                GetVideoOneInfoResopnse resopnse = getVideoOneInfoAPI.process(new GetVideoOneInfoRequest(record.getVid()));
                if(!StringUtils.isEmpty(resopnse)&&!StringUtils.isEmpty(resopnse.getVideoOneInfo())){
                    APIVideoOneInfo videoOneInfo = resopnse.getVideoOneInfo();
                    if(!StringUtils.isEmpty(videoOneInfo.getStatus())){
                        if(videoOneInfo.getStatus() == 60 || videoOneInfo.getStatus() == 61){
                            record.setVideoStatus("3");
                            int update = intelligentRecordMapper.updateById(record);
                            if(update>0){
                                log.info("更新["+record.getCourseName()+"]课程信息成功！");
                            }else{
                                log.error("更新["+record.getCourseName()+"]课程信息失败！");
                            }
                        }else{
                            log.error("已跳过更新["+record.getCourseName()+"]课程信息！");
                        }
                    }else{
                        log.error("更新vid为["+record.getVid()+"]课程信息状态码为空！");
                    }
                }else{
                    log.error("更新vid为["+record.getVid()+"]课程信息接口异常！");
                }
            }else{
                log.error("["+record.getCourseName()+"]课程信息不存在");
            }
        }
        long endTime = System.currentTimeMillis();
        String result = TimesTaskUtil.process(startTime, endTime);
        log.info("------------------------- 更新智能录播-完整版课节视频解码状态执行完毕 -------------------------");
        log.info(result);
        return result;
    }

}
