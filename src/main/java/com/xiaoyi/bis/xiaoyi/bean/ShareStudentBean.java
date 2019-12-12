package com.xiaoyi.bis.xiaoyi.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class ShareStudentBean implements Serializable {

    private static final long serialVersionUID = -3359853367722570072L;

    @ApiModelProperty(value = "添加的学生编号",notes = "添加的学生编号",required = false)
    private Integer addid;
    @ApiModelProperty(value = "学生编号",notes = "学生编号",required = false)
    private Integer id;
    @ApiModelProperty(value = "学生姓名",notes = "学生姓名",required = true)
    private String name;
    @ApiModelProperty(value = "手机号码",notes = "手机号码",required = true)
    private String mobile;
    @ApiModelProperty(value = "所属课程编号",notes = "所属课程编号",required = false)
    private Integer courseId;

}
