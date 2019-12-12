package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 检查智能录播-完整版分享入参实体
 *
 * @author CJ
 * @date 2019/11/7
 */
@ToString
@Getter
@Setter
public class CheckShareStudentAllRequest implements Serializable {

    private static final long serialVersionUID = -460775894254798804L;

    @ApiModelProperty(value = "分享的课程编号", notes = "分享的课程编号", required = true)
    private Integer courseId;
    @ApiModelProperty(value = "学生主键列表",notes = "id",required = true)
    private List<Integer> studentIds;

}
