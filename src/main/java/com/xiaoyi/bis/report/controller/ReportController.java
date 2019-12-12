package com.xiaoyi.bis.report.controller;

import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.report.dto.CourseIndexResponse;
import com.xiaoyi.bis.report.dto.DateResponse;
import com.xiaoyi.bis.report.dto.institutions6Response;
import com.xiaoyi.bis.report.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Report form
 */
@Slf4j
@Api(value = "校翼首页", tags = {"Home"})
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @ApiOperation(value = "顶部数据", notes = "课程,销售额,学生数")
    @GetMapping("/clas")
    public AjaxResult clas(String orgCode) {
        Map map = new HashMap();
        map.put("dshkc", reportService.coPending(orgCode));
        map.put("ysxkc", reportService.online(orgCode));
        map.put("wtgkc", reportService.notThrough(orgCode));
//      --------
        map.put("xszje", reportService.sumSales(orgCode));
        map.put("zyqdxszje", reportService.ownChannelSales(orgCode));
        map.put("tysxxszje", reportService.wings(orgCode));
//      --------
        map.put("ysfxszs", reportService.students(orgCode));
        map.put("zyqdxxzs", reportService.ownChannel(orgCode));
        map.put("tysxffxszs", reportService.wingsOwn(orgCode));
        return AjaxResult.success(map);
    }

    @ApiOperation(value = "热销课程TOP5")
    @GetMapping("/TOP5")
    @ResponseBody
    public AjaxResult TOP5(String orgCode) {
        try {
            CourseIndexResponse response = reportService.CourseName(orgCode);
            return AjaxResult.success(response);
        } catch (Exception e) {
            return AjaxResult.error("热销课程TOP5加载失败!" + e);
        }
    }

    @ApiOperation(value = "机构14日数据")
    @GetMapping("/class14")
    @ResponseBody
    public AjaxResult class14(String courseName, String orgCode) {
        try {
            DateResponse date = reportService.dateFormat(courseName, orgCode);
            return AjaxResult.success(date);
        } catch (Exception e) {
            return AjaxResult.error("机构14日数据加载失败!" + e);
        }
    }

    @ApiOperation(value = "近6月销售数据")
    @GetMapping("/institutions6")
    @ResponseBody
    public AjaxResult institutions6(String orgCode) {
        try {
            institutions6Response inst = reportService.institutions6(orgCode);
            return AjaxResult.success(inst);
        } catch (Exception e) {
            return AjaxResult.error("近6月销售数据加载失败!" + e);
        }
    }
}