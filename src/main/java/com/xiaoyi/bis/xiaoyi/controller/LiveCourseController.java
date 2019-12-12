package com.xiaoyi.bis.xiaoyi.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.bis.common.annotation.ShowLogger;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.xiaoyi.bean.LiveLessonBean;
import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourse;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourseDetail;
import com.xiaoyi.bis.xiaoyi.domain.XiaoYiTeacher;
import com.xiaoyi.bis.xiaoyi.dto.*;
import com.xiaoyi.bis.xiaoyi.dto.AddLiveCourseRequest;
import com.xiaoyi.bis.xiaoyi.dto.QueryLiveCourseDetailRequest;
import com.xiaoyi.bis.xiaoyi.exception.ServiceException;
import com.xiaoyi.bis.xiaoyi.service.LiveCourseService;
import com.xiaoyi.bis.xiaoyi.service.LiveCourseDetailService;
import com.xiaoyi.bis.xiaoyi.service.RecordCourseService;
import com.xiaoyi.bis.xiaoyi.service.XiaoYiTeacherService;
import com.xiaoyi.bis.xiaoyi.util.*;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 校翼-直播课程接口
 * @author CJ
 * @date 2019/10/16
 */
@SuppressWarnings(value = "all")
@Api(tags = {"liveCourse"},value = "校翼")
@RestController
@Validated
@Slf4j
public class LiveCourseController {

    @Autowired
    private LiveCourseDetailService liveCourseDetailService;
    @Autowired
    private XiaoYiTeacherService xiaoYiTeacherService;
    @Autowired
    private RecordCourseService recordCourseService;
    @Autowired
    private LiveCourseService liveCourseService;
    @Autowired
    private AddLiveCourseAPI addLiveCourseAPI;
    @Autowired
    private AddLiveLessonAPI addLiveLessonAPI;
    @Autowired
    private AddTeacherAPI addTeacherAPI;
    @Autowired
    private CheckUtil checkUtil;

    //直播课程添翼申学访问前缀
    @Value(value = "${tysxService.liveCourseAccessPrefix}")
    private String liveCourseAccessPrefix;

    @ShowLogger(info = "校翼-直播课程列表")
    @ApiOperation(value = "校翼-直播课程列表",notes = "校翼-直播课程列表")
    @RequestMapping(path = "/xiaoyi/liveCourse/list",method = RequestMethod.GET)
    public AjaxResult queryLiveCourse(QueryLiveCourseRequest request){
        request.processPageRequest();
        if(StringUtils.isEmpty(request.getUserId())){
            return AjaxResult.error("用户编号为空");
        }
        boolean exists = checkUtil.checkUserIsExists(request.getUserId());
        if(!exists){
            return AjaxResult.error("用户不存在");
        }
        PageResponse pageResponse = liveCourseService.selectByLike(request, getLiveCourse(request));
        return AjaxResult.success(pageResponse);
    }

    /**
     * 获取列表的直播课程对象
     * @param request
     * @return
     */
    public LiveCourse getLiveCourse(QueryLiveCourseRequest request){
        String admin = liveCourseService.isAdmin(request.getUserId());
        LiveCourse course = new LiveCourse();
        if(StringUtils.isEmpty(admin)||"2".equals(admin)){
            log.info("该用户当前为非管理员权限");
            course.setCreateName(request.getUserId());
        }else{
            log.info("该用户当前为管理员权限");
            String orgId = liveCourseService.getOrgIdByUserId(request.getUserId());
            course.setOrgCode(orgId);
        }
        if(!StringUtils.isEmpty(request.getKey())){
            course.setCode(request.getKey());
        }
        if(!StringUtils.isEmpty(request.getOrderType())){
            course.setOrderType(String.valueOf(request.getOrderType()));
        }
        if(!StringUtils.isEmpty(request.getStatus())){
            course.setStatus(request.getStatus());
        }else{
            course.setStatus(-1);
        }
        return course;
    }


