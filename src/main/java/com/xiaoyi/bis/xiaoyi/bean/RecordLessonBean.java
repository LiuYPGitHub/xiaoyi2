package com.xiaoyi.bis.xiaoyi.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 添加录播课程所属课节实体
 * @author CJ
 * @date 2019/11/20
 */
@ToString
@Getter
@Setter
public class RecordLessonBean extends LessonBean implements Serializable {

    private static final long serialVersionUID = 8829339556825446228L;

    @ApiModelProperty(value = "课节编号",notes = "课节编号",required = true)
    private Integer lessonId;

}

