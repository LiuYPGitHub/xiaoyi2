package com.xiaoyi.bis.xiaoyi.videoApi.impl;

import com.xiaoyi.bis.xiaoyi.domain.IntelligentRecord;
import com.xiaoyi.bis.xiaoyi.domain.RecordCourse;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetail;
import com.xiaoyi.bis.xiaoyi.service.IntelligentRecordService;
import com.xiaoyi.bis.xiaoyi.service.RecordCourseService;
import com.xiaoyi.bis.xiaoyi.service.RecordDetailService;
import com.xiaoyi.bis.xiaoyi.videoApi.*;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIVideo;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddRecordCourseAPI;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddRecordLessonAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

/**
 * 智能录播同步点播之后根据保利威回调
 * 添加添翼申学线下课、修改内部数据
 *
 * @author CJ
 * @date 2019/11/08
 */
@Component
@Slf4j
public class FileForwardSaveCallBackAPIImpl implements FileForwardSaveCallBackAPI {

    @Autowired
    private IntelligentRecordService intelligentRecordService;
    @Autowired
    private RecordDetailService recordDetailService;
    @Autowired
    private RecordCourseService recordCourseService;
    @Autowired
    private QueryVideoOneAPI queryVideoOneAPI;

    @Value(value = "${tysxService.recordCourseImage}")
    private String recordCourseImage;
    //设置课节是否可以试看 1:可以 2:不可以
    @Value(value = "${xiaoYiService.allowTaste}")
    private Integer allowTaste;