    @ShowLogger(info = "校翼-直播课程详情")
    @ApiOperation(value = "校翼-直播课程详情",notes = "校翼-直播课程详情")
    @RequestMapping(path = "/xiaoyi/liveCourseDetail",method = RequestMethod.GET)
    public AjaxResult queryLiveCourseById(QueryLiveCourseDetailRequest request){
        request.processPageRequest();
        CheckUtil.checkId(String.valueOf(request.getCourseId()));
        LiveCourse course = liveCourseService.queryCourseById(request.getCourseId());
        if(StringUtils.isEmpty(course)){
            return AjaxResult.error("未找到该直播课程信息");
        }
        XiaoYiTeacher xiaoYiTeacher = xiaoYiTeacherService.getById(course.getTeacherId());
        course.setTeacherPhone(xiaoYiTeacher.getMobile());
        PageResponse pageResponse = liveCourseDetailService.queryCourseDetailList(request.getCourseId(),request.getPageIndex(),request.getPageSize());
        course.setPageResponse(pageResponse);
        String classTag = course.getClassTag();
        if(!StringUtils.isEmpty(classTag)){
            course.setClassTags(classTag.split("&"));
        }
        if(!StringUtils.isEmpty(course.getClassDifficult())){
            course.setClassDifficultCount(FindUtil.findCount(course.getClassDifficult(), "★"));
        }
        List<LiveCourseDetail> details = (List<LiveCourseDetail>) pageResponse.getList();
        for (LiveCourseDetail detail : details) {
            detail.setLessonNum(1);
        }
        course.setLiveCourseDetails(details);
        return AjaxResult.success(course);
    }

    @ShowLogger(info = "校翼-添加直播课程")
    @ApiOperation(value = "校翼-添加直播课程",notes = "校翼-添加直播课程")
    @RequestMapping(path = "/xiaoyi/liveCourse",method = RequestMethod.POST)
    public AjaxResult addLiveCourse(
            @RequestParam(value = "teachersFile") MultipartFile teachersFile,
            @RequestParam(value = "courseCaseFile") MultipartFile courseCaseFile,
            AddLiveCourseRequest request
    ) throws IOException {
        CheckUtil.checkImageFile(teachersFile,courseCaseFile);
        String teachersPath = FileUtil.upload(teachersFile);
        String courseCasePath = FileUtil.upload(courseCaseFile);
        LiveCourse course = saveLiveCourse(request, teachersPath, courseCasePath);
        course.setCourseType(1);
        return AjaxResult.success(course);
    }

    /**
     * 保存直播课程至内部数据库中
     * @param request 用户输入直播课程参数
     * @param teachersPath 教师图片访问路径
     * @param courseCasePath 课程图片访问路径
     */
    public LiveCourse saveLiveCourse(AddLiveCourseRequest request,String teachersPath,String courseCasePath){
        //根据当前用户编号查询所属机构
        String orgCode = liveCourseService.getOrgIdByUserId(request.getUserId());
        log.info("已获取机构编号"+orgCode);
        XiaoYiTeacher teacher=new XiaoYiTeacher();
        teacher.setMobile(request.getTeacherPhone());
        teacher.setOrgCode(orgCode);
        xiaoYiTeacherService.saveTeacher(teacher);
        LiveCourse course=new LiveCourse();
        course.setTeacherPhone(request.getTeacherPhone());
        course.setTeacherId(teacher.getTid());
        course.setMaxCount(request.getMaxCount());
        course.setCreatedAt(new Date());
        course.setOrgCode(orgCode);
        //设置成为提交状态
        course.setStatus(0);
        course.setCourseName(request.getCourseName());
        course.setTeacherName(request.getTeacherName());
        course.setTeacherInfo(request.getTeacherInfo());
        course.setDesc(request.getDesc());
        course.setClassType(request.getClassType());
        if(request.getClassTags().size()>0){
            String classTag = HtmlUtil.processTags(request.getClassTags());
            course.setClassTag(classTag);
        }
        String classDifficult = HtmlUtil.getClassDifficult(request.getClassDifficultCount());
        course.setClassDifficult(String.valueOf(classDifficult));
        //课程封面
        course.setCoverImg(courseCasePath);
        course.setCourseIntroduceImg(courseCasePath);
        course.setCourseNum(request.getClassNum());
        course.setClassHour(String.valueOf(request.getClassNum()));
        course.setCoursePrompt(request.getDesc());
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
        course.setPrice(request.getPrice());
        //添加原价
        course.setPriceCost(request.getPriceCost());
        course.setIsDel(1);
        course.setIsShow(1);
        course.setCreateDate(new Date());
        course.setEnrollStartDate(request.getEnrollStartDate());
        course.setEnrollEndDate(request.getEnrollEndDate());
        course.setUpdatedAt(new Date());
        course.setTeacherImage(teachersPath);
        course.setShowEvaluation(0);
        course.setExpirationDuration(request.getExpirationDuration());
        //String orgName = liveCourseService.getSiteNameByUserId(request.getUserId());
        course.setCreateName(request.getUserId());
        String mobile = recordCourseService.getMobileByUid(request.getUserId());
        if(!StringUtils.isEmpty(mobile)){
            course.setCreateMobile(mobile);
        }else{
            course.setCreateMobile("16621242385");
        }
        liveCourseService.saveCourseInfo(course);

        List<LiveCourseDetail> courseDetails = liveLessonProcess(request.getLessons(), request.getCourseId(), orgCode);
        //添加直播课程课节信息
        course.setLiveCourseDetails(courseDetails);
        liveCourseDetailService.saveXiaoYiCourseDetail(course);

        return course;
    }

