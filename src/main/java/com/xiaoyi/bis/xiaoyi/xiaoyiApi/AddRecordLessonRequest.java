package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/31
 */
@ToString
@Getter
@Setter
public class AddRecordLessonRequest implements Serializable {

    private static final long serialVersionUID = -8495781172715993608L;

    @ApiModelProperty(value = "教师编号",notes = "教师编号",required = true)
    private String teacherId;
    @ApiModelProperty(value = "课程编号",notes = "课程编号",required = true)
    private String classId;
    @ApiModelProperty(value = "课节标题",notes = "课节标题",required = true)
    private String title;
    @ApiModelProperty(value = "课节图片",notes = "课节图片",required = true)
    private String coverImgLink;
    @ApiModelProperty(value = "保利威视频编号",notes = "保利威视频编号",required = false)
    private String vid;
    @ApiModelProperty(value = "课节节时",notes = "课节节时",required = true)
    public String recordHour;
    @ApiModelProperty(value = "是否允许试看(0否1是)",notes = "是否允许试看(0否1是)",required = true)
    public String allowTaste;
    @ApiModelProperty(value = "视频的时长",notes = "视频的时长00:00:00",required = true)
    public String videoDuration;

}
