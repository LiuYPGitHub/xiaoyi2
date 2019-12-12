package com.xiaoyi.bis.xiaoyi.xiaoyiApi.impl;

import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import com.xiaoyi.bis.xiaoyi.constant.ExceptionConstant;
import com.xiaoyi.bis.xiaoyi.exception.ServiceException;
import com.xiaoyi.bis.xiaoyi.util.MD5Util;
import com.xiaoyi.bis.xiaoyi.util.URLUtil;
import com.xiaoyi.bis.xiaoyi.util.XiaoYiAPIJsonUtil;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddLiveCourseAPI;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddLiveCourseRequest;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddLiveCourseResponse;
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
 * 添翼申学-添加直播课程接口实现
 * @author CJ
 * @date 2019/10/12
 */
@Slf4j
@Component
public class AddLiveCourseAPIImpl implements AddLiveCourseAPI {

    //录播接口访问前缀
    @Value(value = "${tysxService.interfaceUrlPrefix}")
    private String interfaceUrlPrefix;

    @Override
    public AddLiveCourseResponse process(AddLiveCourseRequest request) throws Exception {
        checkParms(request);

        String functionCode= APIConstant.API_AddLIVECOURSECODE;
        String name=request.getName();
        String code= UUID.randomUUID().toString();
        String coverImgLink=request.getCoverImgLink();
        String enrollStartDate=request.getEnrollStartDate();
        String enrollEndDate=request.getEnrollEndDate();
        String classHour=request.getClassHour();
        String classType=request.getClassType(); //学科教育 素质教育 国际教育
        String primeCost=request.getPrimeCost();
        String cost=request.getCost();
        String siteName=request.getSiteName();

        String platform=APIConstant.API_PLATFORMTYPE;
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        if(!StringUtils.isEmpty(request.getExpirationDuration())){
            map.put(APIConstant.API_EXPIRATIONDURATION,request.getExpirationDuration());
        }else{
            map.put(APIConstant.API_EXPIRATIONDURATION,"365");
        }
        if(!StringUtils.isEmpty(request.getShowEvaluation())){
            map.put(APIConstant.API_SHOWEVALUATION,request.getShowEvaluation());
        }

        map.put(APIConstant.API_MAXNUM,request.getMaxNum());
        if(!StringUtils.isEmpty(siteName)){
            map.put(APIConstant.API_SITENAME,siteName);
        }else {
            map.put(APIConstant.API_SITENAME,APIConstant.API_SITENAMEVALUE);
        }
        map.put(APIConstant.API_FUNNTIONCODE,functionCode);
        map.put(APIConstant.API_NAME,name);
        map.put(APIConstant.API_CODE,code);
        map.put(APIConstant.API_COVERIMALINK,coverImgLink);
        map.put(APIConstant.API_ENROLLSTARTDATE,enrollStartDate);
        map.put(APIConstant.API_ENROLLENDDATE,enrollEndDate);
        map.put(APIConstant.API_CLASSHOUR,classHour);
        map.put(APIConstant.API_CLASSTYPE,classType);
        map.put(APIConstant.API_PRIMECOST,primeCost);
        map.put(APIConstant.API_COST,cost);
        map.put(APIConstant.API_PLATFORM,platform);
        map.put(APIConstant.API_TIMESTAMP,timestamp);
        map.put(APIConstant.API_KEY, MD5Util.Ksort(map));

        String courseIntroduceImg=request.getCourseIntroduceImg();
        String courseInformation=request.getCourseInformation();
        String courseTeachersHighlight=request.getCourseTeachersHighlight();
        String courseHighlight=request.getCourseHighlight();
        String courseLearningContent=request.getCourseLearningContent();
        String courseObservationStyle=request.getCourseObservationStyle();
        String courseConsultant=request.getCourseConsultant();
        String courseWarmPrompt=request.getCourseWarmPrompt();
        if(!StringUtils.isEmpty(siteName)){
            map.put(APIConstant.API_SITENAME, URLUtil.getURLEncoderString(siteName));
        }else {
            map.put(APIConstant.API_SITENAME,URLUtil.getURLEncoderString(APIConstant.API_SITENAMEVALUE));
        }
        map.put(APIConstant.API_NAME,URLUtil.getURLEncoderString(name));
        map.put(APIConstant.API_CLASSTYPE,URLUtil.getURLEncoderString(classType));
        map.put(APIConstant.API_COURSEINTRODUCEIMG,courseIntroduceImg);
        map.put(APIConstant.API_COURSEINFORMATION,courseInformation); //courseInformation
        map.put(APIConstant.API_COURSETEACHERSHIGHLIGHT,courseTeachersHighlight);
        map.put(APIConstant.API_COURSEHIGHLIGHT,courseHighlight);
        map.put(APIConstant.API_COURSELEARNINGCONTENT,courseLearningContent);
        map.put(APIConstant.API_COURSEOBSERVATIONSTYLE,courseObservationStyle);
        map.put(APIConstant.API_COURSECONSULTANT,courseConsultant);
        map.put(APIConstant.API_COURSEWARMPROMPT,courseWarmPrompt);
        String url= interfaceUrlPrefix +"liveCourseMaintenance/addLiveClass";
        String json = HttpClientUtil.doPost(url,map);
        log.info("添加直播课程结果:"+json);
        return new AddLiveCourseResponse(processJson(json));
    }

    public void checkParms(AddLiveCourseRequest request){
        if(StringUtils.isEmpty(request)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"request参数为空");
        }else if(StringUtils.isEmpty(request.getClassHour())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课程参数为空");
        }else if(StringUtils.isEmpty(request.getClassType())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课程类型参数为空");
        }else if(StringUtils.isEmpty(request.getCost())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"售价参数为空");
        }else if(StringUtils.isEmpty(request.getCourseConsultant())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课程咨询参数为空");
        }else if(StringUtils.isEmpty(request.getCourseHighlight())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课程亮点参数为空");
        }else if(StringUtils.isEmpty(request.getCourseInformation())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课程简介参数为空");
        }else if(StringUtils.isEmpty(request.getCourseIntroduceImg())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课程主图参数为空");
        }else if(StringUtils.isEmpty(request.getCourseLearningContent())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"学习内容参数为空");
        }else if(StringUtils.isEmpty(request.getCourseObservationStyle())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"观看方式参数为空");
        }else if(StringUtils.isEmpty(request.getCourseTeachersHighlight())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"师资介绍参数为空");
        }else if(StringUtils.isEmpty(request.getCourseWarmPrompt())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"温馨提示参数为空");
        }else if(StringUtils.isEmpty(request.getCoverImgLink())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课程封面参数为空");
        }else if(StringUtils.isEmpty(request.getEnrollEndDate())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"招生结束时间参数为空");
        }else if(StringUtils.isEmpty(request.getEnrollStartDate())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"招生开始时间参数为空");
        }else if(StringUtils.isEmpty(request.getMaxNum())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"最大值参数为空");
        }else if(StringUtils.isEmpty(request.getName())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课程名称参数为空");
        }else if(StringUtils.isEmpty(request.getPrimeCost())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"原价参数为空");
        }/*else if(StringUtils.isEmpty(request.getSiteName())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"机构参数为空");
        }*/
    }

    public String processJson(String json){
        return XiaoYiAPIJsonUtil.getJsonResult(json,"classId");
    }

}