    @ShowLogger(info = "校翼-修改单个直播课程")
    @ApiOperation(value = "校翼-修改单个直播课程",notes = "校翼-修改单个直播课程")
    @RequestMapping(path = "/xiaoyi/liveCourse",method = RequestMethod.PUT)
    public AjaxResult updateLiveCourse(
            @RequestParam(value = "teachersFile",required = false) MultipartFile teachersFile,
            @RequestParam(value = "courseCaseFile",required = false) MultipartFile courseCaseFile,
            UpdateLiveCourseRequest request
    ) throws IOException {
        String teachersPath = new String();
        if(!StringUtils.isEmpty(teachersFile)){
            teachersPath = FileUtil.upload(teachersFile);
        }
        String courseCasePath = new String();
        if(!StringUtils.isEmpty(teachersFile)){
            courseCasePath = FileUtil.upload(courseCaseFile);
        }
        LiveCourse course = updateLiveCourse(request, teachersPath, courseCasePath);
        course.setCourseType(1);
        return AjaxResult.success(course);
    }

    /**
     * 修改直播课程、课节信息
     * @param request 用户输入直播课程参数
     * @param teachersPath 教师图片访问路径
     * @param courseCasePath 课程图片访问路径
     */
    public LiveCourse updateLiveCourse(UpdateLiveCourseRequest request, String teachersPath, String courseCasePath){
        LiveCourse course = liveCourseService.queryCourseById(request.getCourseId());
        //修改关联的校翼教师手机号码
        log.info("正在修改["+course.getTeacherId()+"]关联的教师手机号码");
        XiaoYiTeacher xiaoYiTeacher = xiaoYiTeacherService.getById(course.getTeacherId());
        xiaoYiTeacher.setMobile(request.getTeacherPhone());
        boolean update = xiaoYiTeacherService.updateById(xiaoYiTeacher);
        if(update){
            log.info("修改["+course.getTeacherId()+"]关联教师信息结果:修改成功");
        }else{
            log.info("修改["+course.getTeacherId()+"]关联教师信息结果:修改失败");
        }
        course.setMaxCount(request.getMaxCount());
        course.setTeacherPhone(request.getTeacherPhone());
        //根据当前用户编号查询所属机构
        String orgCode = liveCourseService.getOrgIdByUserId(request.getUserId());
        log.info("已获取机构编号"+orgCode);
        course.setOrgCode(orgCode);
        course.setCourseName(request.getCourseName());
        course.setTeacherName(request.getTeacherName());
        course.setTeacherInfo(request.getTeacherInfo());
        course.setDesc(request.getDesc());
        course.setClassType(request.getClassType());
        String classTag = HtmlUtil.processTags(request.getClassTags());
        course.setClassTag(classTag);
        String classDifficult = HtmlUtil.getClassDifficult(request.getClassDifficultCount());
        course.setClassDifficult(String.valueOf(classDifficult));
        //课程封面
        if(!StringUtils.isEmpty(courseCasePath)){
            course.setCoverImg(courseCasePath);
            course.setCourseIntroduceImg(courseCasePath);
        }
        course.setCourseNum(request.getClassNum());
        course.setClassHour(String.valueOf(request.getClassNum()));
        course.setCoursePrompt(request.getDesc());
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
        course.setPrice(request.getPrice());
        //添加原价
        course.setPriceCost(request.getPriceCost());
        course.setIsDel(1);
        course.setIsShow(1);
        course.setEnrollStartDate(request.getEnrollStartDate());
        course.setEnrollEndDate(request.getEnrollEndDate());
        course.setUpdatedAt(new Date());
        if(!StringUtils.isEmpty(teachersPath)){
            course.setTeacherImage(teachersPath);
        }
        course.setShowEvaluation(0);
        course.setExpirationDuration(request.getExpirationDuration());
        //String orgName = liveCourseService.getSiteNameByUserId(request.getUserId());
        course.setCreateName(request.getUserId());
        String mobile = recordCourseService.getMobileByUid(request.getUserId());
        course.setCreateMobile(mobile);

        liveCourseService.updateCourse(course);

        //处理直播课节json数据
        List<LiveCourseDetail> courseDetails = liveLessonProcess(request.getLessons(), request.getCourseId(), orgCode);
        course.setLiveCourseDetails(courseDetails);
        //修改直播课程课节信息
        liveCourseDetailService.updateXiaoYiCourseDetail(course);

        return course;
    }

