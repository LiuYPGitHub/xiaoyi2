package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 修改直播课程入参实体
 * @author CJ
 * @date 2019/10/17
 */
@ToString
@Getter
@Setter
public class UpdateLiveCourseRequest extends AddCourseRequest implements Serializable {

    private static final long serialVersionUID = 7071663149827338830L;
    @ApiModelProperty(value = "课程编号",notes = "课程编号")
    private Integer courseId;
    //lessionId,lessonName,startDate,endDate
    @ApiModelProperty(value = "直播课节列表json数据",notes = "直播课节列表json数据",required = true)
    private String lessons;

}
