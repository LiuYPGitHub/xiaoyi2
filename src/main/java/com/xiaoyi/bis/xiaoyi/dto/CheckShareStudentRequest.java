package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 检查智能录播-精编版分享入参实体
 * @author CJ
 * @date 2019/11/7
 */
@ToString
@Getter
@Setter
public class CheckShareStudentRequest implements Serializable {

    private static final long serialVersionUID = -1818129541347567188L;

    @ApiModelProperty(value = "分享的课程编号",notes = "分享的课程编号",required = true)
    private Integer courseId;
    /*@ApiModelProperty(value = "课节主键列表",notes = "id",required = true)
    private List<Integer> lessonIds;*/
    @ApiModelProperty(value = "分享學生信息列表",notes = "分享學生信息列表",required = true)
    private List<Integer> studentIds;
    //private List<Stud> students;

}