    /**
     * 处理直播课节json数据
     * @param lessons
     * @param courseId
     * @param orgCode
     * @return
     */
    public List<LiveCourseDetail> liveLessonProcess(String lessons,Integer courseId,String orgCode) {
        List<LiveCourseDetail> courseDetails=new ArrayList<>();
        if(!StringUtils.isEmpty(lessons)){
            List<LiveLessonBean> liveLessonBeans = JSONObject.parseArray(lessons, LiveLessonBean.class);
            LiveCourseDetail detail;
            for (int i = 0,j=1; i < liveLessonBeans.size(); i++,j++) {
                detail = new LiveCourseDetail();
                LiveLessonBean lessonBean = liveLessonBeans.get(i);
                if(!StringUtils.isEmpty(lessonBean.getLessionId())){
                    detail.setId(lessonBean.getLessionId());
                }
                detail.setCourseId(courseId);
                detail.setLessonName(lessonBean.getLessonName());
                detail.setLessonNum(j);
                detail.setStartDate(lessonBean.getStartDate());
                detail.setEndDate(lessonBean.getEndDate());
                detail.setOrgCode(orgCode);
                courseDetails.add(detail);
            }
        }
        return courseDetails;
    }

    @ShowLogger(info = "校翼-提交直播课程")
    @ApiOperation(value = "校翼-提交直播课程",notes = "校翼-提交直播课程")
    @RequestMapping(path = "/xiaoyi/liveCourse/submit/{id}",method = RequestMethod.POST)
    public AjaxResult submitLiveCourse(@PathVariable(value = "id")Integer id) throws Exception {
        LiveCourse cours = liveCourseService.queryCourseById(id);
        //获取关联的教师信息
        XiaoYiTeacher xiaoYiTeacher = xiaoYiTeacherService.getById(cours.getTeacherId());
        if(StringUtils.isEmpty(xiaoYiTeacher.getTeacherId())){
            AddTeacherResponse response = addTeacherAPI.process(new AddTeacherRequest(xiaoYiTeacher.getMobile(), APIConstant.API_SITENAMEVALUE));
            xiaoYiTeacher.setTeacherId(response.getTeacherId());
            xiaoYiTeacherService.updateById(xiaoYiTeacher);
        }
        //实时向添翼申学添加直播课程
        String classId = saveTYliveCourse(cours);
        cours.setClassId(classId);
        //实时向添翼申学添加直播课程所属的课节信息
        saveTYliveCourseDetail(cours,xiaoYiTeacher);
        //运营人员暂时不审核
        cours.setStatus(5);
        int updateCourse = liveCourseService.updateCourse(cours);
        //int sumbitCourse = liveCourseService.sumbitCourse(id);
        return AjaxResult.success(updateCourse>0?true:false);
    }

