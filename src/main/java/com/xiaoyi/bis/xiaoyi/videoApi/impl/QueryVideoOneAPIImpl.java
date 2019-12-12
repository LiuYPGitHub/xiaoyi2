package com.xiaoyi.bis.xiaoyi.videoApi.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import com.xiaoyi.bis.xiaoyi.util.DateUtil;
import com.xiaoyi.bis.xiaoyi.util.MD5HexUtil;
import com.xiaoyi.bis.xiaoyi.videoApi.BaseCheck;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoOneAPI;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoOneRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoOneResponse;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CJ
 * @date 2019/10/23
 */
@Slf4j
@Component
public class QueryVideoOneAPIImpl extends BaseCheck implements QueryVideoOneAPI {

    @Override
    public QueryVideoOneResponse process(QueryVideoOneRequest request) throws NoSuchAlgorithmException {
        Map<String, String> map = new HashMap<>();
        map.put("appId", APIConstant.API_APP_ID);
        map.put("timestamp", DateUtil.getNowDate());
        map.put("channelId", request.getChannelId());
        map.put("fileId", request.getFileId());
        String sign = MD5HexUtil.Ksort(map);
        map.put("sign", sign);
        String url = "https://api.polyv.net/live/v3/channel/record/get";
        String json = HttpClientUtil.doGet(url, map);
        log.info("查询保利威单个视频文件结果:" + json);
        return processJson(json);
    }

    @Override
    public QueryVideoOneResponse processJson(String json) {
        checkJson(json);
        JSONObject object = JSONObject.parseObject(json);
        Object code = object.get("code");
        QueryVideoOneResponse response = new QueryVideoOneResponse();
        response.setCode(object.getString("code"));
        response.setStatus(object.getString("status"));
        response.setMessage(object.getString("message"));
        if (!StringUtils.isEmpty(code) && "200".equals(code.toString())) {
            JSONObject jsonObject = JSONArray.parseObject(object.getString("data"));
            APIRecordFile file = new APIRecordFile();
            file.setDuration(jsonObject.getString("duration"));
            file.setFileName(jsonObject.getString("filename"));
            file.setFileSize(jsonObject.getString("filesize"));
            file.setUserId(jsonObject.getString("userId"));
            file.setFileId(jsonObject.getString("fileId"));
            file.setChannelSessionId(jsonObject.getString("channelSessionId"));
            file.setCreatedTime(jsonObject.getString("createdTime"));
            if (!StringUtils.isEmpty(jsonObject.getLong("startTime"))) {
                file.setStartTime(jsonObject.getLong("startTime"));
            }
            if (!StringUtils.isEmpty(jsonObject.getLong("endTime"))) {
                file.setEndTime(jsonObject.getLong("endTime"));
            }
            file.setBitrate(jsonObject.getString("bitrate"));
            file.setResolution(jsonObject.getString("resolution"));
            file.setChannelId(jsonObject.getString("channelId"));
            file.setHeight(jsonObject.getString("height"));
            file.setWidth(jsonObject.getString("width"));
            file.setLiveType(jsonObject.getString("liveType"));
            if (!StringUtils.isEmpty(jsonObject.getString("m3u8"))) {
                file.setM3u8(jsonObject.getString("m3u8"));
            }
            if (!StringUtils.isEmpty(jsonObject.getString("mp4"))) {
                file.setMp4(jsonObject.getString("mp4"));
            }
            response.setData(file);
            return response;
        } else {
            return response;

        }
    }

}
