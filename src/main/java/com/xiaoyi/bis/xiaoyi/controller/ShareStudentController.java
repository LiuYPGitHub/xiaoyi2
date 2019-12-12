package com.xiaoyi.bis.xiaoyi.controller;

import com.xiaoyi.bis.common.annotation.ShowLogger;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.xiaoyi.bean.ShareStudentBean;
import com.xiaoyi.bis.xiaoyi.domain.*;
import com.xiaoyi.bis.xiaoyi.dto.*;
import com.xiaoyi.bis.xiaoyi.service.*;
import com.xiaoyi.bis.xiaoyi.util.DateUtil;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoOneAPI;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoOneRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoOneResponse;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddRecordCourseRequest;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 分享课程至学生个人账号
 *
 * @author CJ
 * @date 2019/11/6
 */
@Api(tags = {"shareStudent"}, value = "校翼")
@Slf4j
@RestController
public class ShareStudentController {

    @Autowired
    private IntelligentRecordService intelligentRecordService;
    @Autowired
    private RecordCourseShareService recordCourseShareService;
    @Autowired
    private RecordDetailShareService recordDetailShareService;
    @Autowired
    private RecordDetailService recordDetailService;
    @Autowired
    private ShareStudentService shareStudentService;
    @Autowired
    private RecordCourseService recordCourseService;
    @Autowired
    private AddUserCourseAPI addUserCourseAPI;
    @Autowired
    private AddRecordCourseAPI addRecordCourseAPI;
    @Autowired
    private AddRecordLessonAPI addRecordLessonAPI;
    @Autowired
    private QueryVideoOneAPI queryVideoOneAPI;
    //设置课节是否可以试看 1:可以 2:不可以
    @Value(value = "${xiaoYiService.allowTaste}")
    private Integer allowTaste;
    @Value(value = "${tysxService.siteName}")
    private String siteName;
    @Value(value = "${tysxService.recordCourseImage}")
    private String recordCourseImage;

    @ApiOperation(value = "查询指定课程分享学生", notes = "查询指定课程分享学生")
    @ShowLogger(info = "查询指定课程分享学生")
    @RequestMapping(path = "/xiaoyi/shareStudent", method = RequestMethod.GET)
    public AjaxResult queryShareStudentList(QueryShareStudentRequest request) {
        List<ShareStudent> shareStudents = shareStudentService.queryShareStudentByLike(request);
        return AjaxResult.success(shareStudents);
    }

    @ApiOperation(value = "新增指定课程分享学生", notes = "新增指定课程分享学生")
    @ShowLogger(info = "新增指定课程分享学生")
    @RequestMapping(path = "/xiaoyi/shareStudent", method = RequestMethod.POST)
    public AjaxResult addShareStudent(QueryShareStudentRequest request) {
        int saveResult = shareStudentService.saveShareStudent(request);
        return AjaxResult.success(saveResult > 0 ? true : false);
    }

