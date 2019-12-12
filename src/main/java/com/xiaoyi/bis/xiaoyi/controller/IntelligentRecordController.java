package com.xiaoyi.bis.xiaoyi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.common.annotation.ShowLogger;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.user.controller.common.ResPageInfo;
import com.xiaoyi.bis.xiaoyi.bean.ResRecorded;
import com.xiaoyi.bis.xiaoyi.bean.StudentBean;
import com.xiaoyi.bis.xiaoyi.domain.IntelligentRecord;
import com.xiaoyi.bis.xiaoyi.domain.Orgs;
import com.xiaoyi.bis.xiaoyi.domain.RecordCourse;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetail;
import com.xiaoyi.bis.xiaoyi.dto.*;
import com.xiaoyi.bis.xiaoyi.service.*;
import com.xiaoyi.bis.xiaoyi.util.ExcelUtil;
import com.xiaoyi.bis.xiaoyi.videoApi.FileForwardSaveResponse;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoOneAPI;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoOneRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoOneResponse;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 智能录播
 *
 * @author CJ
 * @date 2019/11/7
 */
@Api(tags = {"intelligentRecord"}, value = "校翼")
@Slf4j
@RestController
public class IntelligentRecordController {

    @Autowired
    private IntelligentRecordService intelligentRecordService;
    @Autowired
    private RecordDetailService recordDetailService;
    @Autowired
    private RecordCourseService recordCourseService;
    @Autowired
    private ShareStudentService shareStudentService;
    @Autowired
    private LiveCourseService liveCourseService;
    @Autowired
    private QueryVideoOneAPI queryVideoOneAPI;
    @Autowired
    private OrgsService orgsService;
    @Autowired
    private ExcelUtil excelUtil;
    private @Autowired
    Mapper mapper;

    @Value(value = "${xiaoYiService.allowTaste}")
    private Integer allowTaste;

    @ApiOperation(value = "获取智能录播列表-完整版", notes = "获取智能录播列表-完整版")
    @ShowLogger(info = "获取智能录播列表-完整版")
    @RequestMapping(path = "/xiaoyi/intelligentRecordCourseAll/list", method = RequestMethod.GET)
    public AjaxResult intelligentRecordAllCourseList(QueryIntelligentRecordCourseRequest request) {
        request.processPageRequest();
        log.info("获取智能录播列表入参:{}", request);
        List<IntelligentRecord> intelligentRecords = intelligentRecordService.queryIntelligentRecordCourseAllByLike(request);
        PageInfo<IntelligentRecord> pageInfo = new PageInfo<>(intelligentRecords);
        final List<ResRecorded> collect = intelligentRecords.stream().map(e -> {
            final ResRecorded resRecorded = mapper.map(e, ResRecorded.class);
            return resRecorded;
        }).collect(Collectors.toList());
        final ResPageInfo<ResRecorded> resPageInfo = new ResPageInfo<>();
        mapper.map(pageInfo, resPageInfo);
        resPageInfo.setContent(collect);
        return AjaxResult.success(resPageInfo);
    }

    @ApiOperation(value = "获取智能录播列表-精编版", notes = "获取智能录播列表-精编版")
    @ShowLogger(info = "获取智能录播列表-精编版")
    @RequestMapping(path = "/xiaoyi/intelligentRecordCourse/list", method = RequestMethod.GET)
    public AjaxResult intelligentRecordCourseList(QueryIntelligentRecordCourseRequest request) {
        request.processPageRequest();
        log.info("获取智能录播列表入参:{}", request);
        List<IntelligentRecord> intelligentRecords = intelligentRecordService.queryIntelligentRecordCourseByLike(request);
        PageInfo<IntelligentRecord> pageInfo = new PageInfo<>(intelligentRecords);

        intelligentRecords.stream().forEach(e ->{
            List<RecordDetail> details = recordDetailService.queryRecordDetailByCourseId(e.getId());
            e.setDetails(details);
        });

        ResPageInfo<IntelligentRecord> resPageInfo = new ResPageInfo<>();

        mapper.map(pageInfo, resPageInfo);

        resPageInfo.setContent(intelligentRecords);
        return AjaxResult.success(resPageInfo);
    }

