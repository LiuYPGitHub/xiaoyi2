package com.xiaoyi.bis.xiaoyi.xiaoyiApi.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 课程详情实体类
 * @author CJ
 * @date 2019/10/12
 */
@ToString
@Getter
@Setter
public class APICourseDetail implements Serializable {

    private static final long serialVersionUID = 7148099844263326431L;
    @ApiModelProperty(value = "课程名称",notes = "课程名称")
    private String name;
    @ApiModelProperty(value = "课程代码",notes = "课程代码")
    private String code;
    @ApiModelProperty(value = "链接，课程封面",notes = "链接，课程封面")
    private String coverImgLink;
    @ApiModelProperty(value = "Vid，保利威视，课程封面视频",notes = "Vid，保利威视，课程封面视频")
    private String coverVid;
    @ApiModelProperty(value = "课程模板ID",notes = "课程模板ID")
    private String classroomSettingId;
    @ApiModelProperty(value = "招生开始时间yyyyMMddHHmmss",notes = "招生开始时间yyyyMMddHHmmss")
    private String enrollStartDate;
    @ApiModelProperty(value = "招生结束时间yyyyMMddHHmmss 1展示课程评价,0不展示课程评价",notes = "招生结束时间yyyyMMddHHmmss")
    private String enrollEndDate;
    @ApiModelProperty(value = "是否展示课程评价 1展示,0 不展示",notes = "是否展示课程评价 1展示,0 不展示")
    private Integer showEvaluation;
    @ApiModelProperty(value = "课程有效期",notes = "课程有效期")
    private Integer expirationDuration;
    @ApiModelProperty(value = "课时",notes = "课时")
    private BigDecimal classHour;
    @ApiModelProperty(value = "学科教育 素质教育 国际教育",notes = "学科教育 素质教育 国际教育")
    private String classType;
    @ApiModelProperty(value = "最大值",notes = "最大值")
    private String maxNum;
    //@ApiModelProperty(value = "原价",notes = "原价")
    //private BigDecimal primeCost;
    @ApiModelProperty(value = "售价",notes = "售价")
    private BigDecimal cost;
    @ApiModelProperty(value = "是否上架",notes = "是否上架")
    private Integer isSearch;
    @ApiModelProperty(value = "课程主图（富文本）",notes = "课程主图（富文本）")
    private String courseIntroduceImg;
    @ApiModelProperty(value = "课程简介（富文本）",notes = "课程简介（富文本）")
    private String courseInformation;
    @ApiModelProperty(value = "师资介绍（富文本）",notes = "师资介绍（富文本）")
    private String courseTeachersHighlight;
    @ApiModelProperty(value = "课程亮点（富文本）",notes = "课程亮点（富文本）")
    private String courseHighlight;
    @ApiModelProperty(value = "学习内容（富文本）",notes = "学习内容（富文本）")
    private String courseLearningContent;
    @ApiModelProperty(value = "观看方式（富文本）",notes = "观看方式（富文本）")
    private String courseObservationStyle;
    @ApiModelProperty(value = "课程咨询（富文本）",notes = "课程咨询（富文本）")
    private String courseConsultant;
    @ApiModelProperty(value = "温馨提示（富文本）",notes = "温馨提示（富文本）")
    private String courseWarmPrompt;
    @ApiModelProperty(value = "课节列表",notes = "课节列表")
    private List<APILessons> lessonsList;

}
