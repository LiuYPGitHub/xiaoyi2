package com.xiaoyi.bis.xiaoyi.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.bis.common.annotation.ShowLogger;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.xiaoyi.bean.RecordCourseBean;
import com.xiaoyi.bis.xiaoyi.bean.RecordLessonBean;
import com.xiaoyi.bis.xiaoyi.domain.IntelligentRecord;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourse;
import com.xiaoyi.bis.xiaoyi.domain.RecordCourse;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetail;
import com.xiaoyi.bis.xiaoyi.dto.*;
import com.xiaoyi.bis.xiaoyi.dto.AddRecordCourseRequest;
import com.xiaoyi.bis.xiaoyi.dto.QueryLiveCourseDetailRequest;
import com.xiaoyi.bis.xiaoyi.service.IntelligentRecordService;
import com.xiaoyi.bis.xiaoyi.service.LiveCourseService;
import com.xiaoyi.bis.xiaoyi.service.RecordCourseService;
import com.xiaoyi.bis.xiaoyi.service.RecordDetailService;
import com.xiaoyi.bis.xiaoyi.util.CheckUtil;
import com.xiaoyi.bis.xiaoyi.util.DateUtil;
import com.xiaoyi.bis.xiaoyi.util.FileUtil;
import com.xiaoyi.bis.xiaoyi.util.HtmlUtil;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 录播
 * 添翼申学录播数据接口
 * @author CJ
 * @date 2019/11/11
 */
@SuppressWarnings(value = "all")
@RestController
@Api(tags = {"recordCourse"}, value = "校翼")
@Slf4j
public class RecordCourseController {

    @Autowired
    private IntelligentRecordService intelligentRecordService;
    @Autowired
    private RecordCourseService recordCourseService;
    @Autowired
    private RecordDetailService recordDetailService;
    @Autowired
    private AddRecordCourseAPI addRecordCourseAPI;
    @Autowired
    private AddRecordLessonAPI addRecordLessonAPI;
    @Autowired
    private LiveCourseService liveCourseService;
    @Autowired
    private CheckUtil checkUtil;

    //录播课程添翼申学访问前缀
    @Value(value = "${tysxService.recordCourseAccessPrefix}")
    private String recordCourseAccessPrefix;
    @Value(value = "${xiaoYiService.allowTaste}")
    private Integer allowTaste;
    @Value(value = "${tysxService.siteName}")
    private String siteName;

    //获取录播课程列表
    @ApiOperation(value = "获取录播课程列表", notes = "获取录播课程列表")
    @ShowLogger(info = "获取录播课程列表")
    @RequestMapping(path = "/xiaoyi/recordCourse/list", method = RequestMethod.GET)
    public AjaxResult recordCourseList(QueryRecordCourseRequest request) {
        boolean exists = checkUtil.checkUserIsExists(request.getUserId());
        if (!exists) {
            return AjaxResult.error("用户不存在");
        }
        String admin = liveCourseService.isAdmin(request.getUserId());
        LiveCourse course = new LiveCourse();
        if(StringUtils.isEmpty(admin)||"2".equals(admin)){
            log.info("该用户当前为非管理员权限");
        }else{
            log.info("该用户当前为管理员权限");
            String orgId = liveCourseService.getOrgIdByUserId(request.getUserId());
            request.setOrdCode(orgId);
        }

        request.processPageRequest();
        PageResponse pageResponse = recordCourseService.queryRecordCourseByLike(request);
        return AjaxResult.success(pageResponse);
    }

    @ApiOperation(value = "根据指定条件获取智能录播列表", notes = "根据指定条件获取智能录播列表")
    @ShowLogger(info = "根据指定条件获取智能录播列表")
    @RequestMapping(path = "/xiaoyi/queryIntelligentRecordVideo", method = RequestMethod.GET)
    public AjaxResult queryIntelligentRecordVideo(QueryIntelligentRecordVideoRequest request) {
        List<IntelligentRecord> recordList = intelligentRecordService.queryIntelligentRecordVideo(request);
        return AjaxResult.success(recordList);
    }

