package com.xiaoyi.bis.xiaoyi.videoApi.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import com.xiaoyi.bis.xiaoyi.util.DateUtil;
import com.xiaoyi.bis.xiaoyi.util.SHA1Util;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoByVidAPI;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoByVidRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoByVidResponse;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIVideo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 根据指定Vid获取单个录制视频实现
 * @author CJ
 * @date 2019/11/19
 */
@Slf4j
@Component
public class QueryVideoByVidAPIImpl implements QueryVideoByVidAPI {

    @Override
    public QueryVideoByVidResponse process(QueryVideoByVidRequest request) throws NoSuchAlgorithmException {
        String vId = request.getVid();
        String ptime= DateUtil.getNowDate();
        String data = "ptime=" + ptime + "&vid="+ vId + APIConstant.API_SECRET_KEY;
        String sign = SHA1Util.sha1(data).toUpperCase();
        Map<String,String> map=new HashMap<>();
        map.put("ptime",ptime);
        map.put("vid",vId);
        map.put("sign",sign);
        String url="http://api.polyv.net/v2/video/"+APIConstant.API_USER_ID+"/get-live-playback";
        String json = HttpClientUtil.doPost(url,map);
        log.info("根据指定Vid获取单个录制视频结果:"+json);
        return processJson(json);
    }

    @Override
    public QueryVideoByVidResponse processJson(String json) {
        QueryVideoByVidResponse response=new QueryVideoByVidResponse();
        JSONObject jsonObject=JSONObject.parseObject(json);
        response.setCode(jsonObject.getString("code"));
        response.setStatus(jsonObject.getString("status"));
        response.setMessage(jsonObject.getString("message"));
        JSONArray jsonArray = JSON.parseArray(jsonObject.getString("data"));
        APIVideo apiVideo=new APIVideo();
        apiVideo.setCode(jsonObject.getString("code"));
        apiVideo.setStatus(jsonObject.getString("status"));
        apiVideo.setMessage(jsonObject.getString("message"));
        for (Object result : jsonArray) {
            JSONObject resultObject = JSONObject.parseObject(result.toString());
            apiVideo.setVid(resultObject.getString("vid"));
            apiVideo.setFileSize(resultObject.getInteger("fileSize"));
            apiVideo.setFileUrl(resultObject.getString("fileUrl"));
            apiVideo.setSessionId(resultObject.getString("sessionId"));
            apiVideo.setType(resultObject.getString("type"));
            apiVideo.setChannelId(resultObject.getString("channelId"));
            response.setData(apiVideo);
        }
        return response;
    }

}
