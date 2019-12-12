package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 获取智能录播分享学生列表入参实体
 * @author CJ
 * @date 2019/11/6
 */
@ToString
@Setter
@Getter
public class QueryShareStudentRequest implements Serializable {

    private static final long serialVersionUID = -2445338711688182229L;

    @ApiModelProperty(value = "课程编号",notes = "课程编号",required = false)
    private Integer courseId;
    @ApiModelProperty(value = "姓名关键词",notes = "姓名关键词",required = false)
    private String nameKey;
    @ApiModelProperty(value = "学生电话",notes = "学生电话",required = false)
    private String mobile;

}