    @ApiOperation(value = "获取指定录播课程所属课节列表", notes = "获取指定录播课程所属课节列表")
    @ShowLogger(info = "获取指定录播课程所属课节列表")
    @RequestMapping(path = "/xiaoyi/recordCourseDetail", method = RequestMethod.GET)
    public AjaxResult recordCourseDetailList(QueryLiveCourseDetailRequest request) {
        request.processPageRequest();
        RecordCourse recordCourse = recordCourseService.selectById(request.getCourseId());
        if (StringUtils.isEmpty(recordCourse)) {
            return AjaxResult.error("该录播课程为空");
        }
        //设置课程标签
        if(!StringUtils.isEmpty(recordCourse.getClassTag())){
            List<String> tagsProcess = recordCourseService.classTagsProcess(recordCourse.getClassTag());
            recordCourse.setClassTags(tagsProcess);
        }
        PageResponse response = recordDetailService.queryRecordDetailByCourseId(request.getPageIndex(), request.getPageSize(), request.getCourseId());
        recordCourse.setResponse(response);
        recordCourse.setRecordDetails((List<RecordDetail>) response.getList());
        recordCourse.setCourseType(2);
        return AjaxResult.success(recordCourse);
    }

    @ApiOperation(value = "获取单个指定录播课程", notes = "获取单个指定录播课程")
    @ShowLogger(info = "获取单个指定录播课程")
    @RequestMapping(path = "/xiaoyi/recordCourse/get/{id}", method = RequestMethod.GET)
    public AjaxResult recordCourseById(
            @PathVariable(value = "id") Integer id,
            PageRequest request) {
        RecordCourse recordCourse = recordCourseService.selectById(id);
        if(StringUtils.isEmpty(recordCourse)){
            return AjaxResult.error("未找到相关录播课程！");
        }
        //设置课程标签
        if(!StringUtils.isEmpty(recordCourse.getClassTag())){
            List<String> tagsProcess = recordCourseService.classTagsProcess(recordCourse.getClassTag());
            recordCourse.setClassTags(tagsProcess);
        }
        if(!StringUtils.isEmpty(recordCourse.getClassId())){
            recordCourse.setCopyUrl(recordCourseAccessPrefix+recordCourse.getClassId());
        }
        request.processPageRequest();
        if(!StringUtils.isEmpty(request.getPageIndex())){
            request.setPageIndex(request.getPageIndex());
        }
        if(!StringUtils.isEmpty(request.getPageSize())){
            request.setPageSize(request.getPageSize());
        }
        PageResponse response = recordDetailService.queryRecordDetail(request, id);
        recordCourse.setResponse(response);
        recordCourse.setRecordDetails((List<RecordDetail>) response.getList());
        recordCourse.setCourseType(2);
        return AjaxResult.success(recordCourse);
    }

    @ApiOperation(value = "获取单个指定录播课节", notes = "获取单个指定录播课节")
    @ShowLogger(info = "获取单个指定录播课节")
    @RequestMapping(path = "/xiaoyi/recordCourseDetail/get/{id}", method = RequestMethod.GET)
    public AjaxResult recordCourseDetailById(@PathVariable(value = "id") Integer id) {
        RecordDetail recordDetail = recordDetailService.queryRecordDetailById(id);
        return AjaxResult.success(recordDetail);
    }

