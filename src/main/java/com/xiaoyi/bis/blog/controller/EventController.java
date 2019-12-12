package com.xiaoyi.bis.blog.controller;

import com.xiaoyi.bis.blog.domain.Event;
import com.xiaoyi.bis.blog.service.EventService;
import com.xiaoyi.bis.common.controller.BaseController;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.common.exception.FebsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * KOL发布管理-活动
 * ping
 */

@Slf4j
@Validated
@RestController
@Api(value = "发布管理-活动", tags = {"KOL huo dong"})
@RequestMapping("/event")
public class EventController extends BaseController {

    @Autowired
    private EventService eventService;

    /**
     * KOL活动 & 模糊查询 $ KOL活动详情
     *
     * @param userId
     * @param evtTitle
     * @return
     */
    @ApiOperation(value = "KOL活动 & 模糊查询 $ KOL活动详情页", notes = "根据url的id来获取活动")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public AjaxResult findById(@PathVariable String userId, String evtTitle, int pageNum, int pageSize) {
        return AjaxResult.success(eventService.getById(userId, evtTitle, pageNum, pageSize));
    }



    /**
     * KOL活动草稿箱 & 模糊查询
     *
     * @param userId
     * @param evtTitle
     * @return
     */
    @ApiOperation(value = "获取草稿箱活动", notes = "根据url的id来获取草稿箱动态")
    @RequestMapping(value = "/draft/{userId}", method = RequestMethod.GET)
    public AjaxResult findDraftById(@PathVariable String userId, String evtTitle, int pageNum, int pageSize) {
        return AjaxResult.success(eventService.finddraftById(userId, evtTitle, pageNum, pageSize));
    }

    /**
     * 删除活动
     *
     * @param evtId
     * @throws FebsException
     */
    @ApiOperation(value = "删除动态", notes = "根据url的id来删除动态")
    @RequestMapping(value = "/delect/{evtId}", method = RequestMethod.PUT)
    public AjaxResult deleteDynamic(@PathVariable String evtId) {
        try {
            eventService.updateEvent(evtId);
            return AjaxResult.success("删除动态成功");
        } catch (Exception e) {
            return AjaxResult.success("删除动态失败");
        }
    }

    /**
     * KOL活动草稿箱-活动发布
     *
     * @param event
     * @throws FebsException
     */
    @ApiOperation(value = "草稿箱活动发布", notes = "根据url的id来发布")
    @RequestMapping(value = "/release", method = RequestMethod.PUT)
    public AjaxResult deleteDraftDynamic(@RequestBody Event event) {
        try {
            eventService.releaseEvent(event);
            return AjaxResult.success("动态发布成功");
        } catch (Exception e) {
            return AjaxResult.success("动态发布失败");
        }
    }

    /**
     * KOL活动草稿箱-编辑-回显
     *
     * @param evtId
     * @return
     */
    @ApiOperation(value = "活动草稿箱编辑回显", notes = "草稿箱编辑回显")
    @GetMapping("/echo")
    public AjaxResult getReleaseEcho(@RequestParam String evtId) {
        return AjaxResult.success(eventService.finddEchoById(evtId));
    }

    /**
     * KOL活动草稿箱-编辑-回显
     *
     * @param evtId
     * @return
     */
    @ApiOperation(value = "活动详情回显")
    @GetMapping("/echo/details")
    public AjaxResult getRelease(@RequestParam String evtId) {
        return AjaxResult.success(eventService.finddEchoBy(evtId));
    }

    /**
     * KOL活动-添加新活动
     *
     * @param event
     */
    @ApiOperation(value = "添加新活动", notes = "添加新活动")
    @PostMapping("/add")
    public AjaxResult addEvent(@RequestBody Event event) {
        try {
            eventService.addEvent(event);
            return AjaxResult.success("添加活动成功");
        } catch (Exception e) {
            return AjaxResult.error("添加活动失败");
        }
    }

    /**
     * KOL活动-草稿箱-编辑
     *
     * @param event
     * @return
     */
    @ApiOperation(value = "KOL活动-草稿箱-编辑", notes = "根据url的id来编辑")
    @PutMapping(value = "/echo/update")
    public AjaxResult updateEcho(@RequestBody Event event) {
        try {
            eventService.updateEcho(event);
            return AjaxResult.success("活动修改成功");
        } catch (Exception e) {
            return AjaxResult.success("活动修改失败");
        }
    }


}