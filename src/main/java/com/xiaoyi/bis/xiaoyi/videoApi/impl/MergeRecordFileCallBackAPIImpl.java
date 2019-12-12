package com.xiaoyi.bis.xiaoyi.videoApi.impl;

import com.xiaoyi.bis.xiaoyi.domain.IntelligentRecord;
import com.xiaoyi.bis.xiaoyi.service.IntelligentRecordService;
import com.xiaoyi.bis.xiaoyi.util.DateUtil;
import com.xiaoyi.bis.xiaoyi.videoApi.*;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 合并保利威视频回调处理
 * 1.更新内部数据信息
 * 2.同步视频至保利威云点播
 * @author CJ
 * @date 2019/11/7
 */
@Component
@Slf4j
public class MergeRecordFileCallBackAPIImpl implements MergeRecordFileCallBackAPI {

    @Autowired
    private IntelligentRecordService intelligentRecordService;
    @Autowired
    private QueryVideoOneAPI queryVideoOneAPI;

    /**
     * 合并智能录播视频成单个视频之后
     * 根据保利威回调修改内部数据
     * 并且同步至保利威云点播
     * @param request
     * @return
     */
    @Override
    public MergeRecordFileCallBackResopnse process(MergeRecordFileCallBackRequest request) throws NoSuchAlgorithmException {
        IntelligentRecord record = intelligentRecordService.queryIntelligentRecordById(request.getId());
        record.setId(request.getId());
        record.setFileId(request.getFileId());
        record.setVideoStatus("2");
        QueryVideoOneRequest videoRequest = new QueryVideoOneRequest();
        videoRequest.setChannelId(record.getChannelId());
        videoRequest.setFileId(record.getFileId());
        QueryVideoOneResponse videoOneResponse = queryVideoOneAPI.process(videoRequest);
        APIRecordFile data = videoOneResponse.getData();
        record.setFileSize(data.getFileSize());
        record.setDuration(DateUtil.secondToTime(Long.valueOf(data.getDuration())));
        if(!StringUtils.isEmpty(data.getM3u8())){
            record.setM3u8(data.getM3u8());
        }
        if(!StringUtils.isEmpty(data.getMp4())){
            record.setMp4(data.getMp4());
        }
        record.setPolyTitle(data.getFileName());
        //更新智能录播数据
        int saveIntelligentRecord = intelligentRecordService.updateIntelligentRecord(record);

        List<String> fileIds=new ArrayList<>();
        fileIds.add(request.getFileId());
        try {
            log.info("正在同步保利威视频文件fileId:"+request.getFileId());
            intelligentRecordService.sendForwardConnection(request.getCataId(),record.getId(),record.getChannelId(),fileIds);
        }catch (Exception e){
            log.error("不能重复提交请求:"+e.getMessage());
        }
        MergeRecordFileCallBackResopnse resopnse = new MergeRecordFileCallBackResopnse();
        resopnse.setFlag(saveIntelligentRecord>0?true:false);
        return resopnse;
    }

}
