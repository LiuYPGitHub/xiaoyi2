package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/12
 */
@ToString
@Getter
@Setter
public class AddLiveCourseRequest implements Serializable {

    private static final long serialVersionUID = 3272870340107780216L;
    @ApiModelProperty(value = "课程名称",notes = "课程名称",required = true)
    private String name;
    @ApiModelProperty(value = "图片URL链接,课程封面",notes = "图片URL链接,课程封面 http://94.191.62.87:81/images/1.jpg",required = true)
    private String coverImgLink;
    @ApiModelProperty(value = "招生开始时间",notes = "招生开始时间",required = true)
    private String enrollStartDate;
    @ApiModelProperty(value = "招生结束时间",notes = "招生结束时间",required = true)
    private String enrollEndDate;
    @ApiModelProperty(value = "课时",notes = "课时",required = true)
    private String classHour;
    @ApiModelProperty(value = "课程类型（枚举）：学科教育 素质教育 国际教育",notes = "课程类型",required = true)
    private String classType;
    @ApiModelProperty(value = "原价",notes = "原价",required = true)
    private String primeCost;
    @ApiModelProperty(value = "售价",notes = "售价",required = true)
    private String cost;
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
    @ApiModelProperty(value = "最大值",notes = "最大值",required = true)
    private String maxNum;
    @ApiModelProperty(value = "是否显示课程评价 1展示,0不展示",notes = "是否显示课程评价 1展示,0不展示",required = true)
    private String showEvaluation;
    @ApiModelProperty(value = "课程有效期(单位:天)",notes = "课程有效期(单位:天)",required = false)
    private String expirationDuration;

}
