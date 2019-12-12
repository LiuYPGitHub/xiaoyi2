package com.xiaoyi.bis.blog.controller;

import com.xiaoyi.bis.blog.controller.bean.DynamicBean;
import com.xiaoyi.bis.blog.controller.bean.ReqDynamic;
import com.xiaoyi.bis.blog.service.DynamicTeacherService;
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
 * 名师发布管理-动态
 * ping
 */

@Slf4j
@Validated
@RestController
@Api(value = "发布管理-动态", tags = {"ming shi dong tai"})
@RequestMapping("/teacher")
public class DynamicTeacherController extends BaseController {

    @Autowired
    private DynamicTeacherService dynamicTeacherService;
    private @Autowired
    IndexService indexService;
    private @Autowired
    Mapper mapper;
    private String message;

    /**
     * 名师动态 & 模糊查询
     *
     * @param userId
     * @param dynaContent
     * @return
     */
    @ApiOperation(value = "名师动态 & 模糊查询 & 名师详情动态页", notes = "根据url的id来获取动态")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public AjaxResult findById(@PathVariable String userId, String dynaContent, int pageNum, int pageSize) {
        return AjaxResult.success(dynamicTeacherService.getById(userId, dynaContent, pageNum, pageSize));
    }

    /**
     * 名师详情-最新动态页
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "名师详情-最新动态页", notes = "根据url的user_id来获取动态")
    @RequestMapping(value = "/latest/{userId}", method = RequestMethod.GET)
    public AjaxResult LatestNews(@PathVariable String userId, int pageNum, int pageSize) {
        return AjaxResult.success(dynamicTeacherService.LatestNews(userId, pageNum, pageSize));
    }

    /**
     * 名师动态草稿箱 & 模糊查询
     *
     * @param userId
     * @param dynaContent
     * @return
     */
    @ApiOperation(value = "获取草稿箱动态", notes = "根据url的id来获取草稿箱动态")
    @RequestMapping(value = "/draft/{userId}", method = RequestMethod.GET)
    public AjaxResult findDraftById(@PathVariable String userId, String dynaContent, int pageNum, int pageSize) {
        return AjaxResult.success(dynamicTeacherService.finddraftById(userId, dynaContent, pageNum, pageSize));
    }

    /**
     * 删除动态 & 删除草稿箱动态
     *
     * @param dynaId
     * @throws FebsException
     */
    @ApiOperation(value = "删除动态 & 删除草稿箱", notes = "根据url的id来删除动态")
    @RequestMapping(value = "/delect/{dynaId}", method = RequestMethod.PUT)
    public AjaxResult deleteDynamic(@PathVariable String dynaId) {
        try {
            dynamicTeacherService.updateDynamic(dynaId);
            //首页动态数据
            indexService.updateDynamicIndex(dynaId);
            return AjaxResult.success("删除动态成功");
        } catch (Exception e) {
            return AjaxResult.success("删除动态失败");
        }
    }

    /**
     * 草稿箱发布
     *
     * @param dynaId
     * @throws FebsException
     */
    @ApiOperation(value = "草稿箱发布", notes = "根据url的id来发布")
    @RequestMapping(value = "/release/{dynaId}", method = RequestMethod.PUT)
    public AjaxResult deleteDraftDynamic(@PathVariable String dynaId) {
        try {
            dynamicTeacherService.releaseDynamic(dynaId);
            //首页动态数据
            indexService.releaseDynamic(dynaId);
            return AjaxResult.success("动态发布成功");
        } catch (Exception e) {
            return AjaxResult.success("动态发布失败");
        }
    }

    /**
     * 名师动态草稿箱-编辑-回显
     *
     * @param dynaId
     * @return
     */
    @ApiOperation(value = "名师动态草稿箱编辑回显", notes = "名师草稿箱编辑回显")
    @GetMapping("/dyna/show")
    public AjaxResult getReleaseEcho(@RequestParam String dynaId) {
        return AjaxResult.success(dynamicTeacherService.getDynamicById(dynaId));
    }

    /**
     * 名师添加新动态 & 修改动态
     *
     * @param reqDynamic
     * @throws FebsException
     */
    @ApiOperation(value = "名师新增动态", notes = "名师添加新动态")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResult addDynamic(@RequestBody ReqDynamic reqDynamic) {
        try {
            final DynamicBean dynamicBean = mapper.map(reqDynamic, DynamicBean.class);
            final String dynamicId = dynamicTeacherService.saveTeacher(dynamicBean);
            // 首页动态
            indexService.saveOrUpdateDynamicIndex(reqDynamic.getUserId(), dynamicId, reqDynamic.getDynaContent(), String.valueOf(reqDynamic.getDynaStatus()), "1");
            return AjaxResult.success("成功");
        } catch (Exception e) {
            return AjaxResult.error("失败");
        }
    }
}
//    @ApiOperation(value = "获取该用户动态", notes = "根据url的id来获取该用户的所有动态")
//    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
//    public AjaxResult kolList(@PathVariable Long userId) {
//        dynamic list = dynamicService.selectKolList(userId);
//        return AjaxResult.success(list);
//    }