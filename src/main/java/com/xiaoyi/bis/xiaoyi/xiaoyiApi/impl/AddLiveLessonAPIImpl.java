package com.xiaoyi.bis.xiaoyi.xiaoyiApi.impl;

import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import com.xiaoyi.bis.xiaoyi.constant.ExceptionConstant;
import com.xiaoyi.bis.xiaoyi.exception.ServiceException;
import com.xiaoyi.bis.xiaoyi.util.MD5Util;
import com.xiaoyi.bis.xiaoyi.util.URLUtil;
import com.xiaoyi.bis.xiaoyi.util.XiaoYiAPIJsonUtil;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddLiveLessonAPI;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddLiveLessonRequest;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddLiveLessonResponse;
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
 * 添翼申学-添加直播课节接口实现
 * @author CJ
 * @date 2019/10/12
 */
@Slf4j
@Component
public class AddLiveLessonAPIImpl implements AddLiveLessonAPI {

    //录播接口访问前缀
    @Value(value = "${tysxService.interfaceUrlPrefix}")
    private String interfaceUrlPrefix;

    @Override
    public AddLiveLessonResponse process(AddLiveLessonRequest request){
        log.info("添加课节参数:"+request);
        checkParms(request);
        String functionCode= APIConstant.API_AddLESSONCODE;
        String teacherId=request.getTeacherId();
        String classId=request.getClassId();
        String liveContent=request.getLiveContent();
        String code= UUID.randomUUID().toString();
        String liveStartDate=request.getLiveStartDate();
        String liveEndDate=request.getLiveEndDate();
        String lessonHour=request.getLessonHour();
        String liveManNumber=request.getLiveManNumber();
        String platform=APIConstant.API_PLATFORMTYPE;
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put(APIConstant.API_FUNNTIONCODE,functionCode);
        map.put(APIConstant.API_TEACHERID,teacherId);
        map.put(APIConstant.API_CLASSID,classId);
        map.put(APIConstant.API_LIVECONTENT,liveContent);
        map.put(APIConstant.API_CODE,code);
        map.put(APIConstant.API_LIVESTARTDATE,liveStartDate);
        map.put(APIConstant.API_LIVEENDDATE,liveEndDate);
        map.put(APIConstant.API_LESSONHOUR,lessonHour);
        map.put(APIConstant.API_LIVEMANNUMBER,liveManNumber);
        map.put(APIConstant.API_PLATFORM,platform);
        map.put(APIConstant.API_TIMESTAMP,timestamp);
        map.put(APIConstant.API_KEY, MD5Util.Ksort(map));
        map.put(APIConstant.API_LIVECONTENT, URLUtil.getURLEncoderString(liveContent));
        String url=interfaceUrlPrefix+"liveCourseMaintenance/addLesson";
        String json = HttpClientUtil.doPost(url, map);
        log.info("添加直播课节结果:"+json);
        return new AddLiveLessonResponse(processJson(json));
    }

    @Override
    public void checkParms(AddLiveLessonRequest request) {
        if(StringUtils.isEmpty(request)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"参数为空");
        }else if(StringUtils.isEmpty(request.getClassId())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课程编号参数为空");
        }else if(StringUtils.isEmpty(request.getLessonHour())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课节时长参数为空");
        }else if(StringUtils.isEmpty(request.getLiveContent())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课节内容参数为空");
        }else if(StringUtils.isEmpty(request.getLiveStartDate())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课节开始时间参数为空");
        }else if(StringUtils.isEmpty(request.getLiveEndDate())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课节结束时间参数为空");
        }else if(StringUtils.isEmpty(request.getLiveManNumber())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课节数值为空");
        } else if(StringUtils.isEmpty(request.getTeacherId())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"教师参数为空");
        }
    }

    @Override
    public String processJson(String json) {
        return XiaoYiAPIJsonUtil.getJsonResult(json,"lessonId");
    }

}
