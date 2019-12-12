package com.xiaoyi.bis.xiaoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.common.utils.DateUtil;
import com.xiaoyi.bis.user.controller.common.ResPageInfo;
import com.xiaoyi.bis.xiaoyi.dao.IntelligentRecordMapper;
import com.xiaoyi.bis.xiaoyi.dao.LiveCourseMapper;
import com.xiaoyi.bis.xiaoyi.domain.IntelligentRecord;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetail;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import com.xiaoyi.bis.xiaoyi.dto.QueryIntelligentRecordCourseRequest;
import com.xiaoyi.bis.xiaoyi.dto.QueryIntelligentRecordVideoRequest;
import com.xiaoyi.bis.xiaoyi.service.IntelligentRecordService;
import com.xiaoyi.bis.xiaoyi.service.LiveCourseService;
import com.xiaoyi.bis.xiaoyi.service.RecordDetailService;
import com.xiaoyi.bis.xiaoyi.videoApi.*;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIVideo;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.*;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.bean.APICourseDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author CJ
 * @date 2019/11/5
 */
@Slf4j
@Transactional
@Service
public class IntelligentRecordServiceImpl extends ServiceImpl<IntelligentRecordMapper, IntelligentRecord> implements IntelligentRecordService {

    @Autowired
    private QueryLiveCourseDetailAPI queryLiveCourseDetailAPI;
    @Autowired
    private IntelligentRecordMapper intelligentRecordMapper;
    @Autowired
    private LiveCourseMapper liveCourseMapper;
    @Autowired
    private RecordDetailService recordDetailService;
    @Autowired
    private QueryVideoByVidAPI queryVideoByVidAPI;
    @Autowired
    private MergeRecordFileAPI mergeRecordFileAPI;
    @Autowired
    private AddRecordCourseAPI addRecordCourseAPI;
    @Autowired
    private QueryVideoListAPI queryVideoListAPI;
    @Autowired
    private QueryVideoOneAPI queryVideoOneAPI;
    @Autowired
    private FileForwardSaveAPI fileForwardSaveAPI;
    @Autowired
    private LiveCourseService liveCourseService;

    //回调访问前缀
    @Value(value = "${xiaoYiService.callBackPrefix}")
    private String callBackPrefix;
    //同步至点播Url
    @Value(value = "${xiaoYiService.forwardRecordCallBack}")
    private String forwardRecordCallBack;
    //合并视频Url
    @Value(value = "${xiaoYiService.mergeRecordCallBack}")
    private String mergeRecordCallBack;
    @Value(value = "${tysxService.siteName}")
    private String siteName;
    @Value(value = "${tysxService.recordCourseImage}")
    private String recordCourseImage;

    /**
     * 添加智能录播信息至添翼申学作为线下课程
     *
     * @param expirationDuration 有效期
     * @param courseName         课程名称
     * @param vId                保利威视频编号
     * @param courseNum          课时数
     * @param studentNum         学生人数
     * @return classId:添翼申学课程编号
     */
    @Override
    public String addIntelligentRecord(String expirationDuration, String courseName, String vId, Integer courseNum, Integer studentNum) {
        //添加添翼申学录播线下课
        AddRecordCourseRequest recordCourseRequest = new AddRecordCourseRequest();
        recordCourseRequest.setName(courseName);
        //暂时用默认的
        recordCourseRequest.setCoverImgLink(recordCourseImage);
        recordCourseRequest.setCoverVid(vId);
        Calendar instance = Calendar.getInstance();
        String enrollStartDate = DateFormatUtils.format(instance.getTime(), "yyyyMMddHHmmss");
        instance.add(Calendar.YEAR, 2);
        String enrollEndDate = DateFormatUtils.format(instance.getTime(), "yyyyMMddHHmmss");
        recordCourseRequest.setEnrollStartDate(enrollStartDate);
        recordCourseRequest.setEnrollEndDate(enrollEndDate);
        //recordCourseRequest.setExpirationDuration(expirationDuration);
        recordCourseRequest.setClassHour(String.valueOf(courseNum));
        recordCourseRequest.setTotalTime(String.valueOf(studentNum));
        recordCourseRequest.setRecordType("2");
        recordCourseRequest.setClassType("学科教育");
        recordCourseRequest.setPrimeCost("0");
        recordCourseRequest.setCost("0");
        recordCourseRequest.setSiteName(siteName);
        recordCourseRequest.setMaxNum("100");
        AddRecordCourseResponse response = addRecordCourseAPI.process(recordCourseRequest);
        return response.getClassId();
    }