    @ShowLogger(info = "添加录播课程")
    @ApiOperation(value = "添加录播课程", notes = "添加录播课程")
    @RequestMapping(path = "/xiaoyi/recordCourse", method = RequestMethod.POST)
    public AjaxResult addRecordCourse(
            @RequestParam(value = "teachersFile",required = true) MultipartFile teachersFile,
            @RequestParam(value = "courseCaseFile",required = true) MultipartFile courseCaseFile,
            AddRecordCourseRequest request
    ) throws IOException {
        CheckUtil.checkImageFile(teachersFile, courseCaseFile);
        String teachersPath = FileUtil.upload(teachersFile);
        String courseCasePath = FileUtil.upload(courseCaseFile);
        //获取当前用户所属机构编号
        String orgCode = liveCourseService.getOrgIdByUserId(request.getUserId());
        log.info("已获取机构编号" + orgCode);

        //保存录播线上课程至内部数据库
        RecordCourse course = saveRecordCourse(request, teachersPath, courseCasePath);

        StringBuffer less = new StringBuffer();

        String requestLessons = request.getLessons();
        List<RecordLessonBean> lessons = new ArrayList<>();
        if (!StringUtils.isEmpty(request.getLessons())) {
            course.setIsRevised(2);
            lessons = JSONObject.parseArray(requestLessons, RecordLessonBean.class);
        }
        String requestCourses = request.getCourses();
        List<RecordCourseBean> courseBeans = new ArrayList<>();
        if (!StringUtils.isEmpty(requestCourses)) {
            course.setIsRevised(1);
            courseBeans = JSONObject.parseArray(requestCourses, RecordCourseBean.class);
        }

        List<RecordDetail> details = new ArrayList<>();
        //添加精编版课节
        for (int i = 0,j=1; i < lessons.size(); i++,j++) {
            RecordDetail recordDetail = recordDetailService.queryRecordDetailById(lessons.get(i).getLessonId());
            if (!StringUtils.isEmpty(recordDetail)) {
                log.info("正在添加精编版课节:" + recordDetail);
                //保存精编版单个课节
                Integer id = course.getId();
                String duration = recordDetail.getDuration();
                String lessonName = lessons.get(i).getLessonName();
                String lessonDesc = lessons.get(i).getLessonDesc();
                String mp4 = recordDetail.getMp4();
                String vid = recordDetail.getVid();
                String classId = course.getClassId();
                Integer allowTaste = recordDetail.getAllowTaste();
                Integer recordHour = Integer.valueOf(recordDetail.getRecordHour());
                RecordDetail recordDetail1 = saveRecordCourseDetail(j,orgCode, id, duration, lessonName, lessonDesc, mp4, vid, classId, allowTaste, recordHour, courseCasePath);
                recordDetail1.setIsRevised(2);
                details.add(recordDetail);
                if (i == lessons.size() - 1) {
                    if(!StringUtils.isEmpty(lessons.get(i).getLessonId())){
                        less.append(lessons.get(i).getLessonId());
                    }
                } else {
                    if(!StringUtils.isEmpty(lessons.get(i).getLessonId())){
                        less.append(lessons.get(i).getLessonId() + ",");
                    }
                }
            }
        }

        //添加完整版课节
        for (int i = 0; i < courseBeans.size(); i++) {
            IntelligentRecord intelligentRecord = intelligentRecordService.queryIntelligentRecordById(courseBeans.get(i).getCourseId());
            if (!StringUtils.isEmpty(intelligentRecord)) {
                log.info("正在添加完整版课节:" + intelligentRecord);
                //添加课节至添翼申学
                Integer id = course.getId();
                String duration = intelligentRecord.getDuration();
                String lessonName = courseBeans.get(i).getLessonName();
                String lessonDesc = courseBeans.get(i).getLessonDesc();
                String mp4 = intelligentRecord.getMp4();
                String vid = intelligentRecord.getVid();
                String classId = course.getClassId();
                Integer recordHour = 1;
                RecordDetail recordDetail1 = saveRecordCourseDetail(1,orgCode, id, duration, lessonName, lessonDesc, mp4, vid, classId, allowTaste, recordHour, courseCasePath);
                recordDetail1.setIsRevised(1);
                details.add(recordDetail1);
                if (i == courseBeans.size() - 1) {
                    if(!StringUtils.isEmpty(courseBeans.get(i))&&!StringUtils.isEmpty(courseBeans.get(i).getCourseId())){
                        less.append(courseBeans.get(i).getCourseId());
                    }
                } else {
                    if(!StringUtils.isEmpty(courseBeans.get(i))&&!StringUtils.isEmpty(courseBeans.get(i).getCourseId())){
                        less.append(courseBeans.get(i).getCourseId() + ",");
                    }
                }
            }
        }
        if (!StringUtils.isEmpty(less)) {
            course.setLessonIds(less.toString());
        }

        recordCourseService.updateRecordCourse(course);
        course.setRecordDetails(details);
        return AjaxResult.success(course);
    }

