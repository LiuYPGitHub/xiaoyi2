package com.xiaoyi.bis.xiaoyi.controller;

import com.xiaoyi.bis.common.annotation.ShowLogger;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.xiaoyi.domain.IntelligentRecord;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetail;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import com.xiaoyi.bis.xiaoyi.dto.QueryLiveCourseDetailRequest;
import com.xiaoyi.bis.xiaoyi.service.IntelligentRecordService;
import com.xiaoyi.bis.xiaoyi.service.RecordDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 智能录播课节详情
 * @author CJ
 * @date 2019/11/6
 */
@Api(tags = {"intelligentRecord"}, value = "校翼")
@Slf4j
@RestController
public class IntelligentRecordDetailController {

    @Autowired
    private IntelligentRecordService intelligentRecordService;
    @Autowired
    private RecordDetailService recordDetailService;

    @ShowLogger(info = "获取智能录播课节详情")
    @ApiOperation(value = "获取智能录播课节详情", notes = "获取智能录播课节详情")
    @RequestMapping(path = "/xiaoyi/intelligentRecordCourseDetail", method = RequestMethod.GET)
    public AjaxResult addintelligentRecordCourseDetail(QueryLiveCourseDetailRequest request){
        request.processPageRequest();
        IntelligentRecord record = intelligentRecordService.queryIntelligentRecordById(request.getCourseId());
        if(!StringUtils.isEmpty(record)){
            PageResponse response = recordDetailService.queryRecordDetailByCourseId(request.getPageIndex(), request.getPageSize(), request.getCourseId());
//            record.setResponse(response);
            record.setDetails((List<RecordDetail>) response.getList());
            return AjaxResult.success(record);
        }else{
            log.info("该智能录播课程不存在");
            return AjaxResult.error("该智能录播课程不存在");
        }
    }

}