    @ApiOperation(value = "获取指定智能录播课节", notes = "获取指定智能录播课节")
    @ShowLogger(info = "获取指定智能录播课节")
    @RequestMapping(path = "/xiaoyi/intelligentRecordCourseDetail/list", method = RequestMethod.GET)
    public AjaxResult intelligentRecordDetailCourseList(QueryLiveCourseDetailRequest request) {
        request.processPageRequest();
        IntelligentRecord intelligentRecord = intelligentRecordService.getById(request.getCourseId());
        PageResponse response = recordDetailService.queryRecordDetailByCourseId(request.getPageIndex(), request.getPageSize(), request.getCourseId());
        intelligentRecord.setResponse(response);
        intelligentRecord.setDetails((List<RecordDetail>) response.getList());
        return AjaxResult.success(intelligentRecord);
    }

    @ApiOperation(value = "删除指定智能录播课程", notes = "删除指定智能录播课程")
    @ShowLogger(info = "删除指定智能录播课程")
    @RequestMapping(path = "/xiaoyi/intelligentRecordCourse/remove/{id}", method = RequestMethod.POST)
    public AjaxResult intelligentRecordAllCourseRemove(@PathVariable(value = "id") String id) {
        return AjaxResult.success(intelligentRecordService.deleteIntelligentRecord(id));
    }

    /**
     * 添加智能录播-完整版
     *
     * @param file    导入学生excel文件
     * @param request
     * @return
     * @throws Exception
     */
    @ShowLogger(info = "添加智能录播-完整版")
    @ApiOperation(value = "添加智能录播-完整版", notes = "添加智能录播-完整版")
    @RequestMapping(path = "/xiaoyi/intelligentRecordAllCourse", method = RequestMethod.POST)
    public AjaxResult addIntelligentRecordAllCourse(@RequestParam(value = "file", required = false) MultipartFile file, @Valid AddIntelligentRecordAllRequest request) throws Exception {
        log.info("添加智能录播:{}", request);
        String channelId = intelligentRecordService.getChannelIdByUserId(request.getUserId());
        if (!StringUtils.isEmpty(channelId)) {
            log.info("已成功获取用户所属频道号:" + channelId);
            String createMobile = intelligentRecordService.getCreateMobile(request.getUserId());
            String orgCode = liveCourseService.getOrgIdByUserId(request.getUserId());
            Orgs orgs = orgsService.getOrgsByOrgId(orgCode);
            String cataId = "";
            if (!StringUtils.isEmpty(orgs)) {
                cataId = orgs.getCataId();
            }
            //新增智能录播数据信息
            processVideo(cataId, null, orgCode, createMobile, channelId, request, file);
            return AjaxResult.success(true);
        } else {
            return AjaxResult.error();
        }
    }

