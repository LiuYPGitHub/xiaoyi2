package com.xiaoyi.bis.xiaoyi.controller;

import com.xiaoyi.bis.common.annotation.ShowLogger;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.xiaoyi.videoApi.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.security.NoSuchAlgorithmException;

/**
 * 校翼保利威接口内部调用示例
 * @author CJ
 * @date 2019/11/19
 */
@Api(tags = {"videoApi"},value = "校翼")
@RestController
public class VideoApiController {

    @Autowired
    private QueryVideoTokenAPI queryVideoTokenAPI;
    @Autowired
    private QueryVideoByVidAPI queryVideoByVidAPI;
    @Autowired
    private GetVideoOneInfoAPI getVideoOneInfoAPI;


    @ApiOperation(value = "根据指定Vid获取单个录制视频MP4信息",notes = "根据指定Vid获取单个录制视频MP4信息")
    @RequestMapping(path = "/api/queryVideoByVidAPI",method = RequestMethod.GET)
    @ShowLogger(info = "根据指定Vid获取单个录制视频MP4信息")
    public AjaxResult queryVideoByVidAPI(QueryVideoByVidRequest request) throws NoSuchAlgorithmException {
        QueryVideoByVidResponse response = queryVideoByVidAPI.process(request);
        return AjaxResult.success(response.getData());
    }

    @ApiOperation(value = "根据指定Vid获取播放视频token信息",notes = "根据指定Vid获取播放视频token信息")
    @RequestMapping(path = "/api/queryVideoTokenAPI",method = RequestMethod.GET)
    @ShowLogger(info = "根据指定Vid获取播放视频token信息")
    public AjaxResult queryVideoTokenAPI(QueryVideoTokenRequest request) throws NoSuchAlgorithmException {
        QueryVideoTokenResponse response = queryVideoTokenAPI.process(request);
        if(!StringUtils.isEmpty(response)&&!StringUtils.isEmpty(response.getApiToken())){
            return AjaxResult.success(response.getApiToken());
        }else{
            return AjaxResult.error("未获取到视频token信息");
        }
    }

    @ApiOperation(value = "根据指定Vid获取单个录制视频状态信息",notes = "根据指定Vid获取单个录制视频状态信息")
    @RequestMapping(path = "/api/getVideoOneInfoAPI",method = RequestMethod.GET)
    @ShowLogger(info = "根据指定Vid获取单个录制视频状态信息")
    public AjaxResult getVideoOneInfoAPI(GetVideoOneInfoRequest request) throws NoSuchAlgorithmException {
        GetVideoOneInfoResopnse response = getVideoOneInfoAPI.process(request);
        return AjaxResult.success(response.getVideoOneInfo());
    }

}