    @ApiOperation(value = "保存课程分享学生-完整版", notes = "指定课程分享学生-完整版")
    @ShowLogger(info = "保存课程分享学生-完整版")
    @RequestMapping(path = "/xiaoyi/shareStudentAll/save", method = RequestMethod.POST)
    public AjaxResult addShareStudentAll(@RequestBody ReqShareStudent shareStudent) {

        RecordCourse recordCourse = checkClassIdExists(shareStudent.getCourseId());
        if (StringUtils.isEmpty(recordCourse)) {
            return AjaxResult.error("该课程暂不可分享，请稍后再试！");
        }

        IntelligentRecord record = intelligentRecordService.queryIntelligentRecordById(shareStudent.getCourseId());
        final List<ReqShareStudent.ReqStudent> students = shareStudent.getStudents();
        students.stream().forEach(e->{
            AddUserCourseRequest addRequest = new AddUserCourseRequest();
            addRequest.setClassId(recordCourse.getClassId());
            addRequest.setChannel(record.getChannelId());
            addRequest.setMobilephone(e.getStuMobile());
            AddUserCourseResponse addResponse = addUserCourseAPI.process(addRequest);
            log.info("添加分享学生[" + e.getStudName() + "]结果:" + addResponse);

            RecordCourseShare courseShare = new RecordCourseShare();
            courseShare.setStudentName(e.getStudName());
            courseShare.setMobile(e.getStuMobile());
            courseShare.setCourseName(record.getCourseName());
            courseShare.setClassId(recordCourse.getClassId());
            courseShare.setCreatedAt(new Date());
            courseShare.setUpdatedAt(new Date());
            recordCourseShareService.saveShareStudent(courseShare);

            if (org.apache.commons.lang3.StringUtils.isBlank(e.getStuId())) {
                ShareStudent ss=new ShareStudent();
                ss.setName(e.getStudName());
                ss.setMobile(e.getStuMobile());
                ss.setRecordId(record.getId());
                ss.setOrgCode(record.getOrgCode());
                shareStudentService.save(ss);
            }
        });

       /* for (Integer studentId : studentIds) {
            ShareStudent shareStudent = shareStudentService.queryShareStudentById(studentId);
            if(StringUtils.isEmpty(shareStudent)){
                log.error("该学生不存在！");
                return AjaxResult.error("该学生不存在！");
            }
            AddUserCourseRequest addRequest=new AddUserCourseRequest();
            addRequest.setClassId(recordCourse.getClassId());
            addRequest.setChannel(record.getChannelId());
            addRequest.setMobilephone(shareStudent.getMobile());
            AddUserCourseResponse addResponse = addUserCourseAPI.process(addRequest);
            log.info("添加分享学生["+shareStudent.getName()+"]结果:"+addResponse);
            RecordCourseShare courseShare=new RecordCourseShare();
            courseShare.setStudentName(shareStudent.getName());
            courseShare.setMobile(shareStudent.getMobile());
            courseShare.setCourseName(record.getCourseName());
            courseShare.setClassId(recordCourse.getClassId());
            courseShare.setCreatedAt(new Date());
            courseShare.setUpdatedAt(new Date());
            recordCourseShareService.saveShareStudent(courseShare);
        }*/
        return AjaxResult.success(true);
    }

