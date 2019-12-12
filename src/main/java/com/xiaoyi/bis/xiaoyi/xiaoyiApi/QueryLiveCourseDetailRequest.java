package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/12
 */
@ToString
@Getter
@Setter
public class QueryLiveCourseDetailRequest implements Serializable {

    private static final long serialVersionUID = 1124027246880704671L;

    @ApiModelProperty(value = "课程编号",notes = "课程编号",required = true)
    private String classId;

}
