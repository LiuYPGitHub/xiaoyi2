package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 查询智能录播课程视频信息入参实体
 * @author CJ
 * @date 2019/11/20
 */
@ToString
@Getter
@Setter
public class QueryIntelligentRecordVideoRequest implements Serializable {

    private static final long serialVersionUID = 7906697271248230078L;

    @ApiModelProperty(value = "用户编号",notes = "用户编号",required = true)
    private String userId;
    @ApiModelProperty(value = "课程名称",notes = "课程名称")
    private String courseName;
    @ApiModelProperty(value = "教师姓名",notes = "教师姓名")
    private String teacherName;
    @ApiModelProperty(value = "视频版本 1:完整版 2:精编版",notes = "视频版本 1:完整版 2:精编版")
    private Integer videoType;

}