    /**
     * 实时向添翼申学添加直播课程
     * @param cours 用户输入直播课程信息
     * @return 添翼申学添加课程classId结果
     * @throws Exception
     */
    private String saveTYliveCourse(LiveCourse cours) throws Exception {
        log.info("正在添加直播课程[" + cours.getCourseName() + "]信息");
        com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddLiveCourseRequest request = new com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddLiveCourseRequest();
        request.setShowEvaluation(cours.getShowEvaluation().toString());
        request.setExpirationDuration(cours.getExpirationDuration());
        request.setName(cours.getCourseName());
        request.setClassHour(cours.getClassHour());
        request.setClassType(cours.getClassType());
        request.setCost(cours.getPrice().toString());
        request.setPrimeCost(cours.getPrice().toString());
        request.setCoverImgLink(cours.getCoverImg());
        if (cours != null && cours.getEnrollStartDate() != null && cours.getEnrollEndDate() != null) {
            request.setEnrollStartDate(DateFormatUtils.format(cours.getEnrollStartDate(), "yyyyMMddHHmmss"));
            request.setEnrollEndDate(DateFormatUtils.format(cours.getEnrollEndDate(), "yyyyMMddHHmmss"));
        } else {
            Calendar instance = Calendar.getInstance();
            request.setEnrollStartDate(instance.getTime().toString());
            instance.add(Calendar.YEAR, 2);
            request.setEnrollEndDate(instance.getTime().toString());
        }
        request.setCourseIntroduceImg(cours.getCourseIntroduceImg());
        //课程信息
        String desc = HtmlUtil.getCourseInfo(cours.getClassDifficult(), cours.getClassInfo(), cours.getCourseObj(), cours.getTextBook());
        request.setCourseInformation(desc);
        //师资介绍
        String teacherInfo = HtmlUtil.getTeacherInfo(cours.getTeacherInfo(), cours.getTeacherName(), cours.getTeacherImage());
        request.setCourseTeachersHighlight(teacherInfo);
        //课程亮点
        String courseFeature = HtmlUtil.getCourseFeatureInfo(cours.getCourseFeature());
        request.setCourseHighlight(courseFeature);
        //学习内容
        String contentInfo = HtmlUtil.getCourseContentInfo(cours.getCourseContent());
        request.setCourseLearningContent(contentInfo);
        //观看方式
        String viewTypeInfo = HtmlUtil.getViewTypeInfo();
        request.setCourseObservationStyle(viewTypeInfo);
        //课程咨询
        String consultantInfo = HtmlUtil.getCourseConsultantInfo();
        request.setCourseConsultant(consultantInfo);
        //温馨提示
        String coursePromptInfo = HtmlUtil.getCoursePromptInfo();
        request.setCourseWarmPrompt(coursePromptInfo);
        request.setMaxNum(StringUtils.isEmpty(cours.getMaxCount()) ? "500" : String.valueOf(cours.getMaxCount()));
        AddLiveCourseResponse addLiveCourseResponse = addLiveCourseAPI.process(request);
        log.info("添加直播课程[" + cours.getCourseName() + "]信息状态码:"+addLiveCourseResponse.getCode());
        return StringUtils.isEmpty(addLiveCourseResponse.getClassId())?"":addLiveCourseResponse.getClassId();
    }

    /**
     * 实时向添翼申学添加直播课程所属的课节信息
     * @param cours 用户输入直播课程信息
     * @param xiaoYiTeacher 用户输入的手机号码添加的添翼申学教师信息
     */
    private void saveTYliveCourseDetail(LiveCourse cours,XiaoYiTeacher xiaoYiTeacher){
        List<LiveCourseDetail> liveCourseDetails = liveCourseDetailService.queryCourseDetailById(cours.getId());
        for (LiveCourseDetail detail : liveCourseDetails) {
            log.info("已获取直播课程[" + cours.getCourseName() + "] 课节[" + detail.getLessonName() + "]信息");
            if (StringUtils.isEmpty(detail.getLessonId())) {
                AddLiveLessonRequest addLiveLessonRequest = new AddLiveLessonRequest();
                addLiveLessonRequest.setClassId(cours.getClassId());
                addLiveLessonRequest.setTeacherId(xiaoYiTeacher.getTeacherId());
                int intValue = DateUtil.getRecordHour(detail.getEndDate().getTime(),detail.getStartDate().getTime()).intValue();
                addLiveLessonRequest.setLessonHour(String.valueOf(intValue));
                addLiveLessonRequest.setLiveContent(detail.getLessonName());
                if (DateUtil.checkDate(new Date(), detail.getStartDate(), detail.getEndDate())) {
                    String startDate = DateFormatUtils.format(detail.getStartDate(), "yyyyMMddHHmmss");
                    String endDate = DateFormatUtils.format(detail.getEndDate(), "yyyyMMddHHmmss");
                    addLiveLessonRequest.setLiveStartDate(startDate);
                    addLiveLessonRequest.setLiveEndDate(endDate);
                    //classin上课人数 默认:6
                    addLiveLessonRequest.setLiveManNumber("6");
                    log.info("正在添加直播课程[" + cours.getCourseName() + "] 课节[" + detail.getLessonName() + "]信息");
                    AddLiveLessonResponse addLessonResult = addLiveLessonAPI.process(addLiveLessonRequest);
                    String lessonId = addLessonResult.getLessonId();
                    detail.setLessonId(lessonId);
                    liveCourseDetailService.updateXiaoYiCourseDetail(detail,cours.getId(),cours.getOrgCode());
                    log.info("已添加直播课程[" + cours.getCourseName() + "] 课节[" + detail.getLessonName() + "]信息");
                } else {
                    log.error("直播课程[" + cours.getCourseName() + "]所属课节[" + detail.getLessonName() + "]时间段[" + detail.getStartDate() + "-" + detail.getEndDate() + "]信息应小于当前时间");
                }
            } else {
                log.info("已存在直播课程[" + cours.getCourseName() + "] 课节[" + detail.getLessonName() + "]信息");
            }
        }
    }

