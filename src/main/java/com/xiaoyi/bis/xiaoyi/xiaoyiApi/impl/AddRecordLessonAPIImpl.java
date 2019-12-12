package com.xiaoyi.bis.xiaoyi.xiaoyiApi.impl;

import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.util.MD5HexUtil;
import com.xiaoyi.bis.xiaoyi.util.MD5Util;
import com.xiaoyi.bis.xiaoyi.util.URLUtil;
import com.xiaoyi.bis.xiaoyi.util.XiaoYiAPIJsonUtil;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddRecordLessonAPI;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddRecordLessonRequest;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddRecordLessonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author CJ
 * @date 2019/10/31
 */
@Slf4j
@Component
public class AddRecordLessonAPIImpl implements AddRecordLessonAPI {

    //录播接口访问前缀
    @Value(value = "${tysxService.interfaceUrlPrefix}")
    private String interfaceUrlPrefix;

    @Override
    public AddRecordLessonResponse process(AddRecordLessonRequest request) {
        String functionCode="addRecordLesson";
        String classId=request.getClassId();
        String title=request.getTitle();
        String code=UUID.randomUUID().toString();
        String coverImgLink=request.getCoverImgLink();
        String recordHour=request.getRecordHour();
        String allowTaste=request.getAllowTaste();
        String videoDuration=request.getVideoDuration();
        String platform="liveCourseConnect";
        String timestamp=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        if(!StringUtils.isEmpty(request.getTeacherId())){
            map.put("teacherId",request.getTeacherId());
        }
        map.put("classId",classId);
        map.put("title",title);
        map.put("code",code);
        map.put("coverImgLink",coverImgLink);
        if(!StringUtils.isEmpty(request.getVid())){
            map.put("vid",request.getVid());
        }
        map.put("recordHour",recordHour);
        map.put("allowTaste",allowTaste);
        map.put("videoDuration",videoDuration);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        String key= MD5Util.Ksort(map);
        map.put("key",key);
        map.put("title",URLUtil.getURLEncoderString(title));
        String url= interfaceUrlPrefix + "liveCourseMaintenance/addRecordLesson";
        String json = HttpClientUtil.doPost(url, map);
        log.info("添加录播课节结果:"+json);
        return processJson(json);
    }

    @Override
    public AddRecordLessonResponse processJson(String json) {
        String lessonId = XiaoYiAPIJsonUtil.getJsonResult(json, "recordVideoId");
        return new AddRecordLessonResponse(lessonId);
    }
}
