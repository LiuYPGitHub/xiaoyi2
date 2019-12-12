package com.xiaoyi.bis.xiaoyi.controller;

import com.xiaoyi.bis.xiaoyi.service.TimedTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.NoSuchAlgorithmException;

/**
 * 校翼定时任务接口
 * @author CJ
 * @date 2019/10/15
 */
@Api(tags = {"校翼机构端定时任务接口"})
@Configuration
@EnableScheduling   // 2.开启定时任务
@Slf4j
@Controller
public class TimedTaskController {

    @Autowired
    private TimedTaskService timedTaskService;

    @RequestMapping(path = "/xiaoyi/task/updateVideoStatus",method = RequestMethod.GET)
    @ApiOperation(value = "更新智能录播-精编版课节视频解码状态",notes = "更新智能录播-精编版课节视频解码状态")
    @Scheduled(cron = "0 0/3 * * * ?")
    @ResponseBody
    public String updateVideoStatus() throws NoSuchAlgorithmException {
        return timedTaskService.updateVideoStatus();
    }

    @RequestMapping(path = "/xiaoyi/task/updateVideoAllStatus",method = RequestMethod.GET)
    @ApiOperation(value = "更新智能录播-完整版课节视频解码状态",notes = "更新智能录播-完整版课节视频解码状态")
    @Scheduled(cron = "0 0/3 * * * ?")
    @ResponseBody
    public String updateVideoAllStatus() throws NoSuchAlgorithmException {
        return timedTaskService.updateVideoAllStatus();
    }

}
