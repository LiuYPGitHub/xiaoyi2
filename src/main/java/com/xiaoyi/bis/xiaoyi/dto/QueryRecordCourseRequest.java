package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 获取录播课程列表入参实体
 * @author CJ
 * @date 2019/11/12
 */
@ToString
@Getter
@Setter
public class QueryRecordCourseRequest extends BaseCourseRequest implements Serializable {

    @ApiModelProperty(value = "课程添翼申学状态 1:线上 2:线下 默认:所有",notes = "1:线上 2:线下 默认:所有",required = false)
    private Integer courseIsOnline;
    @ApiModelProperty(value = "课程状态 -1:默认所有 0:未提交,1:审核中,2:未通过,4:已通过",notes = "课程状态 -1:默认所有 0:未提交,1:审核中,2:未通过,4:已通过",required = false)
    private Integer status;
    @ApiModelProperty(value = "所属机构",notes = "所属机构",required = false)
    private String ordCode;


}