    @ShowLogger(info = "手动刷新视频-完整版")
    @ApiOperation(value = "手动刷新视频-完整版", notes = "手动刷新视频-完整版")
    @RequestMapping(path = "/xiaoyi/intelligentRecordAllCourse/refresh", method = RequestMethod.GET)
    public AjaxResult refreshIntelligentRecordAllCourse(@RequestParam(value = "id") Integer id,
                                                        @RequestParam(value = "userId") String userId) throws Exception {
        IntelligentRecord record = intelligentRecordService.queryIntelligentRecordById(id);
        if (!StringUtils.isEmpty(record)) {
            //处理完整版刷新请求
            if (record.getIsRevised() == 1) {
                RecordCourse recordCourse = recordCourseService.selectById(record.getId());
                if (!StringUtils.isEmpty(record.getVid()))  {
                    //获取添翼申学最新课程状态
                    boolean courseStatus = intelligentRecordService.getCourseStatus(recordCourse.getClassId());
                    if (courseStatus) {
                        recordCourse.setStatus(new Integer(4));
                    }
                    int updateRecordCourse = recordCourseService.updateRecordCourse(recordCourse);
                    return AjaxResult.success(updateRecordCourse > 0 ? true : false);
                } else {
                    /*if (StringUtils.isEmpty(record.getMp4())) { //delete
                        QueryVideoOneRequest oneRequest = new QueryVideoOneRequest();
                        oneRequest.setChannelId(record.getChannelId());
                        oneRequest.setFileId(record.getFileId());
                        log.info("查询保利威单个视频文件入参:" + oneRequest);
                        QueryVideoOneResponse oneResponse = queryVideoOneAPI.process(oneRequest);
                        APIRecordFile data = oneResponse.getData();
                        if (!StringUtils.isEmpty(data.getM3u8())) {
                            record.setM3u8(data.getM3u8());
                        }
                        if (!StringUtils.isEmpty(data.getMp4())) {
                            record.setMp4(data.getMp4());
                        }
                        record.setPolyTitle(data.getFileName());
                        intelligentRecordService.saveIntelligentRecord(record);
                    }*/

                    // 刷新处理
                    List<String> fileIds = intelligentRecordService.getVideoList(record.getChannelId(), record.getStartDate(), record.getEndDate());
                    String cataId = "";
                    log.info("刷新处理入参:{},{},{},{}", record.getChannelId(), record.getStartDate(), record.getEndDate(), fileIds.size());
                    if (fileIds.size() == 1) {
                        QueryVideoOneRequest oneRequest = new QueryVideoOneRequest();
                        oneRequest.setChannelId(record.getChannelId());
                        oneRequest.setFileId(fileIds.get(0));
                        log.info("查询保利威单个视频文件入参:" + oneRequest);
                        QueryVideoOneResponse oneResponse = queryVideoOneAPI.process(oneRequest);
                        APIRecordFile data = oneResponse.getData();
                        if (!StringUtils.isEmpty(data.getM3u8())) {
                            record.setM3u8(data.getM3u8());
                        }
                        if (!StringUtils.isEmpty(data.getMp4())) {
                            record.setMp4(data.getMp4());
                        }
                        record.setPolyTitle(data.getFileName());
                        record.setVideoStatus("2");
                        intelligentRecordService.updateIntelligentRecord(record);

                        String orgCode = liveCourseService.getOrgIdByUserId(userId);
                        Orgs orgs = orgsService.getOrgsByOrgId(orgCode);

                        if (!StringUtils.isEmpty(orgs)) {
                            cataId = orgs.getCataId();
                        }
                        log.info("同步的智能录播编号:" + record.getId());
                        log.info("正在同步保利威视频文件fileIds:" + fileIds);
                        final FileForwardSaveResponse fileForwardSaveResponse = intelligentRecordService.sendForwardConnection(cataId, record.getId(), record.getChannelId(), fileIds);
                        log.info("刷新成功");
                        return AjaxResult.success("刷新成功");
                    } else if (fileIds.size() > 1){
                        intelligentRecordService.sendMergeConnection(cataId, record.getId(), record.getChannelId(), fileIds);
                        log.info("刷新成功");
                        return AjaxResult.error("正在处理中,请稍后!");
                    }else {
                        return AjaxResult.error("正在处理中,请稍后!");
                    }
                    //

                  /*  AddIntelligentRecordAllRequest request = new AddIntelligentRecordAllRequest(); //delete
                    request.setCourseName(record.getCourseName());
                    request.setCourseCount(record.getCourseNum());
                    request.setTeacherName(record.getTeacherName());
                    request.setStudentCount(record.getStudentNum());
                    request.setClassName(record.getClassInfo());
                    request.setStartDate(record.getStartDate());
                    request.setEndDate(record.getEndDate());
                    try {
                        String orgCode = liveCourseService.getOrgIdByUserId(userId);
                        Orgs orgs = orgsService.getOrgsByOrgId(orgCode);
                        String cataId = "";
                        if(!StringUtils.isEmpty(orgs)){
                            cataId = orgs.getCataId();
                        }
                        //修改内部存在的智能录播数据信息
                        processVideo(cataId,record.getId(), orgCode, record.getCreateMobile(), record.getChannelId(), request, null);
                    } catch (ServiceException e) {
                        log.error("请勿重新提交请求，正在处理中！" + e);
                        return AjaxResult.success("请勿重新提交请求，正在处理中！");
                    }*/

                }
            } else {
                log.error("目前该功能只支持完整版");
                return AjaxResult.error("目前该功能只支持完整版");
            }
        } else {
            log.error("该课程不存在");
            return AjaxResult.error("该课程不存在");
        }
    }