    @ShowLogger(info = "修改录播课程")
    @ApiOperation(value = "修改录播课程", notes = "修改录播课程")
    @RequestMapping(path = "/xiaoyi/recordCourse", method = RequestMethod.PUT)
    public AjaxResult updateRecordCourse(
            @RequestParam(value = "teachersFile",required = false) MultipartFile teachersFile,
            @RequestParam(value = "courseCaseFile",required = false) MultipartFile courseCaseFile,
            AddRecordCourseRequest request
    ) throws IOException {
        String teachersPath=new String();
        String courseCasePath=new String();
        if(!StringUtils.isEmpty(teachersFile)){
            CheckUtil.checkImageFile(teachersFile);
            teachersPath = FileUtil.upload(teachersFile);
        }
        if(!StringUtils.isEmpty(courseCaseFile)){
            CheckUtil.checkImageFile(courseCaseFile);
            courseCasePath = FileUtil.upload(courseCaseFile);
        }
        //获取当前用户所属机构编号
        String orgCode = liveCourseService.getOrgIdByUserId(request.getUserId());
        log.info("已获取机构编号" + orgCode);

        //保存录播线上课程至内部数据库
        RecordCourse course = updateRecordCourse(request, teachersPath, courseCasePath);

        StringBuffer less = new StringBuffer();

        String requestLessons = request.getLessons();
        List<RecordLessonBean> lessons = new ArrayList<>();
        if (!StringUtils.isEmpty(request.getLessons())) {
            course.setIsRevised(2);
            lessons = JSONObject.parseArray(requestLessons, RecordLessonBean.class);
        }
        String requestCourses = request.getCourses();
        List<RecordCourseBean> courseBeans = new ArrayList<>();
        if (!StringUtils.isEmpty(requestCourses)) {
            course.setIsRevised(1);
            courseBeans = JSONObject.parseArray(requestCourses, RecordCourseBean.class);
        }

        List<RecordDetail> details = new ArrayList<>();
        //添加精编版课节
        for (int i = 0; i < lessons.size(); i++) {
            RecordDetail recordDetail = recordDetailService.queryRecordDetailById(lessons.get(i).getLessonId());
            if (!StringUtils.isEmpty(recordDetail)) {
                log.info("正在修改精编版课节:" + recordDetail);
                //保存精编版单个课节
                Integer id = course.getId();
                String duration = recordDetail.getDuration();
                String lessonName = lessons.get(i).getLessonName();
                String lessonDesc = lessons.get(i).getLessonDesc();
                String mp4 = recordDetail.getMp4();
                String vid = recordDetail.getVid();
                String classId = course.getClassId();
                Integer allowTaste = recordDetail.getAllowTaste();
                Integer recordHour = Integer.valueOf(recordDetail.getRecordHour());
                RecordDetail recordDetail1 = updateRecordCourseDetail(orgCode,lessons.get(i).getLessonId(),id, duration, lessonName, lessonDesc, mp4, vid, classId, allowTaste, recordHour, courseCasePath);
                details.add(recordDetail);
                if (i == lessons.size() - 1) {
                    if(!StringUtils.isEmpty(lessons.get(i).getLessonId())){
                        less.append(lessons.get(i).getLessonId());
                    }
                } else {
                    if(!StringUtils.isEmpty(lessons.get(i).getLessonId())){
                        less.append(lessons.get(i).getLessonId() + ",");
                    }
                }
            }
        }

        //添加完整版课节
        for (int i = 0; i < courseBeans.size(); i++) {
            IntelligentRecord intelligentRecord = intelligentRecordService.queryIntelligentRecordById(request.getRecordId());
            if (!StringUtils.isEmpty(intelligentRecord)) {
                log.info("正在修改完整版课节:" + intelligentRecord);
                //添加课节至添翼申学
                Integer id = course.getId();
                String duration = intelligentRecord.getDuration();
                String lessonName = courseBeans.get(i).getLessonName();
                String lessonDesc = courseBeans.get(i).getLessonDesc();
                String mp4 = intelligentRecord.getMp4();
                String vid = intelligentRecord.getVid();
                String classId = course.getClassId();
                Integer recordHour = 1;
                RecordDetail recordDetail1 = updateRecordCourseDetail(orgCode,courseBeans.get(i).getLessonId(),id, duration, lessonName, lessonDesc, mp4, vid, classId, allowTaste, recordHour, courseCasePath);
                details.add(recordDetail1);
                if (i == courseBeans.size() - 1) {
                    if(!StringUtils.isEmpty(courseBeans.get(i))&&!StringUtils.isEmpty(courseBeans.get(i).getCourseId())){
                        less.append(courseBeans.get(i).getCourseId());
                    }
                } else {
                    if(!StringUtils.isEmpty(courseBeans.get(i))&&!StringUtils.isEmpty(courseBeans.get(i).getCourseId())){
                        less.append(courseBeans.get(i).getCourseId() + ",");
                    }
                }
            }
        }

        if(details.size()<=0){
            details = recordDetailService.queryRecordDetailByCourseId(request.getCourseId());
        }

        recordCourseService.updateRecordCourse(course);
        course.setRecordDetails(details);
        log.info("course:"+course);
        return AjaxResult.success(course);
    }

