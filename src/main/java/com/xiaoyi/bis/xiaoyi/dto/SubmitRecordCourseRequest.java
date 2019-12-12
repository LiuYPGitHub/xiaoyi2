package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 添加录播课程入参实体
 * @author CJ
 * @date 2019/11/1
 */
@ToString
@Getter
@Setter
public class SubmitRecordCourseRequest implements Serializable {

    private static final long serialVersionUID = -9028797212094630077L;
    @ApiModelProperty(value = "课时数",notes = "课时数",required = true)
    private Integer classNum;
    @ApiModelProperty(value = "课程编号",notes = "课程编号",required = false)
    private Integer courseId;
    @ApiModelProperty(value = "课程分类 学科教育 素质教育 国际教育",notes = "课程分类 学科教育 素质教育 国际教育",required = true)
    private String classType;
    @ApiModelProperty(value = "课程名称",notes = "课程名称",required = true)
    private String courseName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "招生开始时间",notes = "招生开始时间",required = true)
    private Date enrollStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "招生结束时间",notes = "招生结束时间",required = true)
    private Date enrollEndDate;
    @ApiModelProperty(value = "课程有效期 单位:天",notes = "课程有效期 单位:天",required = false)
    private String expirationDuration;
    @ApiModelProperty(value = "提交的课节编号",notes = "提交的课节编号",required = true)
    private List<String> lessionIds;

}
