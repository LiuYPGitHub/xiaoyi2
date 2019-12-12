package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 获取直播课程列表入参实体
 * @author CJ
 * @date 2019/10/16
 */
@ToString
@Setter
@Getter
public class QueryLiveCourseRequest extends BaseCourseRequest implements Serializable {

    private static final long serialVersionUID = -1793290688234735048L;
    @ApiModelProperty(value = "课程状态",notes = "-1:所有 0:未提交,1:审核中,2:未通过,4:已通过")
    private Integer status;

}
