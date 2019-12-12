package com.xiaoyi.bis.xiaoyi.xiaoyiApi.impl;

import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import com.xiaoyi.bis.xiaoyi.util.MD5Util;
import com.xiaoyi.bis.xiaoyi.util.URLUtil;
import com.xiaoyi.bis.xiaoyi.util.XiaoYiAPIJsonUtil;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddRecordCourseAPI;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddRecordCourseRequest;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddRecordCourseResponse;
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
public class AddRecordCourseAPIImpl implements AddRecordCourseAPI {

    //录播接口访问前缀
    @Value(value = "${tysxService.interfaceUrlPrefix}")
    private String interfaceUrlPrefix;

    @Override
    public AddRecordCourseResponse process(AddRecordCourseRequest request) {
        String functionCode= APIConstant.API_AddRECORDCODE;
        String name=request.getName();
        String code= UUID.randomUUID().toString();
        String coverImgLink=request.getCoverImgLink();
        String classHour=request.getClassHour();
        String classType=request.getClassType(); //学科教育 素质教育 国际教育
        String recordType=request.getRecordType();
        String primeCost=request.getPrimeCost();
        String cost=request.getCost();
        String siteName=request.getSiteName(); //添翼申学
        String maxNum=request.getMaxNum();
        String platform=APIConstant.API_PLATFORMTYPE;
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        if(!StringUtils.isEmpty(request.getExpirationDuration())){
            map.put(APIConstant.API_EXPIRATIONDURATION,request.getExpirationDuration());
        }else{
            map.put(APIConstant.API_EXPIRATIONDURATION,"365");
        }
        if(!StringUtils.isEmpty(request.getCoverVid())){
            map.put("coverVid",request.getCoverVid());
        }
        map.put(APIConstant.API_MAXNUM,maxNum);
        map.put(APIConstant.API_SITENAME,siteName);
        map.put(APIConstant.API_FUNNTIONCODE,functionCode);
        map.put(APIConstant.API_NAME,name);
        map.put(APIConstant.API_CODE,code);
        map.put(APIConstant.API_COVERIMALINK,coverImgLink);

        String enrollStartDate= request.getEnrollStartDate();
        if(StringUtils.isEmpty(enrollStartDate)){
            Calendar instance = Calendar.getInstance();
            enrollStartDate=DateFormatUtils.format(instance.getTime(), "yyyyMMddHHmmss");
        }
        String enrollEndDate=request.getEnrollEndDate();
        if(StringUtils.isEmpty(enrollEndDate)){
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.YEAR,2);
            enrollEndDate=DateFormatUtils.format(instance.getTime(), "yyyyMMddHHmmss");
        }
        map.put(APIConstant.API_ENROLLSTARTDATE,enrollStartDate);
        map.put(APIConstant.API_ENROLLENDDATE,enrollEndDate);
        map.put(APIConstant.API_CLASSHOUR,classHour);
        if(!StringUtils.isEmpty(request.getTotalTime())){
            String totalTime=request.getTotalTime();
            map.put("totalTime",totalTime);
        }
        map.put(APIConstant.API_CLASSTYPE,classType);
        map.put("recordType",recordType);
        map.put(APIConstant.API_PRIMECOST,primeCost);
        map.put(APIConstant.API_COST,cost);
        map.put(APIConstant.API_PLATFORM,platform);
        map.put(APIConstant.API_TIMESTAMP,timestamp);
        map.put(APIConstant.API_KEY, MD5Util.Ksort(map));
        if(!StringUtils.isEmpty(siteName)){
            map.put(APIConstant.API_SITENAME, URLUtil.getURLEncoderString(siteName));
        }else {
            map.put(APIConstant.API_SITENAME,URLUtil.getURLEncoderString(APIConstant.API_SITENAMEVALUE));
        }
        if(!"2".equals(recordType)){
            String courseIntroduceImg=request.getCourseIntroduceImg();
            String courseInformation=request.getCourseInformation();
            String courseTeachersHighlight=request.getCourseTeachersHighlight();
            String courseHighlight=request.getCourseHighlight();
            String courseLearningContent=request.getCourseLearningContent();
            String courseObservationStyle=request.getCourseObservationStyle();
            String courseConsultant=request.getCourseConsultant();
            String courseWarmPrompt=request.getCourseWarmPrompt();
            map.put(APIConstant.API_NAME, URLUtil.getURLEncoderString(name));
            map.put(APIConstant.API_CLASSTYPE,URLUtil.getURLEncoderString(classType));
            map.put(APIConstant.API_COURSEINTRODUCEIMG,courseIntroduceImg);
            map.put(APIConstant.API_COURSEINFORMATION,courseInformation); //courseInformation
            map.put(APIConstant.API_COURSETEACHERSHIGHLIGHT,courseTeachersHighlight);
            map.put(APIConstant.API_COURSEHIGHLIGHT,courseHighlight);
            map.put(APIConstant.API_COURSELEARNINGCONTENT,courseLearningContent);
            map.put(APIConstant.API_COURSEOBSERVATIONSTYLE,courseObservationStyle);
            map.put(APIConstant.API_COURSECONSULTANT,courseConsultant);
            map.put(APIConstant.API_COURSEWARMPROMPT,courseWarmPrompt);
        }
        String url= interfaceUrlPrefix +"liveCourseMaintenance/addRecordClass";
        String json = HttpClientUtil.doPost(url,map);
        log.info("添加录播课程结果:"+json);
        return processJson(json);
    }

    @Override
    public void checkParms(AddRecordCourseRequest request) {

    }

    @Override
    public AddRecordCourseResponse processJson(String json) {
        String classId = XiaoYiAPIJsonUtil.getJsonResult(json, "classId");
        return new AddRecordCourseResponse(classId);
    }

}