    /**
     * 添加智能录播-精编版
     *
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @ShowLogger(info = "添加智能录播-精编版")
    @ApiOperation(value = "添加智能录播-精编版", notes = "添加智能录播-精编版")
    @RequestMapping(path = "/xiaoyi/intelligentRecordCourse", method = RequestMethod.POST)
    public AjaxResult addIntelligentRecordCourse(
            @RequestParam(value = "file") MultipartFile file,
            AddIntelligentRecordRequest request
    ) throws IOException {
        String channelId = intelligentRecordService.getChannelIdByUserId(request.getUserId());
        if (!StringUtils.isEmpty(channelId)) {
            log.info("lessons:" + request.getLessons());
            log.info("已成功获取用户所属频道号:" + channelId);
            String createMobile = intelligentRecordService.getCreateMobile(request.getUserId());
            log.info("运营人员手机号码:" + createMobile);
            String orgCode = liveCourseService.getOrgIdByUserId(request.getUserId());
            log.info("根据用户编号获取机构代码:" + orgCode);

            //添加精编版智能录播课程
            IntelligentRecord intRecord = new IntelligentRecord();
            intRecord.setChannelId(channelId);
            intRecord.setCreatedAt(new Date());
            intRecord.setOrgCode(orgCode);
            intRecord.setStatus(1);
            intRecord.setVideoStatus("1");
            intRecord.setCourseName(request.getCourseName());
            intRecord.setTeacherName(request.getTeacherName());
            intRecord.setCourseNum(request.getCourseCount());
            intRecord.setClassInfo(request.getClassName());
            intRecord.setIsDel(1);
            intRecord.setIsShow(1);
            intRecord.setRegDate(new Date());
            intRecord.setUpdatedAt(new Date());
            intRecord.setCreateMobile(createMobile);
            intRecord.setIsRevised(2);
            intRecord.setCreatedAt(new Date());
            intRecord.setCreator(request.getUserId());
            intRecord.setStudentNum(Integer.valueOf(request.getStudentCount()));
            intRecord.setCourseMins(request.getCourseMins());
            intelligentRecordService.saveIntelligentRecord(intRecord);

            //添加分享学生
            if (file != null) {
                List<StudentBean> students = excelUtil.excelImportStudents(file.getOriginalFilename(), file.getInputStream());
                shareStudentService.saveShareStudent(intRecord.getId(), students);
            }
            //String str="[{\"lesson_order\":\"课节1\",\"lessonName\":\"1111\",\"mp4\":\"http://mpv.videocc.net/aef3afd3d0/b/aef3afd3d058bd818f66e1c84e7e704b_1.mp4\",\"vid\":\"aef3afd3d058bd818f66e1c84e7e704b_a\"},{\"lesson_order\":\"课节2\",\"lessonName\":\"2222\",\"vid\":\"aef3afd3d0e6a9c2d39c1dda36a99cfc_a\",\"mp4\":\"http://mpv.videocc.net/aef3afd3d0/c/aef3afd3d0e6a9c2d39c1dda36a99cfc_1.mp4\"}]";
            JSONArray jsonArray = JSONObject.parseArray(request.getLessons());

            RecordDetail recordDetail;
            for (Object o : jsonArray) {
                JSONObject jsonObject = JSONObject.parseObject(o.toString());
                if (!StringUtils.isEmpty(jsonObject.getString("vid"))) {
                    recordDetail = new RecordDetail();
                    recordDetail.setOrgCode(orgCode);
                    recordDetail.setCourseId(intRecord.getId());
                    recordDetail.setLessonName(jsonObject.getString("lessonName"));
//                recordDetail.setLessonDesc(less.getLessonDesc());
                    recordDetail.setMp4(jsonObject.getString("mp4"));
                    recordDetail.setVid(jsonObject.getString("vid"));
                    recordDetail.setCreatedAt(new Date());
                    recordDetail.setUpdatedAt(new Date());
                    recordDetail.setIsRevised(2);
                    recordDetail.setAllowTaste(allowTaste);
                    recordDetail.setRecordHour(request.getCourseMins() / 60);
                    recordDetail.setStatus(1);
                    recordDetailService.save(recordDetail);
                } else {
                    recordDetail = new RecordDetail();
                    recordDetail.setOrgCode(orgCode);
                    recordDetail.setCourseId(intRecord.getId());
                    recordDetail.setLessonName(jsonObject.getString("lessonName"));
                    recordDetail.setMp4(jsonObject.getString("mp4"));
                    recordDetail.setVid(jsonObject.getString("vid"));
                    recordDetail.setCreatedAt(new Date());
                    recordDetail.setUpdatedAt(new Date());
                    recordDetail.setIsRevised(2);
                    recordDetail.setAllowTaste(allowTaste);
                    recordDetail.setRecordHour(request.getCourseMins() / 60);
                    recordDetail.setStatus(1);
                    recordDetailService.save(recordDetail);
                }
            }
            return AjaxResult.success(true);
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 合并及转存保利威返回的录播视频列表(完整版)
     *
     * @param cataId    视频分类编号
     * @param courseId  录播课程编号
     * @param userId    当前登录用户编号
     * @param channelId 频道编号
     * @param request   用户输入参数
     * @param file      导入的学生excel文件
     * @throws Exception
     */
    public void processVideo(String cataId, Integer courseId, String orgCode, String userId, String channelId, AddIntelligentRecordAllRequest request, MultipartFile file) throws Exception {
        IntelligentRecord record = new IntelligentRecord();
        record.setVideoStatus("1");
        record.setOrgCode(orgCode);
        record.setChannelId(channelId);
        record.setCataId(cataId);
        record.setCourseName(request.getCourseName());
        record.setTeacherName(request.getTeacherName());
        record.setStudentNum(request.getStudentCount());
        record.setCourseNum(request.getCourseCount());
        record.setClassInfo(request.getClassName());
        record.setCourseMins(request.getCourseCount());
        record.setRegDate(new Date());
        record.setCreatedAt(new Date());
        record.setUpdatedAt(new Date());
        record.setStartDate(request.getStartDate());
        record.setEndDate(request.getEndDate());
        record.setCreateMobile(userId);
        //完整版
        record.setCreator(request.getUserId());
        record.setIsRevised(new Integer(1));
        record.setStatus(new Integer(1));
        if (courseId == null) {
            intelligentRecordService.saveIntelligentRecord(record);
        } else {
            record.setId(courseId);
            intelligentRecordService.updateIntelligentRecord(record);
        }
        //获取视频列表
        List<String> fileIds = intelligentRecordService.getVideoList(channelId, request.getStartDate(), request.getEndDate());
        //if (fileIds.size() > 0) {
        if (file != null) {
            log.info("开始添加[课程" + record.getId() + "]分享课节学生");
            List<StudentBean> students = excelUtil.excelImportStudents(file.getOriginalFilename(), file.getInputStream());
            shareStudentService.saveShareStudent(record.getId(), students);
        }
        // }
        if (fileIds.size() > 1) {
            log.info("关联的智能录播编号:" + record.getId());
            log.info("正在合并保利威视频文件fileIds:" + fileIds);
            intelligentRecordService.sendMergeConnection(cataId, record.getId(), channelId, fileIds);
        } else if (fileIds.size() == 1) {
            log.info("同步的智能录播编号:" + record.getId());
            log.info("正在同步保利威视频文件fileIds:" + fileIds);
            intelligentRecordService.sendForwardConnection(cataId, record.getId(), channelId, fileIds);
        } else {
            String pattern = "yyyy-MM-dd HH:mm:ss";
            String startDate = DateFormatUtils.format(request.getStartDate(), pattern);
            String endDate = DateFormatUtils.format(request.getEndDate(), pattern);
            log.error("未获取到频道" + channelId + "[" + startDate + " - " + endDate + "]的相应视频");
        }

    }

