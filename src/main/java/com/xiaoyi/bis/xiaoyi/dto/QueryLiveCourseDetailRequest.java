package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取直播课程所属课节列表入参实体
 * @author CJ
 * @date 2019/11/21
 */
@ToString
@Setter
@Getter
public class QueryLiveCourseDetailRequest extends PageRequest {

    @ApiModelProperty(value = "课程编号",notes = "课程编号",required = true)
    private Integer courseId;

}
