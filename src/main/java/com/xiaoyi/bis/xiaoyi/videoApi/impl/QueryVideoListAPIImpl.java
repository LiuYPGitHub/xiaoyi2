package com.xiaoyi.bis.xiaoyi.videoApi.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import com.xiaoyi.bis.xiaoyi.constant.ExceptionConstant;
import com.xiaoyi.bis.xiaoyi.exception.ServiceException;
import com.xiaoyi.bis.xiaoyi.util.DateUtil;
import com.xiaoyi.bis.xiaoyi.util.MD5HexUtil;
import com.xiaoyi.bis.xiaoyi.videoApi.BaseCheck;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoListAPI;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoListRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoListResponse;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CJ
 * @date 2019/10/23
 */
@Component
public class QueryVideoListAPIImpl extends BaseCheck implements QueryVideoListAPI {

    @Override
    public QueryVideoListResponse process(QueryVideoListRequest request) throws NoSuchAlgorithmException {
        checkParms(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new HashMap<>();
        map.put("appId", APIConstant.API_APP_ID);
        map.put("timestamp", DateUtil.getNowDate());
        map.put("userId", APIConstant.API_USER_ID);
        if (!StringUtils.isEmpty(request.getStartDate())) {
            map.put("startDate", sdf.format(request.getStartDate()));
        }
        if (!StringUtils.isEmpty(request.getEndDate())) {
            map.put("endDate", sdf.format(request.getEndDate()));
        }
        if (!StringUtils.isEmpty(request.getSessionId())) {
            map.put("sessionId", request.getSessionId());
        }
        String sign = MD5HexUtil.Ksort(map);
        map.put("sign", sign);
        String channelId = request.getChannelId();
        String url = "http://api.live.polyv.net/v2/channels/" + channelId + "/recordFiles";
        String json = HttpClientUtil.doGet(url, map);
        return processJson(json);
    }

    @Override
    public void checkParms(QueryVideoListRequest request) {
        if (StringUtils.isEmpty(request)) {
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "参数为空");
        } else if (StringUtils.isEmpty(request.getChannelId())) {
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "渠道编号参数为空");
        }
    }

    @Override
    public QueryVideoListResponse processJson(String json) {
        checkJson(json);
        JSONObject object = JSONObject.parseObject(json);
        Object code = object.get("code");
        QueryVideoListResponse response = new QueryVideoListResponse();
        response.setCode(object.getString("code"));
        response.setStatus(object.getString("status"));
        response.setMessage(object.getString("message"));
        if (!StringUtils.isEmpty(code) && "200".equals(code.toString())) {
            JSONArray data = JSONArray.parseArray(object.getString("data"));
            List<APIRecordFile> files = new ArrayList<>();
            for (Object datum : data) {
                APIRecordFile file = new APIRecordFile();
                JSONObject jsonObject = JSONObject.parseObject(datum.toString());
                file.setDuration(jsonObject.getString("duration"));
                file.setFileName(jsonObject.getString("fileName"));
                file.setFileSize(jsonObject.getString("fileSize"));
                file.setFileId(jsonObject.getString("fileId"));
                file.setUrl(jsonObject.getString("url"));
                file.setChannelSessionId(jsonObject.getString("channelSessionId"));
                file.setStartTime(jsonObject.getLong("startTime"));
                file.setEndTime(jsonObject.getLong("endTime"));
                file.setBitrate(jsonObject.getString("bitrate"));
                file.setResolution(jsonObject.getString("resolution"));
                file.setChannelId(jsonObject.getString("channelId"));
                files.add(file);
            }
            response.setData(files);
            return response;
        } else {
            // throw new ServiceException(ExceptionConstant.EXCEPTION_JSON_ERROR," json:"+json);
            return response;
        }
    }

}
