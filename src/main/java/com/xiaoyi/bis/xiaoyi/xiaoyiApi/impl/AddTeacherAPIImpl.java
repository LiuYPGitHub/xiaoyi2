package com.xiaoyi.bis.xiaoyi.xiaoyiApi.impl;

import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import com.xiaoyi.bis.xiaoyi.constant.ExceptionConstant;
import com.xiaoyi.bis.xiaoyi.exception.ServiceException;
import com.xiaoyi.bis.xiaoyi.util.MD5Util;
import com.xiaoyi.bis.xiaoyi.util.URLUtil;
import com.xiaoyi.bis.xiaoyi.util.XiaoYiAPIJsonUtil;
import com.xiaoyi.bis.xiaoyi.util.XiaoYiSecurityDesUtil;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddTeacherAPI;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddTeacherRequest;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddTeacherResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 添翼申学-添加教师接口实现
 * @author CJ
 * @date 2019/10/12
 */
@Slf4j
@Component
public class AddTeacherAPIImpl implements AddTeacherAPI {

    @Override
    public AddTeacherResponse process(AddTeacherRequest request) throws Exception {
        checkParms(request);
        String functionCode= APIConstant.API_AddTEACHERCODE;
        String userId=request.getUserId();
        String siteName=request.getSiteName();
        String orient=request.getOrient();
        String note=request.getNote();
        String platform=APIConstant.API_PLATFORMTYPE;
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put(APIConstant.API_FUNNTIONCODE,functionCode);
        map.put(APIConstant.API_NOTE,note);
        map.put(APIConstant.API_USERID,userId);
        map.put(APIConstant.API_SITENAME,siteName);
        map.put(APIConstant.API_ORIENT,orient);
        map.put(APIConstant.API_PLATFORM,platform);
        map.put(APIConstant.API_TIMESTAMP,timestamp);
        String key= MD5Util.Ksort(map);
        userId = new XiaoYiSecurityDesUtil(APIConstant.API_ENCRYPE).encrypt(userId);
        map.put(APIConstant.API_USERID,userId);
        map.put(APIConstant.API_SITENAME, URLUtil.getURLEncoderString(siteName));
        map.put(APIConstant.API_ORIENT,URLUtil.getURLEncoderString(orient));
        map.put(APIConstant.API_NOTE,URLUtil.getURLEncoderString(note));
        map.put(APIConstant.API_KEY,key);
        String post = HttpClientUtil.doPost(APIConstant.API_AddTEACHERURL,map);
        log.info("添加教师结果:"+post);
        return new AddTeacherResponse(processJson(post));
    }

    public void checkParms(AddTeacherRequest request){
        if(StringUtils.isEmpty(request)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"参数为空");
        }else if(StringUtils.isEmpty(request.getUserId())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"用户参数为空");
        }else if(StringUtils.isEmpty(request.getNote())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"备注参数为空");
        }else if(StringUtils.isEmpty(request.getOrient())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"专业或方向参数为空");
        }else if(StringUtils.isEmpty(request.getSiteName())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"机构为空");
        }
    }

    public String processJson(String json){
        return XiaoYiAPIJsonUtil.getJsonResult(json,"teacherId");
    }

}
