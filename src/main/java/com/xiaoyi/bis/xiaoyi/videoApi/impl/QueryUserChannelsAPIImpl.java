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
import com.xiaoyi.bis.xiaoyi.videoApi.QueryUserChannelsAPI;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryUserChannelsRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryUserChannelsResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author CJ
 * @date 2019/10/23
 */
@Component
public class QueryUserChannelsAPIImpl extends BaseCheck implements QueryUserChannelsAPI {

    @Override
    public QueryUserChannelsResponse process(QueryUserChannelsRequest request) throws NoSuchAlgorithmException {
        String appId = APIConstant.API_APP_ID;
        String userId = APIConstant.API_USER_ID;
        // 创建参数表 （创建接口需要传递的所有参数表）
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appId", appId);
        paramMap.put("timestamp", DateUtil.getNowDate());

        String sign = MD5HexUtil.Ksort(paramMap);

        String url="http://api.live.polyv.net/v1/users/" + userId + "/channels?appId=" + appId + "&timestamp=" + DateUtil.getNowDate() + "&sign=" + sign;
        String json = HttpClientUtil.doGet(url);
        return processJson(json);
    }

    @Override
    public QueryUserChannelsResponse processJson(String json) {
        checkJson(json);
        JSONObject object = JSONObject.parseObject(json);
        QueryUserChannelsResponse response=new QueryUserChannelsResponse();
        List<String> data=new ArrayList<>();
        String result = object.getString("result");
        if(!StringUtils.isEmpty(result)){
            JSONArray objects = JSONObject.parseArray(result);
            for (Object o : objects) {
                data.add(o.toString());
            }
            response.success();
            response.setData(data);
            return response;
        }else{
            throw new ServiceException(ExceptionConstant.EXCEPTION_JSON_ERROR," json:"+json);
        }
    }
}