    /**
     * 智能录播同步点播回调(完整、精编版)处理:(多个fileId回调多次,视频默认分类目录为1)
     * 完整版:更新智能录播数据、添加添翼申学线下课程
     * @param request
     * @throws NoSuchAlgorithmException
     */
    @Override
    public void process(FileForwardSaveCallBackRequest request) throws NoSuchAlgorithmException {
        //获取智能录播数据
        IntelligentRecord record = intelligentRecordService.queryIntelligentRecordById(request.getId());
        record.setId(request.getId());
        record.setChannelId(request.getChannelId());
        record.setFileId(request.getFileId());
        record.setVid(request.getVid());
        record.setPolyTitle(request.getTitle());
        record.setDuration(request.getDuration());
        record.setFileSize(request.getFileSize());
        record.setSessionIds(request.getSessionIds());
        //录制中
        record.setVideoStatus("2");
        QueryVideoOneRequest oneRequest = new QueryVideoOneRequest();
        oneRequest.setChannelId(request.getChannelId());
        oneRequest.setFileId(request.getFileId());
        log.info("查询保利威单个视频文件入参:" + oneRequest);
        QueryVideoOneResponse oneResponse = queryVideoOneAPI.process(oneRequest);
        APIRecordFile data = oneResponse.getData();
        if (!StringUtils.isEmpty(data.getM3u8())) {
            record.setM3u8(data.getM3u8());
        }
        if (!StringUtils.isEmpty(data.getMp4())) {
            record.setMp4(data.getMp4());
        }else{
            APIVideo apiVideo = intelligentRecordService.getVideoByVid(request.getVid());
            String fileUrl = apiVideo.getFileUrl();
            record.setMp4(fileUrl);
            data.setMp4(fileUrl);
        }

        record.setPolyTitle(data.getFileName());

        //判断此录播课程是否存在
        RecordCourse recordCourse = recordCourseService.selectByRecordId(request.getId());
        if (StringUtils.isEmpty(recordCourse)) {
            //完整版
            if (record.getIsRevised() == 1) {
                //添加添翼申学录播完整版线下课
                String expirationDuration = "7";
                String classId =  intelligentRecordService.addIntelligentRecord(expirationDuration,request.getTitle(),request.getVid(),record.getCourseNum(),record.getStudentNum());
                //添加至内部数据库ty_record_course
                if (!StringUtils.isEmpty(classId)) {
                    RecordCourse course = new RecordCourse();
                    boolean courseStatus = intelligentRecordService.getCourseStatus(classId);
                    if (courseStatus) {
                        record.setStatus(new Integer(4));
                    } else {
                        record.setStatus(new Integer(3));
                    }
                    course.setStatus(record.getStatus());

                    /*AddRecordLessonRequest recordLessonRequest=new AddRecordLessonRequest();
                    recordLessonRequest.setClassId(classId);
                    recordLessonRequest.setTitle(request.getTitle());
                    recordLessonRequest.setCoverImgLink(recordCourseImage);
                    recordLessonRequest.setRecordHour(getRecordHour(data.getStartTime(),data.getEndTime()).toString());
                    recordLessonRequest.setAllowTaste(String.valueOf(allowTaste));
                    //recordLessonRequest.setTeacherId("8a8880866e1a5c23016e1b592a790126");
                    recordLessonRequest.setVideoDuration("222");
                    recordLessonRequest.setVid(request.getVid());

                    log.info("添加课节入参:"+recordLessonRequest);
                    AddRecordLessonResponse recordLessonResponse = addRecordLessonAPI.process(recordLessonRequest);
                    String lessonId = recordLessonResponse.getLessonId();*/
                    //保存完整版单个课节
                    /*RecordDetail detail = new RecordDetail();
                    detail.setRecordHour(getRecordHour(data.getStartTime(), data.getEndTime()).intValue());
                    detail.setLessonId(lessonId);
                    detail.setCourseId(request.getId());
                    detail.setLessonName(request.getTitle());
                    detail.setVid(request.getVid());
                    detail.setMp4(data.getMp4());
                    detail.setCreatedAt(new Date());
                    detail.setUpdatedAt(new Date());
                    detail.setAllowTaste(allowTaste);
                    recordDetailService.save(detail);*/
                    course.setOrgCode(record.getOrgCode());
                    //course.setLessonIds(String.valueOf(detail.getId()));
                    course.setCreateDate(new Date());
                    course.setCreatedAt(new Date());
                    course.setUpdatedAt(new Date());
                    //待审核状态
                    course.setStatus(new Integer(1));
                    course.setClassId(classId);
                    course.setCourseName(record.getCourseName());
                    course.setClassType("学科教育");
                    course.setTeacheName(record.getTeacherName());
                    course.setClassInfo("暂无");
                    course.setShowEvaluation("0");
                    String mobile = recordCourseService.getMobileByUid(request.getUserId());
                    if (StringUtils.isEmpty(mobile)) {
                        mobile = "16621242385";
                    }
                    course.setCreateMobile(mobile);
                    Calendar instance = Calendar.getInstance();
                    course.setEnrollStartDate(instance.getTime());
                    instance.add(Calendar.YEAR, 2);
                    course.setEnrollEndDate(instance.getTime());
                    course.setPolyTitle(request.getTitle());
                    course.setVid(request.getVid());
                    course.setFileSize(request.getFileSize());
                    //course.setExpirationDuration(expirationDuration);
                    course.setRecordId(request.getId());
                    course.setPrice(new BigDecimal(0));
                    course.setCost(new BigDecimal(0));
                    //默认设置为线下录播课
                    course.setIsOnline(new Integer(2));
                    recordCourseService.saveRecordCourse(course);
                }
            }
        } else {
            if (record.getIsRevised() == 2) {
                //添加精编版课节信息至添翼申学
                /*AddRecordLessonRequest recordLessonRequest=new AddRecordLessonRequest();
                recordLessonRequest.setClassId(recordCourse.getClassId());
                //recordLessonRequest.setTeacherId("8a8880866e1a5c23016e1b592a790126");
                recordLessonRequest.setAllowTaste(String.valueOf(allowTaste));
                recordLessonRequest.setCoverImgLink(recordCourseImage);
                recordLessonRequest.setRecordHour(getRecordHour(data.getStartTime(),data.getEndTime()).toString()));
                recordLessonRequest.setTitle(request.getTitle());
                recordLessonRequest.setVid(request.getVid());
                recordLessonRequest.setVideoDuration("222");
                AddRecordLessonResponse recordLessonResponse = addRecordLessonAPI.process(recordLessonRequest);
                String lessonId = recordLessonResponse.getLessonId();*/
                //保存课程课节信息
                RecordDetail detail = new RecordDetail();
                detail.setDuration(request.getDuration());
                //detail.setLessonId(lessonId);
                detail.setCourseId(request.getId());
                detail.setLessonName(request.getTitle());
                detail.setVid(request.getVid());
                detail.setMp4(data.getMp4());
                detail.setCreatedAt(new Date());
                detail.setUpdatedAt(new Date());
                detail.setRecordHour(getRecordHour(data.getStartTime(), data.getEndTime()).intValue());
                detail.setAllowTaste(allowTaste);
                recordDetailService.save(detail);

                boolean courseStatus = intelligentRecordService.getCourseStatus(recordCourse.getClassId());
                if (courseStatus) {
                    record.setStatus(new Integer(4));
                } else {
                    record.setStatus(new Integer(3));
                }
                recordCourse.setStatus(record.getStatus());

                recordCourse.setLessonIds(recordCourse.getLessonIds() + "," + String.valueOf(detail.getId()));
            }
            recordCourseService.updateRecordCourse(recordCourse);
        }
        //更新智能录播完整版数据
        intelligentRecordService.updateIntelligentRecord(record);
    }

    public Long getRecordHour(long startTime, long endTime) {
        if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime) && startTime != 0 && endTime != 0) {
            return startTime - endTime;
        } else {
            return Long.valueOf(1);
        }
    }

}