    /**
     * 根据Vid获取指定课程mp4视频信息
     *
     * @param vid 保利威视频编号
     * @return mp4地址
     * @throws NoSuchAlgorithmException
     */
    public APIVideo getVideoByVid(String vid) throws NoSuchAlgorithmException {
        QueryVideoByVidRequest videoByVidRequest = new QueryVideoByVidRequest();
        videoByVidRequest.setVid(vid);
        QueryVideoByVidResponse videoByVidResponse = queryVideoByVidAPI.process(videoByVidRequest);
        return videoByVidResponse.getData();
    }

    @Override
    public List<IntelligentRecord> queryIntelligentRecordCourseAllByLike(QueryIntelligentRecordCourseRequest request) {
        final String role = liveCourseMapper.isAdmin(request.getUserId());
        final LambdaQueryWrapper<IntelligentRecord> wrapper = new LambdaQueryWrapper<>();
        if (request.getCourseType() != null) {
            wrapper.eq(IntelligentRecord::getIsRevised, request.getCourseType());
        }
        if (StringUtils.isNotBlank(request.getVideoStatus())) {
            wrapper.eq(IntelligentRecord::getVideoStatus, request.getVideoStatus());
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(request.getKey())) {
            wrapper.like(IntelligentRecord::getCourseName, request.getKey());
        }
        if (org.apache.commons.lang3.StringUtils.equals(role, "1")) {
            wrapper.eq(IntelligentRecord::getOrgCode, request.getOrgCode());
        } else {
            wrapper.eq(IntelligentRecord::getCreator, request.getUserId());
        }
        if (request.getOrderType() == null) {
            wrapper.orderByDesc(IntelligentRecord::getRegDate);
        } else if (request.getOrderType() == 1 || request.getOrderType() == 3) {
            wrapper.orderByDesc(IntelligentRecord::getRegDate);
        } else if (request.getOrderType() == 2) {
            wrapper.orderByAsc(IntelligentRecord::getRegDate);
        }
        wrapper.eq(IntelligentRecord::getIsDel, "1");
        wrapper.eq(IntelligentRecord::getIsShow, "1");
        if (request.getPageIndex() != null && request.getPageSize() != null) {
            PageHelper.startPage(request.getPageIndex(), request.getPageSize());
        }
        final List<IntelligentRecord> intelligentRecords = intelligentRecordMapper.selectList(wrapper);
        return intelligentRecords;
    }

    @Override
    public List<IntelligentRecord> queryIntelligentRecordCourseByLike(QueryIntelligentRecordCourseRequest request) {

        QueryWrapper<IntelligentRecord> queryWrapper = new QueryWrapper<>();
        String role = liveCourseMapper.isAdmin(request.getUserId());

        if (org.apache.commons.lang3.StringUtils.equals(role, "1")) {
            queryWrapper.eq("org_code", request.getOrgCode());
        } else {
            queryWrapper.eq("org_code", request.getUserId());
        }
        if (request.getCourseType() == 1) {
            queryWrapper.eq("is_revised", "1");
        } else if (request.getCourseType() == 2) {
            queryWrapper.eq("is_revised", "2");
        }
        if (!StringUtils.isEmpty(request.getKey())) {
            queryWrapper.like("`course_name`", request.getKey());
            queryWrapper.or().like("`teacher_name`", request.getKey());
            if (!StringUtils.isEmpty(request.getOrgCode())) {
                queryWrapper.eq("org_code", request.getOrgCode());
            }
            if (request.getCourseType() == 1) {
                queryWrapper.eq("is_revised", "1");
            } else if (request.getCourseType() == 2) {
                queryWrapper.eq("is_revised", "2");
            }
        }
        if (request.getOrderType() == null) {
            queryWrapper.orderByDesc("reg_date");
        } else if (request.getOrderType() == 1 || request.getOrderType() == 3) {
            queryWrapper.orderByDesc("reg_date");
        } else {
            queryWrapper.orderByAsc("reg_date");
        }
        queryWrapper.eq("is_del", "1");
        queryWrapper.eq("is_show", "1");

        if (request.getPageIndex() != null && request.getPageSize() != null) {
            PageHelper.startPage(request.getPageIndex(), request.getPageSize());
        }
        List<IntelligentRecord> intelligentRecords = intelligentRecordMapper.selectList(queryWrapper);
        return intelligentRecords;
    }