    @ShowLogger(info = "校翼-获取单个直播课程")
    @ApiOperation(value = "校翼-获取单个直播课程",notes = "校翼-获取单个直播课程")
    @RequestMapping(path = "/xiaoyi/liveCourse/query/{id}",method = RequestMethod.GET)
    public AjaxResult queryLiveCourse(@PathVariable(value = "id")Integer id, PageRequest request){
        LiveCourse course = liveCourseService.queryCourseById(id);
        if(StringUtils.isEmpty(course)){
            return AjaxResult.error("未找到相关直播课程！");
        }
        XiaoYiTeacher xiaoYiTeacher = xiaoYiTeacherService.getById(course.getTeacherId());
        course.setTeacherPhone(xiaoYiTeacher.getMobile());
        String classTag = course.getClassTag();
        if (!StringUtils.isEmpty(classTag)) {
            course.setClassTags(classTag.split("&"));
        }
        if (!StringUtils.isEmpty(course.getClassDifficult())) {
            course.setClassDifficultCount(FindUtil.findCount(course.getClassDifficult(), "★"));
        }
        if (!StringUtils.isEmpty(course)) {
            String classId = course.getClassId();
            if (!StringUtils.isEmpty(classId)) {
                course.setCopyUrl(liveCourseAccessPrefix + classId);
            }
        }
        course.setCourseType(1);
        request.processPageRequest();
        if(!StringUtils.isEmpty(request.getPageIndex())){
            request.setPageIndex(request.getPageIndex());
        }
        if(!StringUtils.isEmpty(request.getPageSize())){
            request.setPageSize(request.getPageSize());
        }
        PageResponse response = liveCourseDetailService.queryCourseDetailById(request, course.getId());
        course.setPageResponse(response);
        course.setLiveCourseDetails((List<LiveCourseDetail>) response.getList());
        return AjaxResult.success(course);
    }

    @ShowLogger(info = "校翼-删除直播课程")
    @ApiOperation(value = "校翼-删除直播课程",notes = "校翼-删除直播课程")
    @RequestMapping(path = "/xiaoyi/liveCourse/remove/{id}",method = RequestMethod.POST)
    public AjaxResult dropLiveCourse(@PathVariable(value = "id")Integer id){
        int sumbitCourse = liveCourseService.dropCourse(id);
        return AjaxResult.success(sumbitCourse>0?true:false);
    }

    @ShowLogger(info = "校翼-删除直播课程所属的课节")
    @ApiOperation(value = "校翼-删除直播课程所属的课节",notes = "校翼-删除直播课程所属的课节")
    @RequestMapping(path = "/xiaoyi/liveCourse/remove/{id}",method = RequestMethod.DELETE)
    public AjaxResult dropLiveCourseDetail(@PathVariable(value = "id")Integer id){
        int sumbitCourse = liveCourseDetailService.deleteXiaoYiCourseDetail(id);
        return AjaxResult.success(sumbitCourse>0?true:false);
    }

    @ShowLogger(info = "检查教师手机号码是否在添翼申学注册成为用户")
    @ApiOperation(value = "检查教师手机号码是否在添翼申学注册成为用户",notes = "检查教师手机号码是否在添翼申学注册成为用户")
    @RequestMapping(path = "/xiaoyi/liveCourse/check/{techerPhone}",method = RequestMethod.GET)
    public AjaxResult checkTecherPhone(@NotBlank @PathVariable String techerPhone) throws Exception {
        CheckTecherPhoneResponse techerPhoneResponse = new CheckTecherPhoneResponse();
        try {
            AddTeacherResponse response = addTeacherAPI.process(new AddTeacherRequest(techerPhone, APIConstant.API_SITENAMEVALUE));
            techerPhoneResponse.success();
        }catch (ServiceException e){
            log.error("检查["+techerPhone+"]结果:"+e.getMess());
            techerPhoneResponse.failed(e.getMess());
        }
        return AjaxResult.success(techerPhoneResponse);
    }

}
