package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 检查智能录播-分享实体
 * @author CJ
 * @date 2019/11/7
 */
@ToString
@Getter
@Setter
public class CheckShareStudentAll implements Serializable {

    private static final long serialVersionUID = 4773064729297542802L;
    @ApiModelProperty(value = "学生姓名",notes = "学生姓名")
    private String studName;
    @ApiModelProperty(value = "学生号码",notes = "学生号码")
    private String stuMobile;
    @ApiModelProperty(value = "检查结果标记",notes = "true:可以分享 false:不可分享")
    private boolean checkFlag;
    @ApiModelProperty(value = "检查结果信息",notes = "成功/失败信息")
    private String checkMess;

}