    @ShowLogger(info = "提交录播课程")
    @ApiOperation(value = "提交录播课程", notes = "提交录播课程")
    @RequestMapping(path = "/xiaoyi/recordCourse/submit", method = RequestMethod.POST)
    public AjaxResult submitRecordCourse(SubmitRecordCourseRequest request) throws IOException {
        RecordCourse recordCourse = recordCourseService.selectById(request.getCourseId());
        if (!StringUtils.isEmpty(recordCourse)) {
            com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddRecordCourseRequest recordCourseRequest = new com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddRecordCourseRequest();
            //添加添翼申学录播线上课
            String expirationDuration = request.getExpirationDuration();
            recordCourseRequest.setName(request.getCourseName());

            recordCourseRequest.setCourseIntroduceImg(recordCourse.getCourseIntroduceImg());
            recordCourseRequest.setCoverImgLink(recordCourse.getCourseIntroduceImg());
            if (!StringUtils.isEmpty(request.getEnrollStartDate()) && !StringUtils.isEmpty(request.getEnrollEndDate())) {
                String start = DateFormatUtils.format(request.getEnrollStartDate(), "yyyyMMddHHmmss");
                String end = DateFormatUtils.format(request.getEnrollEndDate(), "yyyyMMddHHmmss");
                recordCourseRequest.setEnrollStartDate(start);
                recordCourseRequest.setEnrollEndDate(end);
            }
            recordCourseRequest.setExpirationDuration(expirationDuration);
            recordCourseRequest.setClassHour(String.valueOf(request.getClassNum()));
            //设置为线上录播课 1:线上 2:线下
            recordCourseRequest.setRecordType("1");
            recordCourseRequest.setClassType(request.getClassType());
            recordCourseRequest.setPrimeCost(String.valueOf(recordCourse.getCost()));
            recordCourseRequest.setCost(String.valueOf(recordCourse.getPrice()));
            recordCourseRequest.setSiteName(siteName);
            recordCourseRequest.setMaxNum("99999");
            String classDifficult = HtmlUtil.getClassDifficult(Integer.valueOf(recordCourse.getClassDifficult()));
            recordCourse.setClassDifficult(String.valueOf(recordCourse.getClassDifficult()));
            HtmlUtil.processCourseHtml(classDifficult, recordCourse, recordCourseRequest);

            AddRecordCourseResponse recordCourseResponse = addRecordCourseAPI.process(recordCourseRequest);
            String classId = recordCourseResponse.getClassId();
            recordCourse.setClassId(classId);
            recordCourse.setStatus(1);

            //添加课节信息至添翼申学
            List<String> lessionIds = request.getLessionIds();
            for (String lessionId : lessionIds) {
                RecordDetail recordDetail = recordDetailService.getById(lessionId);
                if(!StringUtils.isEmpty(recordDetail)){
                    AddRecordLessonRequest recordLessonRequest = new AddRecordLessonRequest();
                    recordLessonRequest.setClassId(classId);
                    //recordLessonRequest.setTeacherId("8a8880866e1a5c23016e1b592a790126");
                    recordLessonRequest.setAllowTaste(String.valueOf(allowTaste));
                    recordLessonRequest.setCoverImgLink(recordCourse.getCourseIntroduceImg());
                    if(!StringUtils.isEmpty(recordDetail.getRecordHour())){
                        recordLessonRequest.setRecordHour(String.valueOf(recordDetail.getRecordHour()));
                    }else{
                        recordLessonRequest.setRecordHour("1");
                    }
                    recordLessonRequest.setTitle(recordDetail.getLessonName());
                    recordLessonRequest.setVid(recordDetail.getVid());
                    recordLessonRequest.setVideoDuration("222");
                    AddRecordLessonResponse recordLessonResponse = addRecordLessonAPI.process(recordLessonRequest);
                    String tylessonId = recordLessonResponse.getLessonId();
                    recordDetail.setLessonId(tylessonId);
                    recordDetailService.updateById(recordDetail);
                }else{
                    log.error("未找到["+lessionId+"]相关课节信息");
                }
            }
            int updateRecordCourse = recordCourseService.updateRecordCourse(recordCourse);
            return AjaxResult.success(updateRecordCourse > 0 ? true : false);
        } else {
            return AjaxResult.error("提交失败，该课程不存在！");
        }
    }

