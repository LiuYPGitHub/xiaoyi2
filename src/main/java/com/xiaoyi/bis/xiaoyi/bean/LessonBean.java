package com.xiaoyi.bis.xiaoyi.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 添加录播课程所属课节实体
 * @author CJ
 * @date 2019/11/20
 */
@ToString
@Getter
@Setter
public class LessonBean implements Serializable {

    private static final long serialVersionUID = 2933653241255182176L;

    @ApiModelProperty(value = "课节名称", notes = "课节名称", required = true)
    private String lessonName;
    @ApiModelProperty(value = "课节描述", notes = "课节描述", required = false)
    private String lessonDesc;

}

