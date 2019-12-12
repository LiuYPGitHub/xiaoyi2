package com.xiaoyi.bis.xiaoyi.xiaoyiApi.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 课节实体类
 * @author CJ
 * @date 2019/10/12
 */
@ToString
@Getter
@Setter
public class APILessons implements Serializable {

    private static final long serialVersionUID = -647004115366759327L;
    @ApiModelProperty(value = "教师编号",notes = "教师编号")
    private String teacherId;
    @ApiModelProperty(value = "课程编号",notes = "课程编号")
    private String classId;
    @ApiModelProperty(value = "直播内容/课节标题",notes = "直播内容/课节标题")
    private String liveContent;
    @ApiModelProperty(value = "课节code，同一课程内不能重复",notes = "课节code，同一课程内不能重复")
    private String code;
    @ApiModelProperty(value = "直播开始时间 yyyyMMddHHmmss",notes = "直播开始时间 yyyyMMddHHmmss")
    private String liveStartDate;
    @ApiModelProperty(value = "直播结束时间",notes = "直播结束时间")
    private String liveEndDate;
    @ApiModelProperty(value = "课节时长",notes = "课节时长")
    private BigDecimal lessonHour;
    @ApiModelProperty(value = "1-15的数值",notes = "1-15的数值")
    private Integer liveManNumber;
    @ApiModelProperty(value = "是否高清(0 非高清 1 高清) 高清目前只支持1V1或者1V6",notes = "是否高清")
    private String isHd;
    @ApiModelProperty(value = "是否自动上台(0 自动上台 1 非自动上台)",notes = "是否自动上台(0 自动上台 1 非自动上台)")
    private String isAutoOnstage;

}
