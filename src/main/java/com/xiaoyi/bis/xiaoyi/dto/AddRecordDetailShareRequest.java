package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 添加智能录播分享课程入参实体
 * @author CJ
 * @date 2019/11/6
 */
@ToString
@Getter
@Setter
public class AddRecordDetailShareRequest implements Serializable {

    private static final long serialVersionUID = -6578030573901447100L;

    @ApiModelProperty(value = "分享的课程编号",notes = "分享的课程编号",required = true)
    private Integer courseId;
    @ApiModelProperty(value = "课节主键列表(为空:完整版 不为空:精编版)",notes = "id",required = false)
    private List<Integer> lessonIds;
    @ApiModelProperty(value = "学生主键列表",notes = "id",required = true)
    private List<Integer> studentIds;

}
