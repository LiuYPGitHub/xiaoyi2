package com.xiaoyi.bis.xiaoyi.xiaoyiApi.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import com.xiaoyi.bis.xiaoyi.constant.ExceptionConstant;
import com.xiaoyi.bis.xiaoyi.exception.ServiceException;
import com.xiaoyi.bis.xiaoyi.util.MD5Util;
import com.xiaoyi.bis.xiaoyi.util.URLUtil;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.QueryLiveCourseDetailAPI;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.QueryLiveCourseDetailRequest;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.QueryLiveCourseDetailResponse;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.bean.APICourseDetail;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.bean.APILessons;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.*;

/**
 * 添翼申学-查询直播课程详情接口实现
 * @author CJ
 * @date 2019/10/12
 */
@Slf4j
@Component
public class QueryLiveCourseDetailAPIImpl implements QueryLiveCourseDetailAPI {
    @Override
    public QueryLiveCourseDetailResponse process(QueryLiveCourseDetailRequest request) {
        checkParms(request);
        String functionCode= APIConstant.API_GETCLASSDETAILCODE;
        String classId=request.getClassId();
        String platform=APIConstant.API_PLATFORMTYPE;
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put(APIConstant.API_FUNNTIONCODE,functionCode);
        map.put(APIConstant.API_CLASSID,classId);
        map.put(APIConstant.API_PLATFORM,platform);
        map.put(APIConstant.API_TIMESTAMP,timestamp);
        map.put(APIConstant.API_KEY, MD5Util.Ksort(map));
        map.put(APIConstant.API_PLATFORM, URLUtil.getURLEncoderString(platform));
        String post = HttpClientUtil.doPost(APIConstant.API_GETCLASSDETAILURL, map);
        log.info("查询直播课程详情结果:"+post);
        return new QueryLiveCourseDetailResponse(processJson(post));
    }

    @Override
    public void checkParms(QueryLiveCourseDetailRequest request) {
        if(StringUtils.isEmpty(request)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"参数为空");
        }else if(StringUtils.isEmpty(request.getClassId())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课程编号参数为空");
        }
    }

    @Override
    public APICourseDetail processJson(String json) {
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
            Object returnData1 = jsonObject.get("returnData");
            if(!"[]".equals(returnData1)&&!StringUtils.isEmpty(returnData1)){
                JSONObject returnData = JSONObject.parseObject(returnData1.toString());
                APICourseDetail course=new APICourseDetail();
                course.setIsSearch(returnData.getInteger("isSearch"));
                course.setName(returnData.getString("name"));
                course.setCode(returnData.getString("code"));
                course.setCoverImgLink(returnData.getString("coverImgLink"));
                course.setCoverVid(returnData.getString("coverVid"));
                course.setClassroomSettingId(returnData.getString("classroomSettingId"));
                course.setEnrollStartDate(returnData.getString("enrollStartDate"));
                course.setEnrollEndDate(returnData.getString("enrollEndDate"));
                course.setExpirationDuration(Integer.valueOf(returnData.getString("expirationDuration")));
                course.setClassHour(new BigDecimal(returnData.getString("classHour")));
                course.setClassType(returnData.getString("classType"));
                course.setMaxNum(returnData.getString("maxNum"));
                //course.setPrimeCost(new BigDecimal(returnData.getString("primeCost")));
                course.setCost(new BigDecimal(returnData.getString("cost")));
                course.setCourseIntroduceImg(returnData.getString("courseIntroduceImg"));
                course.setCourseInformation(returnData.getString("courseInformation"));
                course.setCourseTeachersHighlight(returnData.getString("courseTeachersHighlight"));
                course.setCourseHighlight(returnData.getString("courseHighlight"));
                course.setCourseLearningContent(returnData.getString("courseLearningContent"));
                course.setCourseObservationStyle(returnData.getString("courseObservationStyle"));
                course.setCourseConsultant(returnData.getString("courseConsultant"));
                course.setCourseWarmPrompt(returnData.getString("courseWarmPrompt"));
                String lessonDatas = returnData.getString("lessonDatas");
                if(!"[]".equals(lessonDatas)&&!StringUtils.isEmpty(lessonDatas)){
                    List<APILessons> lessonsList=new ArrayList<>();
                    JSONArray lessonObjects = JSONObject.parseArray(lessonDatas);
                    for (int i = 0; i < lessonObjects.size(); i++) {
                        JSONObject jsonObject1 = JSONObject.parseObject(lessonObjects.get(i).toString());
                        APILessons lessons=new APILessons();
                        lessons.setTeacherId(jsonObject1.getString("teacherId"));
                        lessons.setClassId(jsonObject1.getString("classId"));
                        lessons.setLiveContent(jsonObject1.getString("liveContent"));
                        lessons.setCode(jsonObject1.getString("code"));
                        lessons.setLiveStartDate(jsonObject1.getString("liveStartDate"));
                        lessons.setLiveEndDate(jsonObject1.getString("liveEndDate"));
                        lessons.setLessonHour(new BigDecimal(jsonObject1.getString("lessonHour")));
                        lessons.setIsHd(jsonObject1.getString("isHd"));
                        lessons.setIsAutoOnstage(jsonObject1.getString("isAutoOnstage"));
                        lessonsList.add(lessons);
                    }
                    course.setLessonsList(lessonsList);
                }else{
                    course.setLessonsList(null);
                }
                return course;
            }
            return null;
        }else{
            throw new ServiceException(jsonObject.get("returnCode").toString(),jsonObject.get("returnMessage").toString());
        }
    }
}
