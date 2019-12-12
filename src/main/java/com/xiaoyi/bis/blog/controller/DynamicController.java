package com.xiaoyi.bis.blog.controller;

import com.xiaoyi.bis.blog.controller.bean.DynamicBean;
import com.xiaoyi.bis.blog.controller.bean.ReqDynamic;
import com.xiaoyi.bis.blog.domain.Dynamic;
import com.xiaoyi.bis.blog.service.DynamicService;
import com.xiaoyi.bis.common.controller.BaseController;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.common.exception.FebsException;
import com.xiaoyi.bis.user.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * KOL发布管理-动态
 * ping
 */
@Slf4j
@Validated
@RestController
@Api(value = "发布管理-动态", tags = {"KOL dong tai"})
@RequestMapping("/dyna")
public class DynamicController extends BaseController {

    @Autowired
    private DynamicService dynamicService;
    private @Autowired
    IndexService indexService;

    private @Autowired
    Mapper mapper;
    private String message;



    /**
     * KOL动态 & 模糊查询
     *
     * @param userId
     * @param dynaContent
     * @return
     */
    @ApiOperation(value = "KOL动态 & 模糊查询", notes = "根据url的id来获取动态")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public AjaxResult findById(@PathVariable String userId, String dynaContent, int pageNum, int pageSize) {
        return AjaxResult.success(dynamicService.getById(userId, dynaContent, pageNum, pageSize));
    }

    /**
     * KOL详情-最新动态页
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "KOL详情-最新动态页")
    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    public AjaxResult LatestNews(String userId, int pageNum, int pageSize) {
        return AjaxResult.success(dynamicService.LatestNews(userId,pageNum,pageSize));
    }

    /**
     * KOL动态草稿箱 & 模糊查询
     *
     * @param userId
     * @param dynaContent
     * @return
     */
    @ApiOperation(value = "获取草稿箱动态", notes = "根据url的id来获取草稿箱动态")
    @RequestMapping(value = "/draft/{userId}", method = RequestMethod.GET)
    public AjaxResult findDraftById(@PathVariable String userId, String dynaContent, int pageNum, int pageSize) {
        return AjaxResult.success(dynamicService.finddraftById(userId, dynaContent, pageNum, pageSize));
    }

    /**
     * KOL添加新动态
     *
     * @param reqDynamic
     * @throws FebsException
     */
    @ApiOperation(value = "新增动态-立即发布", notes = "随便一发,添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResult addDynamic(@RequestBody ReqDynamic reqDynamic) {
        try {
            final DynamicBean dynamicBean = mapper.map(reqDynamic, DynamicBean.class);
            final String dynamicId = dynamicService.saveDynamic(dynamicBean);
            // 首页动态
            indexService.saveOrUpdateDynamicIndex(reqDynamic.getUserId(), dynamicId, reqDynamic.getDynaContent(), String.valueOf(reqDynamic.getDynaStatus()), "0");           return AjaxResult.success("成功");
        } catch (Exception e) {
            return AjaxResult.error("失败");
        }
    }

    /**
     * 删除动态
     *
     * @param dynaId
     * @throws FebsException
     */
    @ApiOperation(value = "删除动态", notes = "根据url的id来删除动态")
    @RequestMapping(value = "/delect/{dynaId}", method = RequestMethod.PUT)
    public AjaxResult deleteDynamic(@PathVariable String dynaId) {
        try {
            dynamicService.updateDynamic(dynaId);
            //首页动态
            indexService.updateDynamicIndex(dynaId);
            return AjaxResult.success("删除动态成功");
        } catch (Exception e) {
            return AjaxResult.success("删除动态失败");
        }
    }

    /**
     * 草稿箱发布
     *
     * @param dynamic
     * @throws FebsException
     */
    @ApiOperation(value = "共用草稿箱发布")
    @RequestMapping(value = "/release/", method = RequestMethod.PUT)
    public AjaxResult deleteDraftDynamic(@RequestBody Dynamic dynamic) {
        try {
            dynamicService.releaseDynamic(dynamic);
            //首页动态
            indexService.releaseDynamic(dynamic.getDynaId());
            return AjaxResult.success("动态发布成功");
        } catch (Exception e) {
            return AjaxResult.success("动态发布失败");
        }
    }

    /**
     * KOL动态草稿箱-编辑-回显
     *
     * @param dynaId
     * @return
     */
    @ApiOperation(value = "动态草稿箱编辑回显", notes = "草稿箱编辑回显")
    @GetMapping("/dyna/show")
    public AjaxResult getReleaseEcho(@RequestParam String dynaId) {
        return AjaxResult.success(dynamicService.getDynamicById(dynaId));
    }
}