    /**
     * 修改录播线上课程至内部数据库
     *
     * @param request        添加录播课程参数
     * @param teachersPath   教师图片
     * @param courseCasePath 课程图片
     * @return 添加的录播课程编号
     */
    public RecordCourse updateRecordCourse(AddRecordCourseRequest request, String teachersPath, String courseCasePath) {
        //保存录播线上课程至内部数据库
        RecordCourse course = recordCourseService.getById(request.getCourseId());
        course.setMaxCount(request.getMaxCount());
        course.setId(request.getCourseId());
        course.setCreateName(request.getUserId());
        course.setExpirationDuration(request.getExpirationDuration());
        String orgId = liveCourseService.getOrgIdByUserId(request.getUserId());
        course.setUpdatedAt(new Date());
        /*course.setCreateDate(new Date());
        course.setCreatedAt(new Date());*/
        course.setOrgCode(orgId);
        //设置成未提交状态
        course.setStatus(new Integer(0));
        course.setCourseName(request.getCourseName());
        course.setTeacheName(request.getTeacherName());
        course.setTeacherInfo(request.getTeacherInfo());
        course.setDesc(request.getDesc());
        course.setClassType(request.getClassType());
        if(!StringUtils.isEmpty(teachersPath)){
            course.setTeacherImage(teachersPath);
        }
        if(!StringUtils.isEmpty(courseCasePath)){
            course.setCourseIntroduceImg(courseCasePath);
            course.setCoverImg(courseCasePath);
        }
        String tag = HtmlUtil.processTags(request.getClassTags());
        course.setClassTag(tag);
        String classDifficult = HtmlUtil.getClassDifficult(request.getClassDifficultCount());
        course.setClassDifficult(String.valueOf(request.getClassDifficultCount()));
        course.setCourseNum(request.getClassNum());
        course.setStudentNum(request.getMaxCount().toString());
        course.setCourseObj(request.getCourseObj());
        StringBuffer feature=new StringBuffer();
        if(!StringUtils.isEmpty(request.getCourseFeature1())){
            feature.append(request.getCourseFeature1()+"|");
        }
        if(!StringUtils.isEmpty(request.getCourseFeature2())){
            feature.append(request.getCourseFeature2()+"|");
        }
        if(!StringUtils.isEmpty(request.getCourseFeature3())){
            feature.append(request.getCourseFeature3()+"|");
        }
        course.setCourseFeature(feature.toString());
        course.setCourseContent(request.getCourseContent());
        course.setClassInfo(request.getClassInfo());
        course.setTextBook(request.getTextBook());
        course.setCost(request.getPriceCost());
        course.setPrice(request.getPrice());
        course.setIsShow(new Integer(1));
        course.setIsDel(new Integer(1));
        course.setIsOnline(new Integer(1));
        course.setEnrollStartDate(request.getEnrollStartDate());
        course.setEnrollEndDate(request.getEnrollEndDate());
        course.setShowEvaluation(request.getShowEvaluation());
        String mobile = recordCourseService.getMobileByUid(request.getUserId());
        course.setCreateMobile(mobile);
        course.setIsOnline(1);
        course.setCourseType(2);
        course.setClassHour(request.getClassNum());
        recordCourseService.updateRecordCourse(course);

        return course;
    }