    @Override
    public List<IntelligentRecord> queryIntelligentRecordVideo(QueryIntelligentRecordVideoRequest request) {
        LambdaQueryWrapper<IntelligentRecord> queryWrapper = new LambdaQueryWrapper<>();
        String role = liveCourseMapper.isAdmin(request.getUserId());
        String orgId = liveCourseMapper.getOrgIdByUserId(request.getUserId());
        if ("1".equals(role)) {
            queryWrapper.eq(IntelligentRecord::getOrgCode, orgId);
        } else {
            queryWrapper.eq(IntelligentRecord::getCreator, request.getUserId());
        }
        if (!StringUtils.isEmpty(request.getCourseName())) {
            queryWrapper.like(IntelligentRecord::getCourseName, request.getCourseName());
        }
        if (!StringUtils.isEmpty(request.getTeacherName())) {
            queryWrapper.like(IntelligentRecord::getTeacherName, request.getTeacherName());
        }
        if (request.getVideoType() != null) {
            queryWrapper.eq(IntelligentRecord::getIsRevised, request.getVideoType());
        }
        if ("1".equals(request.getVideoType())) {
            queryWrapper.isNotNull(IntelligentRecord::getVid);
        }
        List<IntelligentRecord> records = intelligentRecordMapper.selectList(queryWrapper);
        for (IntelligentRecord record : records) {
            List<RecordDetail> details = recordDetailService.queryRecordDetailVidNotNull(record.getId());
            for (RecordDetail detail : details) {
                detail.setIsRevised(record.getIsRevised());
            }
            record.setDetails(details);
        }
        return records;
    }

    /**
     * 获取当前用户保利威视频道编号
     *
     * @param userId
     * @return
     */
    @Override
    public String getChannelIdByUserId(String userId) {
        return intelligentRecordMapper.selectChannelIdByUserId(userId);
    }

    @Override
    public int saveIntelligentRecord(IntelligentRecord record) {
        return intelligentRecordMapper.insert(record);
    }

    @Override
    public IntelligentRecord queryIntelligentRecordById(Integer id) {
        log.info("获取智能录播数据入参:" + id);
        return intelligentRecordMapper.selectById(id);
    }

    @Override
    public int updateIntelligentRecord(IntelligentRecord record) {
        return intelligentRecordMapper.updateById(record);
    }

    @Override
    public boolean deleteIntelligentRecord(String courseId) {
        IntelligentRecord record = intelligentRecordMapper.selectById(courseId);
        record.setIsDel(new Integer(2));
        return intelligentRecordMapper.updateById(record) > 0 ? true : false;
    }

    /**
     * 根据开始/结束时间获取保利威视频列表
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<String> 保利威视频列表
     * @throws NoSuchAlgorithmException
     */
    public List<String> getVideoList(String channelId, Date startDate, Date endDate) throws NoSuchAlgorithmException {

        //30分钟
        long time = 30 * 60 * 1000;
        final Date newStartDate = DateUtil.getNewDate(startDate, time);
        final Date newEndDate = DateUtil.getNewDate(endDate, time);
        log.info("正在获取保利威视频列表...");
        QueryVideoListRequest videoRequest = new QueryVideoListRequest();
        videoRequest.setStartDate(newStartDate);
        videoRequest.setEndDate(newEndDate);
        videoRequest.setChannelId(channelId);
        log.info("获取保利威视频列表入参:" + videoRequest);
        QueryVideoListResponse videoList = queryVideoListAPI.process(videoRequest);
        List<String> fileIds = new ArrayList<>();
        List<APIRecordFile> datas = videoList.getData();
        for (APIRecordFile data : datas) {
            if (!StringUtils.isEmpty(data.getFileId())) {
                fileIds.add(data.getFileId());
            }
        }
        log.info("保利威视频文件列表:" + datas);
        return fileIds;
    }

