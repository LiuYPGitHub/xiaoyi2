package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：分享学生
 * @Author：KK
 * @Date：2019/11/29 13:48
 */
@Data
@ToString
public class ReqShareStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分享的课程编号", notes = "分享的课程编号", required = true)
    private Integer courseId;
    @ApiModelProperty(value = "学生主键列表", notes = "id", required = true)
    private List<ReqShareStudent.ReqStudent> students;

    @Data
    public static class ReqStudent {
        private String stuId;
        @ApiModelProperty(value = "学生姓名", notes = "学生姓名")
        private String studName;
        @ApiModelProperty(value = "学生号码", notes = "学生号码")
        private String stuMobile;

    }

}