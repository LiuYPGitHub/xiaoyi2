package com.xiaoyi.bis.xiaoyi.videoApi.impl;

import com.xiaoyi.bis.xiaoyi.domain.PolyRecordCallback;
import com.xiaoyi.bis.xiaoyi.service.PolyRecordCallbackService;
import com.xiaoyi.bis.xiaoyi.videoApi.StartLiveCallBackAPI;
import com.xiaoyi.bis.xiaoyi.videoApi.StartLiveCallBackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 录制视频回调处理
 * @author CJ
 * @date 2019/10/31
 */
@Component
public class StartLiveCallBackAPIImpl implements StartLiveCallBackAPI {

    @Autowired
    private PolyRecordCallbackService polyRecordCallbackService;

    @Override
    public Integer process(StartLiveCallBackRequest request) {
        //更新ty_poly_record_callback回调表记录
        PolyRecordCallback polyRecordCallback=new PolyRecordCallback();
        polyRecordCallback.setFileUrl(request.getFileUrl());
        polyRecordCallback.setChannelId(request.getChannelId());
        polyRecordCallback.setCreatedAt(new Date());
        polyRecordCallback.setDateTime(new Date());
        polyRecordCallback.setFileId(request.getFileId());
        polyRecordCallback.setOrigin(request.getOrigin());
        polyRecordCallback.setTimestamp(request.getTimestamp());
        polyRecordCallback.setSign(request.getSign());
        polyRecordCallback.setHasRtcRecord(request.getHasRtcRecord());
        int savePolyRecordCallback = polyRecordCallbackService.savePolyRecordCallback(polyRecordCallback);
        return savePolyRecordCallback;
    }
}
