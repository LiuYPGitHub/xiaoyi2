package com.xiaoyi.bis.xiaoyi.controller;

import com.xiaoyi.bis.common.annotation.ShowLogger;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.xiaoyi.videoApi.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.security.NoSuchAlgorithmException;

/**
 * 智能录播回调处理
 * (设置、开始录播回调暂时不用)
 * @author CJ
 * @date 2019/11/5
 */
@Api(tags = {"intelligentRecordCallBack"}, value = "校翼")
@Slf4j
@RestController
public class IntelligentRecordCallBackController {

    @Autowired
    private MergeRecordFileCallBackAPI mergeRecordFileCallBackAPI;
    @Autowired
    private FileForwardSaveCallBackAPI fileForwardSaveCallBackAPI;
    /*@Autowired
    private SettingLiveCallBackAPI settingLiveCallBackAPI;
    @Autowired
    private StartLiveCallBackAPI startLiveCallBackAPI;*/

    @ShowLogger(info = "合并直播录制文件回调函数")
    @RequestMapping(path = "${xiaoYiService.mergeRecordCallBack}",method = RequestMethod.GET)
    @ApiOperation(value = "合并直播录制文件回调函数",notes = "合并直播录制文件回调函数")
    public AjaxResult mergeRecordFileCallBackGET(MergeRecordFileCallBackRequest request) throws NoSuchAlgorithmException {
        MergeRecordFileCallBackResopnse resopnse = mergeRecordFileCallBackAPI.process(request);
        if(!StringUtils.isEmpty(resopnse)){
            boolean flag = resopnse.isFlag();
            if(flag){
                log.info("合并直播录制文件成功");
                return AjaxResult.success("合并直播录制文件成功");
            }else{
                log.error("合并直播录制文件失败");
                return AjaxResult.error("合并直播录制文件失败");
            }
        }else{
            log.error("合并直播录制文件失败");
            return AjaxResult.error("合并直播录制文件失败");
        }
    }

    @ShowLogger(info = "转存录制文件到点播回调函数")
    @RequestMapping(path = "${xiaoYiService.forwardRecordCallBack}",method = RequestMethod.GET)
    @ApiOperation(value = "转存录制文件到点播回调函数",notes = "转存录制文件到点播回调函数")
    public AjaxResult fileForwardSaveCallBack(FileForwardSaveCallBackRequest request) throws NoSuchAlgorithmException {
        fileForwardSaveCallBackAPI.process(request);
        return AjaxResult.success(true);
    }

    /*@ResponseBody
    @ShowLogger(info = "开始录制回调函数")
    @RequestMapping(path = "${service.startRecordCallBack}",method = RequestMethod.GET)
    @ApiOperation(value = "开始录制回调函数",notes = "开始录制回调函数")
    public AjaxResult fileForwardSaveCallBack(StartLiveCallBackRequest request){
        Integer result = startLiveCallBackAPI.process(request);
        return AjaxResult.success(result>0?true:false);
    }*/

    /*@ResponseBody
    @ShowLogger(info = "设置录制回调通知url")
    @RequestMapping(path = "/xiaoyi/settingLiveCallBack",method = RequestMethod.GET)
    @ApiOperation(value = "设置录制回调通知url",notes = "设置录制回调通知url")
    public AjaxResult settingLiveCallBack(SettingLiveCallBackRequest request){
        SettingLiveCallBackResponse response = settingLiveCallBackAPI.process(request);
        return AjaxResult.success(response);
    }*/

}
