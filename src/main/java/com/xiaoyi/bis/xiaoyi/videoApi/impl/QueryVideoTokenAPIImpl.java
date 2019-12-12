package com.xiaoyi.bis.xiaoyi.videoApi.impl;

import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import com.xiaoyi.bis.xiaoyi.util.MD5Util;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoTokenAPI;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoTokenRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoTokenResponse;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * 获取保利威视频播放token实现
 * @author CJ
 * @date 2019/11/25
 */
@Component
@Slf4j
public class QueryVideoTokenAPIImpl implements QueryVideoTokenAPI {

    @Override
    public QueryVideoTokenResponse process(QueryVideoTokenRequest request) {
        String viewerId = UUID.randomUUID().toString();
        String vid = request.getVid();
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        String ptime = String.valueOf(instance.getTimeInMillis());
        String userId = APIConstant.API_USER_ID;
        String secretKey = APIConstant.API_SECRET_KEY;
        String data = secretKey +"ts"+ptime+"userId"+userId+"videoId"+vid+"viewerId"+viewerId+secretKey;
        String sign = MD5Util.getMD5(data).toUpperCase();
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId);
        map.put("ts",ptime);
        map.put("videoId",vid);
        map.put("viewerId",viewerId);
        map.put("sign",sign);
        String url="https://hls.videocc.net/service/v1/token";
        String json = HttpClientUtil.doPost(url,map);
        log.info("获取保利威视频播放token结果:"+json);
        return processJson(json);
    }

    @Override
    public QueryVideoTokenResponse processJson(String json) {
        QueryVideoTokenResponse response=new QueryVideoTokenResponse();
        JSONObject jsonObject = JSONObject.parseObject(json);
        if(!StringUtils.isEmpty(jsonObject.getString("code"))&&"200".equals(jsonObject.getString("code"))){
            response.setCode(jsonObject.getString("code"));
            response.setStatus(jsonObject.getString("status"));
            response.setMessage(jsonObject.getString("message"));
            JSONObject data1 = JSONObject.parseObject(jsonObject.get("data").toString());
            APIToken apiToken=new APIToken();
            apiToken.setToken(StringUtils.isEmpty(data1.getString("token"))?"":data1.getString("token"));
            apiToken.setUserId(StringUtils.isEmpty(data1.getString("userId"))?"":data1.getString("userId"));
            apiToken.setAppId(StringUtils.isEmpty(data1.getString("appId"))?"":data1.getString("appId"));
            apiToken.setVideoId(StringUtils.isEmpty(data1.getString("videoId"))?"":data1.getString("videoId"));
            apiToken.setViewerIp(StringUtils.isEmpty(data1.getString("viewerIp"))?"":data1.getString("viewerIp"));
            apiToken.setViewerId(StringUtils.isEmpty(data1.getString("viewerId"))?"":data1.getString("viewerId"));
            apiToken.setViewerName(StringUtils.isEmpty(data1.getString("viewerName"))?"":data1.getString("viewerName"));
            apiToken.setExtraParams(StringUtils.isEmpty(data1.getString("extraParams"))?"":data1.getString("extraParams"));
            apiToken.setTtl(StringUtils.isEmpty(data1.getString("ttl"))?"":data1.getString("ttl"));
            apiToken.setCreatedTime(StringUtils.isEmpty(data1.getLong("createdTime"))?null:data1.getLong("createdTime"));
            apiToken.setExpiredTime(StringUtils.isEmpty(data1.getLong("expiredTime"))?null:data1.getLong("expiredTime"));
            apiToken.setIswxa(StringUtils.isEmpty(data1.getInteger("iswxa"))?null:data1.getInteger("iswxa"));
            apiToken.setDisposable(StringUtils.isEmpty(data1.getBoolean("disposable"))?false:data1.getBoolean("disposable"));
            response.setApiToken(apiToken);
            return response;
        }else{
            return null;
        }
    }

}