    /**
     * 根据Id编号列表获取保利威视频列表
     *
     * @param fileIds
     */
    @Override
    public List<APIRecordFile> getVideoList(String channelId, List<String> fileIds) throws NoSuchAlgorithmException {
        List<APIRecordFile> files = new ArrayList<>();
        QueryVideoOneRequest request;
        QueryVideoOneResponse response;
        for (String fileId : fileIds) {
            request = new QueryVideoOneRequest();
            request.setChannelId(channelId);
            request.setFileId(fileId);
            response = queryVideoOneAPI.process(request);
            if (response != null) {
                files.add(response.getData());
            }
        }
        return files;
    }

    /**
     * 发送合并请求至保利威
     *
     * @param id        智能录播编号
     * @param channelId 频道编号
     * @param fileIds   保利威视频编号列表
     */
    @Override
    public void sendMergeConnection(String cataId, Integer id, String channelId, List<String> fileIds) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                log.info("正在合并Mp4文件:" + fileIds);
                log.info("合并成Mp4上送参数:" + getMergeRecordRequest(cataId, id, "y", channelId, fileIds));
                //合并成Mp4
                MergeRecordFileResponse response = mergeRecordFileAPI.process(getMergeRecordRequest(cataId, id, "y", channelId, fileIds));
                Thread.sleep(3 * 1000);
                return response.getData();
            } catch (InterruptedException e) {
                log.error("合并MP4文件异常:" + e.getMessage());
                return "合并MP4文件异常！" + e.getMessage();
            }
        }, executor);

        future1.thenAccept((e) -> {
            log.info("合并MP4结果：" + e);
        });

    }

    @Override
    public FileForwardSaveResponse sendForwardConnection(String cataId, Integer id, String channelId, List<String> fileIds) {
        FileForwardSaveRequest forwardRequest = getForwardRequest(cataId, id, channelId, fileIds);
        log.info("同步点播上送参数:" + forwardRequest);
        FileForwardSaveResponse response = fileForwardSaveAPI.process(forwardRequest);
        log.info("转存录制文件到点播结果:" + response);
        return response;
    }

    @Override
    public FileForwardSaveRequest getForwardRequest(String cataId, Integer id, String channelId, List<String> fileIds) {
        FileForwardSaveRequest forwardRequest = new FileForwardSaveRequest();
        //回调函数带上录播课程编号
        forwardRequest.setCallbackUrl(callBackPrefix + forwardRecordCallBack + "?id=" + id);
        forwardRequest.setChannelId(channelId);
        forwardRequest.setFileIds(fileIds);
        forwardRequest.setCataId(cataId);
        return forwardRequest;
    }

    /**
     * 获取合并参数对象
     *
     * @param type      y:合并成MP4 n:合并成M3u8
     * @param channelId 频道编号
     * @param fileIds   合并前视频文件编号列表
     * @return
     */
    @Override
    public MergeRecordFileRequest getMergeRecordRequest(String cataId, Integer id, String type, String channelId, List<String> fileIds) {
        MergeRecordFileRequest mergeRequest = new MergeRecordFileRequest();
        //回调函数带上录播课程编号
        mergeRequest.setCallbackUrl(callBackPrefix + mergeRecordCallBack + "?id=" + id + "&cataId=" + cataId);
        mergeRequest.setChannelId(channelId);
        mergeRequest.setFileIds(fileIds);
        if (!StringUtils.isEmpty(type)) {
            mergeRequest.setMergeMp4("y");
        }
        return mergeRequest;
    }

    @Override
    public String getCreateMobile(String userId) {
        return intelligentRecordMapper.selectCreateMobileByUserId(userId);
    }

    /**
     * 根据课程编号判断是否已经在添翼申学上架
     *
     * @param classId 课程编号
     * @return true:上架 false:下架
     */
    @Override
    public boolean getCourseStatus(String classId) {
        QueryLiveCourseDetailRequest queryRequest = new QueryLiveCourseDetailRequest();
        queryRequest.setClassId(classId);
        try {
            QueryLiveCourseDetailResponse queryResponse = queryLiveCourseDetailAPI.process(queryRequest);
            APICourseDetail queryResponseCourse = queryResponse.getCourse();
            Integer isSearch = queryResponseCourse.getIsSearch();
            if (isSearch == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("根据课程编号判断是否已经在添翼申学上架接口异常:" + e.getMessage());
            return false;
        }
    }

}