    @ApiOperation(value = "检查课程学生是否已经分享-完整版", notes = "检查课程学生是否已经分享-完整版")
    @ShowLogger(info = "检查课程学生是否已经分享-完整版")
    @RequestMapping(path = "/xiaoyi/shareStudentAll/check", method = RequestMethod.POST)
    public AjaxResult checkShareStudentAll(@RequestBody ReqShareStudent shareStudent) {
        RecordCourse recordCourse = checkClassIdExists(shareStudent.getCourseId());
        if (StringUtils.isEmpty(recordCourse)) {
            return AjaxResult.error("该课程暂不可分享，请稍后再试！");
        }
        String classId = recordCourse.getClassId();
        final List<ReqShareStudent.ReqStudent> students = shareStudent.getStudents();
        List<CheckShareStudentAll> CheckResults = new ArrayList<>();
        CheckShareStudentAll checkShareStudentAll = null;
        for (ReqShareStudent.ReqStudent student : students) {
            checkShareStudentAll = new CheckShareStudentAll();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(student.getStuId())) {
                ShareStudent shareStu = shareStudentService.queryShareStudentById(Integer.valueOf(student.getStuId()));
                boolean checkResult = recordCourseShareService.checkShareStudent(classId, shareStu);
                if (!checkResult) {
                    checkShareStudentAll.setCheckFlag(checkResult);
                    checkShareStudentAll.setStudName(student.getStudName());
                    String message = checkResult == false ? "该学生已获得本视频，不可重复分享" : "该学生可以分享此课程";
                    checkShareStudentAll.setCheckMess(message);
                    checkShareStudentAll.setStuMobile(student.getStuMobile());
                    CheckResults.add(checkShareStudentAll);
                }
            }
        }
        return AjaxResult.success(CheckResults);
    }

    @ApiOperation(value = "保存课程分享学生-精编版",notes = "指定课程分享学生-精编版")
    @ShowLogger(info = "保存课程分享学生-精编版")
    @RequestMapping(path = "/xiaoyi/shareStudent/save",method = RequestMethod.POST)
    public AjaxResult addShareStudent(@RequestBody SaveShareStudentRequest request) throws NoSuchAlgorithmException {
        IntelligentRecord record = intelligentRecordService.queryIntelligentRecordById(request.getCourseId());
        List<ShareStudentBean> students = request.getStudents();
        List<RecordDetail> recordDetails = recordDetailService.queryRecordDetailByCourseId(record.getId());

        //1.添加添翼申学线下课程(classId) AddRecordCourseAPI
        AddRecordCourseRequest recordCourseRequest = new AddRecordCourseRequest();
        recordCourseRequest.setName(record.getCourseName());
        //暂时用默认的
        recordCourseRequest.setCoverImgLink(recordCourseImage);
        if(!StringUtils.isEmpty(record.getVid())){
            recordCourseRequest.setCoverVid(record.getVid());
        }
        Calendar instance = Calendar.getInstance();
        String enrollStartDate = DateFormatUtils.format(instance.getTime(), "yyyyMMddHHmmss");
        instance.add(Calendar.YEAR, 2);
        String enrollEndDate = DateFormatUtils.format(instance.getTime(), "yyyyMMddHHmmss");
        recordCourseRequest.setEnrollStartDate(enrollStartDate);
        recordCourseRequest.setEnrollEndDate(enrollEndDate);
        //recordCourseRequest.setExpirationDuration("7");
        if(!StringUtils.isEmpty(record.getClassHour())){
            recordCourseRequest.setClassHour(String.valueOf(record.getClassHour()));
            recordCourseRequest.setTotalTime(String.valueOf(record.getClassHour()));
        }else{
            recordCourseRequest.setClassHour("10");
            recordCourseRequest.setTotalTime("10");
        }
        recordCourseRequest.setRecordType("2");
        recordCourseRequest.setClassType("学科教育");
        recordCourseRequest.setPrimeCost("0");
        recordCourseRequest.setCost("0");
        recordCourseRequest.setSiteName(siteName);
        recordCourseRequest.setMaxNum("99999");
        AddRecordCourseResponse response = addRecordCourseAPI.process(recordCourseRequest);
        String classId = response.getClassId();

        APIRecordFile data =null;

        if(!StringUtils.isEmpty(record)&&!StringUtils.isEmpty(record.getChannelId())&&!StringUtils.isEmpty(record.getFileId())){
            //根据fileId获取保利威视频数据
            QueryVideoOneRequest videoRequest = new QueryVideoOneRequest();
            videoRequest.setChannelId(record.getChannelId());
            videoRequest.setFileId(record.getFileId());
            QueryVideoOneResponse videoOneResponse = queryVideoOneAPI.process(videoRequest);

            data = videoOneResponse.getData();
        }

        for (RecordDetail recordDetail : recordDetails) {
            //RecordDetail recordDetail = recordDetailService.queryRecordDetailById(lessonId);
            if(StringUtils.isEmpty(recordDetail)){
                log.error("该课节不存在！");
                return AjaxResult.error("该课节不存在！");
            }
            for (ShareStudentBean shareStudent : students) {

                if(!StringUtils.isEmpty(shareStudent)&&!StringUtils.isEmpty(shareStudent.getAddid())&&StringUtils.isEmpty(shareStudent.getId())){
                    ShareStudent shareStudent1=new ShareStudent();
                    shareStudent1.setName(shareStudent.getName());
                    shareStudent1.setMobile(shareStudent.getMobile());
                    shareStudent1.setRecordId(record.getId());
                    shareStudent1.setOrgCode(recordDetail.getOrgCode());
                    shareStudentService.save(shareStudent1);
                }

                //ShareStudent shareStudent = shareStudentService.queryShareStudentById(studentId);
                if(StringUtils.isEmpty(shareStudent)){
                    log.error("该学生不存在！");
                    return AjaxResult.error("该学生不存在！");
                }
                //2.添加添翼申学线下课程(LessonId)所属课节 AddRecordLessonAPI
                AddRecordLessonRequest recordLessonRequest=new AddRecordLessonRequest();
                recordLessonRequest.setClassId(classId);
                //recordLessonRequeust.setTeacherId("8a8880866e1a5c23016e1b592a790126");
                recordLessonRequest.setAllowTaste(String.valueOf(allowTaste));
                recordLessonRequest.setCoverImgLink(recordCourseImage);
                if(!StringUtils.isEmpty(data)){
                    recordLessonRequest.setRecordHour(DateUtil.getRecordHour(data.getStartTime(),data.getEndTime()).toString());
                }else{
                    recordLessonRequest.setRecordHour("1");
                }
                recordLessonRequest.setTitle(recordDetail.getLessonName());
                recordLessonRequest.setVid(recordDetail.getVid());
                recordLessonRequest.setVideoDuration(StringUtils.isEmpty(recordDetail.getDuration())?"00:00:17":recordDetail.getDuration());
                AddRecordLessonResponse recordLessonResponse = addRecordLessonAPI.process(recordLessonRequest);
                String tylessonId = recordLessonResponse.getLessonId();

                //3.添加课程分享用户
                AddUserCourseRequest addRequest=new AddUserCourseRequest();
                addRequest.setClassId(classId);
                addRequest.setChannel(record.getChannelId());
                addRequest.setMobilephone(shareStudent.getMobile());
                AddUserCourseResponse addResponse = addUserCourseAPI.process(addRequest);
                log.info("添加分享学生["+shareStudent.getName()+"]结果:"+addResponse);
                //保存分享学生数据至内部数据库
                RecordDetailShare detailShare=new RecordDetailShare();
                detailShare.setCourseId(record.getId());
                detailShare.setLessonId(tylessonId);
                detailShare.setLessonName(recordDetail.getLessonName());
                detailShare.setStudentName(shareStudent.getName());
                detailShare.setMobile(shareStudent.getMobile());
                detailShare.setCode(recordDetail.getCode());
                detailShare.setOrgCode(recordDetail.getOrgCode());
                detailShare.setVid(recordDetail.getVid());
                detailShare.setMp4(recordDetail.getMp4());
                detailShare.setRecordHour(recordDetail.getRecordHour());
                detailShare.setCreatedAt(new Date());
                detailShare.setUpdatedAt(new Date());
                detailShare.setIsRevised(new Integer(2));
                recordDetailShareService.saveRecordDetailShare(detailShare);
            }
        }
        return AjaxResult.success(true);
    }


    @ApiOperation(value = "检查课程学生是否已经分享-精编版",notes = "检查课程学生是否已经分享-精编版")
    @ShowLogger(info = "检查课程学生是否已经分享-精编版")
    @RequestMapping(path = "/xiaoyi/shareStudent/check",method = RequestMethod.GET)
    public AjaxResult checkShareStudent(CheckShareStudentRequest request){
        List<RecordDetail> recordDetails =  recordDetailService.queryRecordDetailByCourseId(request.getCourseId());
        //List<Integer> lessonIds = request.getLessonIds();
        List<Integer> studentIds = request.getStudentIds();
        List<CheckShareStudentAll> checkShareStudentAlls=new ArrayList<>();
        CheckShareStudentAll checkShareStudentAll;
        for (RecordDetail recordDetail : recordDetails) {
            //RecordDetail recordDetail = recordDetailService.queryRecordDetailById(lessonId);
            if(StringUtils.isEmpty(recordDetail)){
                log.error("该课节不存在！");
                return AjaxResult.error("该课节不存在！");
            }
            for (Integer studentId : studentIds) {
                ShareStudent shareStudent = shareStudentService.queryShareStudentById(studentId);
                if(StringUtils.isEmpty(shareStudent)){
                    log.error("该学生不存在！");
                    return AjaxResult.error("该学生不存在！");
                }
                boolean checkResult = recordDetailShareService.checkShareStudent(request.getCourseId(), recordDetail, shareStudent);
                if(!checkResult){
                 checkShareStudentAll = new CheckShareStudentAll();
                 checkShareStudentAll.setStuMobile(shareStudent.getMobile());
                 checkShareStudentAll.setStudName(shareStudent.getName());
//                 checkShareStudentAll.setCheckFlag(checkResult);
                 String message = checkResult == false?"该学生已获得本视频，不可重复分享":"该学生可以分享此课程";
                 checkShareStudentAll.setCheckMess(message);
                 checkShareStudentAlls.add(checkShareStudentAll);
                }
            }
        }
        return AjaxResult.success(checkShareStudentAlls);
    }


    public RecordCourse checkClassIdExists(Integer courseId) {
        //获取课程ClassId
        RecordCourse recordCourse = recordCourseService.selectByRecordId(courseId);
        if (StringUtils.isEmpty(recordCourse) || StringUtils.isEmpty(recordCourse.getClassId())) {
            return null;
        } else {
            return recordCourse;
        }
    }

}
