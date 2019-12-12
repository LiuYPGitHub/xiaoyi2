package com.xiaoyi.bis.blog.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.blog.domain.Course;
import com.xiaoyi.bis.blog.service.CourseService;
import com.xiaoyi.bis.common.controller.BaseController;
import com.xiaoyi.bis.common.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 名师发布管理-课程
 * ping
 */

@Slf4j
@Validated
@RestController
@Api(value = "发布管理-课程", tags = {"ming shi ke cheng"})
@RequestMapping("/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    /**
     * 名师课程 & 模糊查询
     *
     * @param userId
     * @param coTitle
     * @return
     */
    @ApiOperation(value = "名师课程 & 模糊查询 $ 名师详情课程页", notes = "根据url的id来获取课程")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public AjaxResult findById(@PathVariable String userId, String coTitle, int pageNum, int pageSize) {
        PageInfo<Course> aa =  courseService.getById(userId, coTitle, pageNum, pageSize);
        if(aa.getSize()==0){
            AjaxResult.success(null);
        }
        return AjaxResult.success(aa);
    }

    /**
     * 名师课程草稿箱 & 模糊查询
     *
     * @param userId
     * @param coTitle
     * @return
     */
    @ApiOperation(value = "名师课程草稿箱", notes = "根据url的id来获取课程")
    @RequestMapping(value = "/draft/{userId}", method = RequestMethod.GET)
    public AjaxResult findByIdDraft(@PathVariable String userId, String coTitle, int pageNum, int pageSize) {
        return AjaxResult.success(courseService.finddraftById(userId, coTitle, pageNum, pageSize));
    }


    /**
     * 删除课程 & 草稿箱删除
     *
     * @param coId
     */
    @ApiOperation(value = "删除课程 & 草稿箱删除", notes = "根据url的id来删除课程")
    @RequestMapping(value = "/delect/{coId}", method = RequestMethod.PUT)
    public AjaxResult deleteDynamic(@PathVariable String coId) {
        try {
            courseService.updateEvent(coId);
            return AjaxResult.success("删除动态成功");
        } catch (Exception e) {
            return AjaxResult.success("删除动态失败");
        }
    }

    /**
     * 名师草稿箱-课程发布
     *
     * @param course
     */
    @ApiOperation(value = "草稿箱课程发布", notes = "根据url的id来发布")
    @RequestMapping(value = "/release", method = RequestMethod.PUT)
    public AjaxResult deleteDraftDynamic(@RequestBody Course course) {
        try {
            courseService.releaseEvent(course);
            return AjaxResult.success("动态发布成功");
        } catch (Exception e) {
            return AjaxResult.success("动态发布失败");
        }
    }

    /**
     * 名师课程草稿箱-编辑-回显
     *
     * @param coId
     * @return
     */
    @ApiOperation(value = "名师课程草稿箱编辑回显-查看的课程链接在这里获取", notes = "草稿箱编辑回显")
    @GetMapping("/echo")
    public AjaxResult getReleaseEcho(@RequestParam String coId) {
        return AjaxResult.success(courseService.finddEchoById(coId));
    }

    /**
     * 名师课程-添加新课程
     *
     * @param course
     */
    @ApiOperation(value = "添加新课程", notes = "添加新课程")
    @PostMapping("/add")
    public AjaxResult addEvent(@RequestBody Course course) {
        try {
            courseService.addCourse(course);
            return AjaxResult.success("添加课程成功");
        } catch (Exception e) {
            return AjaxResult.success("添加课程失败");
        }
    }

    /**
     * 名师课程-草稿箱-修改
     *
     * @param course
     * @return
     */
    @ApiOperation(value = "名师课程-草稿箱-修改", notes = "修改")
    @PutMapping(value = "/echo/update")
    public AjaxResult updateEcho(@RequestBody Course course) {
        try {
            courseService.updateCourse(course);
            return AjaxResult.success("课程修改成功");
        } catch (Exception e) {
            return AjaxResult.success("课程修改失败");
        }
    }

}