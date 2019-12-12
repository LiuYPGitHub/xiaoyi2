package com.xiaoyi.bis.xiaoyi.controller;

import com.xiaoyi.bis.common.annotation.ShowLogger;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.xiaoyi.domain.IntelligentRecord;
import com.xiaoyi.bis.xiaoyi.domain.RecordDetail;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import com.xiaoyi.bis.xiaoyi.dto.PlayerVideoResponse;
import com.xiaoyi.bis.xiaoyi.dto.QueryLiveCourseDetailRequest;
import com.xiaoyi.bis.xiaoyi.dto.getTokenResponse;
import com.xiaoyi.bis.xiaoyi.service.IntelligentRecordService;
import com.xiaoyi.bis.xiaoyi.service.RecordCourseService;
import com.xiaoyi.bis.xiaoyi.service.RecordDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取视频信息接口
 * @author CJ
 * @date 2019/11/8
 */
@Api(tags = {"videoCenter"},value = "校翼")
@RestController
@Validated
@Slf4j
public class VideoCenterController {

    @Autowired
    private IntelligentRecordService intelligentRecordService;
    @Autowired
    private RecordDetailService recordDetailService;
    @Autowired
    private RecordCourseService recordCourseService;

    @ApiOperation(value = "获取视频播放token值",notes = "获取视频播放token值")
    @ShowLogger(info = "获取视频播放token值")
    @RequestMapping(path = "/xiaoyi/player/getToken/{vid}",method = RequestMethod.GET)
    public AjaxResult getToken(@NotBlank @PathVariable String vid){
        String token = recordCourseService.getToken(vid);
        if(StringUtils.isEmpty(token)){
            return AjaxResult.error("未获取到视频相关token信息");
        }else{
            return AjaxResult.success(new getTokenResponse(token));
        }
    }

    @ApiOperation(value = "智能录播-获取视频列表",notes = "智能录播-获取视频列表")
    @ShowLogger(info = "智能录播-获取视频列表")
    @RequestMapping(path = "/xiaoyi/player/all",method = RequestMethod.GET)
    public AjaxResult playerAll(QueryLiveCourseDetailRequest request){
        request.processPageRequest();
        IntelligentRecord record = intelligentRecordService.queryIntelligentRecordById(request.getCourseId());
        if(!StringUtils.isEmpty(record)&&!StringUtils.isEmpty(record.getIsRevised())){
            if(record.getIsRevised() == 1){
                //完整版
                return AjaxResult.success(getPlayerVideoResponse(record));
            }else{
                //精编版
                PageResponse response = recordDetailService.queryRecordDetailByCourseId(request.getPageIndex(), request.getPageSize(), request.getCourseId());
                record.setResponse(response);
                record.setDetails((List<RecordDetail>) response.getList());
                return AjaxResult.success(getPlayerVideoResponse(record));
            }
        }else{
            log.error("未获取到相关数据，请稍后再试！");
            return AjaxResult.error("未获取到相关数据，请稍后再试！");
        }
    }

    public PlayerVideoResponse getPlayerVideoResponse(IntelligentRecord intelligentRecord){
        if(StringUtils.isEmpty(intelligentRecord)){
            PlayerVideoResponse response=new PlayerVideoResponse();
            response.setMess("暂未获取到相关视频");
            response.setCode("400");
            return response;
        }else{
            return checkCourse(intelligentRecord.getId(),intelligentRecord.getMp4(),intelligentRecord.getVid());
        }
    }

    public List<PlayerVideoResponse> getPlayerVideoResponse(List<RecordDetail> recordDetails){
        List<PlayerVideoResponse> responses=new ArrayList<>();
        for (RecordDetail recordDetail : recordDetails) {
            PlayerVideoResponse response=new PlayerVideoResponse();
            if(StringUtils.isEmpty(recordDetail)){
                response.setMess("暂未获取到相关视频");
                response.setCode("400");
            }else{
                response = checkCourse(recordDetail.getCourseId(), recordDetail.getMp4(), recordDetail.getVid());
            }
            responses.add(response);
        }
        return responses;
    }

    public PlayerVideoResponse checkCourse(Integer courseId,String mp4,String vid){
        PlayerVideoResponse response=new PlayerVideoResponse();
        response.setVid(vid);
        response.setCourseId(courseId);
        if(StringUtils.isEmpty(mp4)){
            response.setMess("暂未获取到相关视频");
            response.setCode("400");
        }else{
            response.setMess("已成功获取到视频");
            response.setCode("200");
            response.setMp4(mp4);
        }
        return response;
    }

}
