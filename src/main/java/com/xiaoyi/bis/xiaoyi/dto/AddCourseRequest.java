package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author CJ
 * @date 2019/11/26
 */
@ToString
@Getter
@Setter
public class AddCourseRequest implements Serializable {

    private static final long serialVersionUID = 424472390601510240L;

    @ApiModelProperty(value = "当前登录用户编号",notes = "当前登录用户编号",required = true)
    private String userId;
    @ApiModelProperty(value = "课程名称[最大:50]",notes = "课程名称",required = true)
    private String courseName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "招生开始时间",notes = "招生开始时间",required = true)
    private Date enrollStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "招生结束时间",notes = "招生结束时间",required = true)
    private Date enrollEndDate;
    @ApiModelProperty(value = "课程分类 学科教育 素质教育 国际教育",notes = "课程分类 学科教育 素质教育 国际教育",required = true)
    private String classType;
    @ApiModelProperty(value = "课程标签[最大:255]",notes = "课程标签[最大:255 最小:1]",required = true)
    private List<String> classTags;
    @ApiModelProperty(value = "课程难度数1:1星 2:2星 以此类推,最高5星",notes = "1:1星 2:2星 以此类推,最高5星",required = true)
    private Integer classDifficultCount;
    @ApiModelProperty(value = "最大报名人数 [最大:11]",notes = "最大报名人数",required = true)
    private Integer maxCount;
    @ApiModelProperty(value = "课时数",notes = "课时数",required = true)
    private Integer classNum;
    @ApiModelProperty(value = "课程有效期 单位:天",notes = "课程有效期 单位:天",required = false)
    private String expirationDuration;
    @ApiModelProperty(value = "原价",notes = "原价",required = true)
    private BigDecimal priceCost;
    @ApiModelProperty(value = "售价",notes = "售价",required = true)
    private BigDecimal price;
    @ApiModelProperty(value = "课程适用学生",notes = "课程适用学生",required = true)
    private String classInfo;
    @ApiModelProperty(value = "讲义/教材",notes = "讲义/教材",required = true)
    private String textBook;
    @ApiModelProperty(value = "课程介绍",notes = "课程介绍",required = true)
    private String desc;
    @ApiModelProperty(value = "课程亮点",notes = "课程亮点",required = false)
    private String courseFeature;
    @ApiModelProperty(value = "课程亮点1",notes = "课程亮点1",required = true)
    private String courseFeature1;
    @ApiModelProperty(value = "课程亮点2",notes = "课程亮点2",required = false)
    private String courseFeature2;
    @ApiModelProperty(value = "课程亮点3",notes = "课程亮点3",required = false)
    private String courseFeature3;
    @ApiModelProperty(value = "学习内容",notes = "学习内容",required = true)
    private String courseContent;
    @ApiModelProperty(value = "学习目标",notes = "学习目标",required = true)
    private String courseObj;
    @ApiModelProperty(value = "教师电话",notes = "教师电话",required = true)
    private String teacherPhone;
    @ApiModelProperty(value = "教师姓名",notes = "教师姓名",required = true)
    private String teacherName;
    @ApiModelProperty(value = "教师简介",notes = "教师简介",required = true)
    private String teacherInfo;
    @ApiModelProperty(value = "是否显示课程评价 1展示,0不展示",notes = "是否显示课程评价 1展示,0不展示",required = false)
    private String showEvaluation;

}
