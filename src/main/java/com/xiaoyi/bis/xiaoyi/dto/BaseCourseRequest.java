package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 课程父级实体
 *
 * @author CJ
 * @date 2019/11/11
 */
@ToString
@Getter
@Setter
public class BaseCourseRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = -8245021565386471204L;

    @ApiModelProperty(value = "用户编号", notes = "用户编号", required = true)
    private String userId;
    @ApiModelProperty(value = "关键词", notes = "关键词")
    private String key;
    @ApiModelProperty(value = "排序方式 1:降序(由近到远),2:正序(由远到近) 3:所有", notes = "排序方式 1:降序(由近到远),2:正序(由远到近) 3:所有", required = true)
    private Integer orderType;

}