    /**
     * 转存保利威返回的录播视频列表(精编版)
     *
     * @param userId
     * @param channelId
     * @param request
     * @param file
     * @param fileIds
     * @throws Exception
     */
    public void processVideo(String cataId, String userId, String orgCode, String channelId, AddIntelligentRecordRequest request, MultipartFile file, List<String> fileIds) throws Exception {
        IntelligentRecord record = new IntelligentRecord();
        record.setOrgCode(orgCode);
        record.setCataId(cataId);
        record.setChannelId(channelId);
        record.setCourseName(request.getCourseName());
        record.setTeacherName(request.getTeacherName());
        record.setStudentNum(request.getStudentCount());
        record.setCourseNum(request.getCourseCount());
        record.setClassInfo(request.getClassName());
        record.setCourseMins(request.getCourseCount());
        record.setRegDate(new Date());
        record.setCreatedAt(new Date());
        record.setUpdatedAt(new Date());
        record.setCreateMobile(userId);
        //精编版
        record.setIsRevised(new Integer(2));
        record.setStatus(new Integer(1));
        intelligentRecordService.saveIntelligentRecord(record);
        if (fileIds.size() > 0) {
            Integer recordId = record.getId();
            log.info("关联的智能录播编号:" + recordId);
            log.info("正在同步保利威视频文件fileIds:" + fileIds);

            //同步多个视频至保利威-云点播
            intelligentRecordService.sendForwardConnection(cataId, recordId, channelId, fileIds);

            if (file != null) {
                log.info("开始添加[课程" + record.getId() + "]分享课节学生");
                List<StudentBean> students = excelUtil.excelImportStudents(file.getOriginalFilename(), file.getInputStream());
                shareStudentService.saveShareStudent(recordId, students);
            }

        } else {
            log.info("未获取到频道" + channelId + "的相应视频");
        }
    }

}
