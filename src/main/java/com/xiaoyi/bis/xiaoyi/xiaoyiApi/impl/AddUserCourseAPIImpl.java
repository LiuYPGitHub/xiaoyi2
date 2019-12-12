package com.xiaoyi.bis.xiaoyi.xiaoyiApi.impl;

import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.constant.ExceptionConstant;
import com.xiaoyi.bis.xiaoyi.exception.ServiceException;
import com.xiaoyi.bis.xiaoyi.util.MD5Util;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddUserCourseAPI;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddUserCourseRequest;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddUserCourseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CJ
 * @date 2019/10/31
 */
@Slf4j
@Component
public class AddUserCourseAPIImpl implements AddUserCourseAPI {

    //录播接口访问前缀
    @Value(value = "${tysxService.interfaceUrlPrefix}")
    private String interfaceUrlPrefix;

    @Override
    public AddUserCourseResponse process(AddUserCourseRequest request) {
        String functionCode="addUserClass";
        String classId=request.getClassId();
        String channel=request.getChannel();
        String mobilephone=request.getMobilephone();
        String platform="liveCourseConnect";
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("channel",channel);
        map.put("classId",classId);
        map.put("mobilephone",mobilephone);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        map.put("key", MD5Util.Ksort(map));
        String url= interfaceUrlPrefix + "liveCourseMaintenance/addUserClass";
        String json = HttpClientUtil.doPost(url, map);
        log.info("添加用户课程结果:"+json);
        return processJson(json);
    }

    @Override
    public AddUserCourseResponse processJson(String json) {
        AddUserCourseResponse response = new AddUserCourseResponse();
        if(StringUtils.isEmpty(json)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"返回参数为空");
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if(StringUtils.isEmpty(jsonObject)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"返回参数为空");
        }
        Object returnCode = jsonObject.get("returnCode");
        if(StringUtils.isEmpty(returnCode)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"返回参数状态码为空");
        }
        if("1".equals(returnCode.toString())){
            response.setFlag(true);
            response.success();
        }else{
            response.setFlag(false);
            String mess = jsonObject.get("returnMessage").toString();
            response.setMessage(mess);
            response.failed(mess);
        }
        return response;
    }
}
