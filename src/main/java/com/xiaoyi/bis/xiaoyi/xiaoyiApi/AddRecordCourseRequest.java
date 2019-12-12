package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/31
 */
@ToString
@Getter
@Setter
public class AddRecordCourseRequest implements Serializable {

    private static final long serialVersionUID = 5922518168035571594L;

    @ApiModelProperty(value = "课程名称",notes = "课程名称",required = true)
    private String name;
    @ApiModelProperty(value = "课程封面",notes = "课程封面 http://94.191.62.87:81/images/1.jpg",required = true)
    private String coverImgLink;
    @ApiModelProperty(value = "招生开始时间",notes = "招生开始时间",required = true)
    private String enrollStartDate;
    @ApiModelProperty(value = "招生结束时间",notes = "招生结束时间",required = true)
    private String enrollEndDate;
    @ApiModelProperty(value = "课程有效期(单位:天)",notes = "课程有效期(单位:天)",required = false)
    private String expirationDuration;
    @ApiModelProperty(value = "课时",notes = "课时",required = true)
    private String classHour;
    @ApiModelProperty(value = "课程类型（枚举）：学科教育 素质教育 国际教育",notes = "课程类型",required = true)
    private String classType;
    @ApiModelProperty(value = "保利威视频编号",notes = "保利威视频编号",required = false)
    private String coverVid;
    @ApiModelProperty(value = "原价",notes = "原价",required = true)
    private String primeCost;
    @ApiModelProperty(value = "售价",notes = "售价",required = true)
    private String cost;
    @ApiModelProperty(value = "总时长",notes = "总时长",required = true)
    private String totalTime;
    @ApiModelProperty(value = "录播课类型",notes = "1线上 2 线下",required = true)
    private String recordType;
    @ApiModelProperty(value = "最大值",notes = "最大值",required = true)
    private String maxNum;
    @ApiModelProperty(value = "课程主图（富文本）",notes = "课程主图（富文本）",required = true)
    private String courseIntroduceImg;
    @ApiModelProperty(value = "课程简介（富文本）",notes = "课程简介（富文本）",required = true)
    private String courseInformation;
    @ApiModelProperty(value = "师资介绍（富文本）",notes = "师资介绍（富文本）",required = true)
    private String courseTeachersHighlight;
    @ApiModelProperty(value = "课程亮点（富文本）",notes = "课程亮点（富文本）",required = true)
    private String courseHighlight;
    @ApiModelProperty(value = "学习内容（富文本）",notes = "学习内容（富文本）",required = true)
    private String courseLearningContent;
    @ApiModelProperty(value = "观看方式（富文本）",notes = "观看方式（富文本）",required = true)
    private String courseObservationStyle;
    @ApiModelProperty(value = "课程咨询（富文本）",notes = "课程咨询（富文本）",required = true)
    private String courseConsultant;
    @ApiModelProperty(value = "温馨提示（富文本）",notes = "温馨提示（富文本）",required = true)
    private String courseWarmPrompt;
    @ApiModelProperty(value = "机构名称",notes = "机构名称",required = true)
    private String siteName;

}
