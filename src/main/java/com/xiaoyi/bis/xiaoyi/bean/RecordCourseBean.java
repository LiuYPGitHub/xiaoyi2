package com.xiaoyi.bis.xiaoyi.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/11/25
 */
@ToString
@Getter
@Setter
public class RecordCourseBean extends LessonBean implements Serializable {

    private static final long serialVersionUID = 7385284298237213207L;
    //修改课节必填
    @ApiModelProperty(value = "课节主键编号",notes = "课节主键编号",required = false)
    private Integer lessonId;
    @ApiModelProperty(value = "课节编号",notes = "课节编号",required = true)
    private Integer courseId;

}