    /**
     * 添加录播线上课程至内部数据库
     *
     * @param request        添加录播课程参数
     * @param teachersPath   教师图片
     * @param courseCasePath 课程图片
     * @return 添加的录播课程编号
     */
    public RecordCourse saveRecordCourse(AddRecordCourseRequest request, String teachersPath, String courseCasePath) {
        //保存录播线上课程至内部数据库
        RecordCourse course = new RecordCourse();
        course.setMaxCount(request.getMaxCount());
        course.setCreateName(request.getUserId());
        course.setExpirationDuration(request.getExpirationDuration());
        String orgId = liveCourseService.getOrgIdByUserId(request.getUserId());
        course.setUpdatedAt(new Date());
        course.setCreateDate(new Date());
        course.setCreatedAt(new Date());
        course.setOrgCode(orgId);
        //设置成未提交状态
        course.setStatus(new Integer(0));
        course.setCourseName(request.getCourseName());
        course.setTeacheName(request.getTeacherName());
        course.setTeacherInfo(request.getTeacherInfo());
        course.setDesc(request.getDesc());
        course.setClassType(request.getClassType());
        course.setTeacherImage(teachersPath);
        course.setCourseIntroduceImg(courseCasePath);
        course.setCoverImg(courseCasePath);
        String tag = HtmlUtil.processTags(request.getClassTags());
        course.setClassTag(tag);
        String classDifficult = HtmlUtil.getClassDifficult(request.getClassDifficultCount());
        course.setClassDifficult(String.valueOf(request.getClassDifficultCount()));
        course.setCourseNum(request.getClassNum());
        course.setStudentNum(request.getMaxCount().toString());
        course.setCourseObj(request.getCourseObj());
        StringBuffer feature=new StringBuffer();
        if(!StringUtils.isEmpty(request.getCourseFeature1())){
            feature.append(request.getCourseFeature1()+"|");
        }
        if(!StringUtils.isEmpty(request.getCourseFeature2())){
            feature.append(request.getCourseFeature2()+"|");
        }
        if(!StringUtils.isEmpty(request.getCourseFeature3())){
            feature.append(request.getCourseFeature3()+"|");
        }
        course.setCourseFeature(feature.toString());
        course.setCourseContent(request.getCourseContent());
        course.setClassInfo(request.getClassInfo());
        course.setTextBook(request.getTextBook());
        course.setCost(request.getPriceCost());
        course.setPrice(request.getPrice());
        course.setIsShow(new Integer(1));
        course.setIsDel(new Integer(1));
        course.setIsOnline(new Integer(1));
        course.setEnrollStartDate(request.getEnrollStartDate());
        course.setEnrollEndDate(request.getEnrollEndDate());
        course.setShowEvaluation(request.getShowEvaluation());
        String mobile = recordCourseService.getMobileByUid(request.getUserId());
        course.setCreateMobile(mobile);
        course.setIsOnline(1);
        course.setCourseType(2);
        course.setRecordId(request.getRecordId());
        recordCourseService.saveRecordCourse(course);

        return course;
    }

