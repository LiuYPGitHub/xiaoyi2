package com.xiaoyi.bis.xiaoyi.videoApi.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import com.xiaoyi.bis.xiaoyi.util.SHA1Util;
import com.xiaoyi.bis.xiaoyi.videoApi.GetVideoOneInfoAPI;
import com.xiaoyi.bis.xiaoyi.videoApi.GetVideoOneInfoRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.GetVideoOneInfoResopnse;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIVideoOneInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取单个视频信息
 * @author CJ
 * @date 2019/12/5
 */
@Component
@Slf4j
public class GetVideoOneInfoAPIImpl implements GetVideoOneInfoAPI {
    @Override
    public GetVideoOneInfoResopnse process(GetVideoOneInfoRequest request) throws NoSuchAlgorithmException {
        String vid=request.getVid();
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        String ptime = String.valueOf(instance.getTimeInMillis());
        String data = "ptime="+ptime+"&vid="+vid+ APIConstant.API_SECRET_KEY;
        String sign = SHA1Util.sha1(data).toUpperCase();
        Map<String,String> map=new HashMap<>();
        map.put("ptime",ptime);
        map.put("vid",vid);
        map.put("sign",sign);
        String url="http://api.polyv.net/v2/video/"+APIConstant.API_USER_ID+"/get-video-msg";//?ptime="+ptime+"&sign="+sign;//?ptime="+ptime+"&sign="+sign+"&cataId=1499328808069";
        String json = HttpClientUtil.doPost(url,map);
        log.info("获取单个视频信息结果:"+json);
        return processJson(json);
    }

    @Override
    public GetVideoOneInfoResopnse processJson(String json) {
        GetVideoOneInfoResopnse resopnse=new GetVideoOneInfoResopnse();
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray jsonArray = JSONObject.parseArray(jsonObject.getString("data"));
        for (Object o : jsonArray) {
            APIVideoOneInfo info=new APIVideoOneInfo();
            JSONObject jsonObject1 = JSONObject.parseObject(o.toString());
            info.setStatus(jsonObject1.getInteger("status"));
            resopnse.setVideoOneInfo(info);
        }
        return resopnse;
    }
}
