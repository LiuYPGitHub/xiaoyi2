package com.xiaoyi.bis.xiaoyi.videoApi.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.xiaoyi.bis.common.utils.HttpClientUtil;
import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import com.xiaoyi.bis.xiaoyi.constant.ExceptionConstant;
import com.xiaoyi.bis.xiaoyi.exception.ServiceException;
import com.xiaoyi.bis.xiaoyi.util.DateUtil;
import com.xiaoyi.bis.xiaoyi.util.SHA1Util;
import com.xiaoyi.bis.xiaoyi.videoApi.BaseCheck;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoClassAPI;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoClassRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoClassResponse;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APINode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

/**
 * @author CJ
 * @date 2019/10/22
 */
@Component
public class QueryVideoClassAPIImpl extends BaseCheck implements QueryVideoClassAPI {

    @Override
    public QueryVideoClassResponse process(QueryVideoClassRequest request) throws NoSuchAlgorithmException {
        String ptime= DateUtil.getNowDate();
        String userId= APIConstant.API_USER_ID;
        String data = "ptime="+ptime+"&userid="+ userId + APIConstant.API_SECRET_KEY;
        String sign = SHA1Util.sha1(data).toUpperCase();
        String url="http://api.polyv.net/v2/video/"+userId+"/cataJson?ptime="+ptime+"&sign="+sign+"&cataId=1499328808069";
        String json = HttpClientUtil.doGet(url);
        return processJson(json);
    }

    @Override
    public QueryVideoClassResponse processJson(String json) {
        checkJson(json);
        JSONObject object = JSONObject.parseObject(json);
        Object code = object.get("code");
        QueryVideoClassResponse response=new QueryVideoClassResponse();
        if(!StringUtils.isEmpty(code) && "200".equals(code.toString())){
            response.setCode(object.getString("code"));
            response.setStatus(object.getString("status"));
            response.setMessage(object.getString("message"));
            String dataJson = object.getString("data");
            Type listType = new TypeToken<List<APINode>>() {}.getType();
            List<APINode> apiNodes = new Gson().fromJson(dataJson, listType);
            response.setData(apiNodes);
        }else{
            throw new ServiceException(ExceptionConstant.EXCEPTION_JSON_ERROR," json:"+json);
        }
        return response;
    }

}
