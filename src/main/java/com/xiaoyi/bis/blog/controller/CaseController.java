package com.xiaoyi.bis.blog.controller;

import com.xiaoyi.bis.blog.domain.Event;
import com.xiaoyi.bis.blog.service.CaseService;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.common.exception.FebsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * KOL发布管理-案例
 * ping
 */
@Slf4j
@Validated
@Component
@RestController
@Api(value = "发布管理-案例", tags = {"KOL an li"})
@RequestMapping("/case")
public class CaseController {

    @Autowired
    private CaseService caseService;

    /**
     * KOL案例
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "KOL案例 & KOL详情往期案例页", notes = "根据url的use_id来获取案例")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public AjaxResult findById(@PathVariable String userId, int pageNum, int pageSize) {
        return AjaxResult.success(caseService.getById(userId, pageNum, pageSize));
    }

    /**
     * KOL案例删除
     *
     * @param evtId
     * @throws FebsException
     */
    @ApiOperation(value = "删除案例", notes = "根据url的id来删除案例")
    @RequestMapping(value = "/delect/{evtId}", method = RequestMethod.PUT)
    public AjaxResult deleteCase(@PathVariable String evtId) {
        try {
            caseService.updateEvtId(evtId);
            return AjaxResult.success("删除案例成功");
        } catch (Exception e) {
            return AjaxResult.success("删除案例失败");
        }
    }

    /**
     * 定时活动转成案例 - 每天12点触发
     */
    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void taskCase() {
        caseService.taskCase();
        log.info(">>>>>-定时执行活动转案例计划成功-<<<<<");
    }

    /**
     * 案例回显 单条
     */
    @ApiOperation(value = "案例回显 单条")
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public AjaxResult show(String evtId) {
        return AjaxResult.success(caseService.showById(evtId));
    }
}
