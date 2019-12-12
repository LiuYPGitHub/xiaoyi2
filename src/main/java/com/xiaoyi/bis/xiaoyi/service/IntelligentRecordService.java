package com.xiaoyi.bis.xiaoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.user.controller.common.ResPageInfo;
import com.xiaoyi.bis.xiaoyi.domain.IntelligentRecord;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import com.xiaoyi.bis.xiaoyi.dto.QueryIntelligentRecordCourseRequest;
import com.xiaoyi.bis.xiaoyi.dto.QueryIntelligentRecordVideoRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.FileForwardSaveRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.FileForwardSaveResponse;
import com.xiaoyi.bis.xiaoyi.videoApi.MergeRecordFileRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIVideo;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * 智能录播相关业务逻辑处理
 */
public interface IntelligentRecordService extends IService<IntelligentRecord> {


    /**
     * 添加智能录播信息至添翼申学作为线下课程
     * @param expirationDuration 有效期:默认7天
     * @param courseName 课程名称
     * @param vId 保利威视频编号
     * @param courseNum 课时数
     * @param studentNum 学生人数
     * @return classId:添翼申学课程编号
     */
    String addIntelligentRecord(String expirationDuration,String courseName,String vId,Integer courseNum,Integer studentNum);

    /**
     * 根据Vid获取指定课程mp4视频信息
     * @param vid 保利威视频编号
     * @return mp4地址
     * @throws NoSuchAlgorithmException
     */
    APIVideo getVideoByVid(String vid) throws NoSuchAlgorithmException;

    /**
     * 智能录播完整版模糊查询
     * @param request
     * @return
     */
    List<IntelligentRecord> queryIntelligentRecordCourseAllByLike(QueryIntelligentRecordCourseRequest request);

    /**
     * 智能录播精编版模糊查询
     * @param request
     * @return
     */
    List<IntelligentRecord> queryIntelligentRecordCourseByLike(QueryIntelligentRecordCourseRequest request);


    /**
     * 录播界面根据用户输入获取智能数据列表视频信息
     * @param request
     * @return
     */
    List<IntelligentRecord> queryIntelligentRecordVideo(QueryIntelligentRecordVideoRequest request);

    /**
     * 获取当前用户保利威视频道编号
     * @param userId
     * @return
     */
    String getChannelIdByUserId(String userId);

    /**
     * 保存智能录播
     * @param record
     * @return
     */
    int saveIntelligentRecord(IntelligentRecord record);

    /**
     * 查询单个智能录播数据
     * @param id
     * @return
     */
    IntelligentRecord queryIntelligentRecordById(Integer id);

    /**
     * 修改最新智能录播数据
     * @param record
     * @return
     */
    int updateIntelligentRecord(IntelligentRecord record);

    /**
     * 删除指定智能录播数据
     * @param courseId
     * @return
     */
    boolean deleteIntelligentRecord(String courseId);

    /**
     * 根据开始/结束时间获取保利威视频列表
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return List<String> 保利威视频列表
     * @throws NoSuchAlgorithmException
     */
    List<String> getVideoList(String channelId, Date startDate, Date endDate) throws NoSuchAlgorithmException ;

    /**
     * 根据Id编号列表获取保利威视频列表
     * @param fileIds
     */
    List<APIRecordFile> getVideoList(String channelId, List<String> fileIds) throws NoSuchAlgorithmException;

    /**
     * 发送合并请求至保利威
     * @param id 智能录播编号
     * @param channelId 频道编号
     * @param fileIds 保利威视频编号列表
     */
    void sendMergeConnection(String cataId,Integer id,String channelId,List<String> fileIds);

    /**
     * 发送同步点播请求至保利威
     * @param id 智能录播编号
     * @param channelId 频道编号
     * @param fileIds 保利威视频编号列表
     */
    FileForwardSaveResponse sendForwardConnection(String cataId, Integer id, String channelId, List<String> fileIds);

    /**
     * 生成同步点播参数对象
     * @param id
     * @param channelId
     * @param fileIds
     * @return
     */
    FileForwardSaveRequest getForwardRequest(String cataId,Integer id,String channelId, List<String> fileIds);

    /**
     * 生成合并参数对象
     * @param type y:合并成MP4 n:合并成M3u8
     * @param channelId 频道编号
     * @param fileIds 合并前视频文件编号列表
     * @return
     */
    MergeRecordFileRequest getMergeRecordRequest(String cataId,Integer id, String type, String channelId, List<String> fileIds);

    /**
     * 查询运营人员手机号码
     * @param userId
     * @return
     */
    String getCreateMobile(String userId);

    /**
     * 根据课程编号判断是否已经在添翼申学上架
     * @param classId 课程编号
     * @return true:上架 false:下架
     */
    boolean getCourseStatus(String classId);

}