    /**
     * 修改录播课节线上课程至内部数据库
     *
     * @param courseId       课程编号
     * @param duration       视频时长
     * @param lessionName    课节名称
     * @param lessionDesc    课节备注
     * @param mp4            保利威视频地址
     * @param vid            保利威视频编号
     * @param classId        添翼申学课程编号
     * @param allowTaste     是否试看
     * @param recordHour     课节节时
     * @param courseCasePath 课程图片
     */
    public RecordDetail updateRecordCourseDetail(
            String orgCode,Integer lessonId,Integer courseId, String duration, String lessionName,
            String lessionDesc, String mp4, String vid, String classId,
            Integer allowTaste, Integer recordHour, String courseCasePath) {
        //保存精编版单个课节
        RecordDetail detail = new RecordDetail();
        //课节主键
        detail.setId(lessonId);
        detail.setOrgCode(orgCode);
        detail.setDuration(duration);
        detail.setCourseId(courseId);
        //detail.setLessonName(recordDetail.getLessonName());
        detail.setLessonName(lessionName);
        if (!StringUtils.isEmpty(lessionDesc)) {
            detail.setLessonDesc(lessionDesc);
        }
        detail.setVid(vid);
        detail.setMp4(mp4);
        detail.setCreatedAt(new Date());
        detail.setUpdatedAt(new Date());
        detail.setRecordHour(Integer.valueOf(recordHour));
        detail.setAllowTaste(Integer.valueOf(allowTaste));
        detail.setStatus(1);
        //recordDetailService.updateById(detail);
        recordDetailService.saveOrUpdate(detail);
        return detail;
    }


    /**
     * 添加录播课节线上课程至内部数据库
     *
     * @param courseId       课程编号
     * @param duration       视频时长
     * @param lessionName    课节名称
     * @param lessionDesc    课节备注
     * @param mp4            保利威视频地址
     * @param vid            保利威视频编号
     * @param classId        添翼申学课程编号
     * @param allowTaste     是否试看
     * @param recordHour     课节节时
     * @param courseCasePath 课程图片
     */
    public RecordDetail saveRecordCourseDetail(
            Integer num,String orgCode, Integer courseId, String duration, String lessionName,
            String lessionDesc, String mp4, String vid, String classId,
            Integer allowTaste, Integer recordHour, String courseCasePath) {
        //保存精编版单个课节
        RecordDetail detail = new RecordDetail();
        detail.setLessonNum(String.valueOf(num));
        detail.setOrgCode(orgCode);
        detail.setDuration(duration);
        detail.setCourseId(courseId);
        //detail.setLessonName(recordDetail.getLessonName());
        detail.setLessonName(lessionName);
        if (!StringUtils.isEmpty(lessionDesc)) {
            detail.setLessonDesc(lessionDesc);
        }
        detail.setVid(vid);
        detail.setMp4(mp4);
        detail.setCreatedAt(new Date());
        detail.setUpdatedAt(new Date());
        detail.setRecordHour(Integer.valueOf(recordHour));
        detail.setAllowTaste(Integer.valueOf(allowTaste));
        detail.setStatus(1);
        recordDetailService.save(detail);
        return detail;
    }


    @ShowLogger(info = "删除录播课程的指定课节")
    @ApiOperation(value = "删除录播课程的指定课节", notes = "删除录播课程的指定课节")
    @RequestMapping(path = "/xiaoyi/recordCourse/{id}", method = RequestMethod.DELETE)
    public AjaxResult updateRecordCourse(@PathVariable Integer id){
        RecordDetail recordDetail = recordDetailService.queryRecordDetailById(id);
        if(StringUtils.isEmpty(recordDetail)){
            log.error("该课节不存在!");
            return AjaxResult.error("该课节不存在！");
        }else{
            boolean remove = recordDetailService.removeById(recordDetail);
            return AjaxResult.success(remove);
        }
    }

}
