package com.xiaoyi.bis.xiaoyi.videoApi.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 录播视频信息
 * @author CJ
 * @date 2019/10/23
 */
@ToString
@Getter
@Setter
public class APIRecordFile implements Serializable {

    private static final long serialVersionUID = 3340656472534914822L;

    @ApiModelProperty(value = "用户编号",notes = "用户编号",required = true)
    private String userId;
    @ApiModelProperty(value = "频道编号",notes = "频道编号",required = true)
    private String channelId;
    @ApiModelProperty(value = "录制文件名称",notes = "录制文件名称",required = true)
    private String fileName;
    @ApiModelProperty(value = "文件编号",notes = "文件编号",required = true)
    private String fileId;
    @ApiModelProperty(value = "录制文件地址",notes = "录制文件地址",required = true)
    private String url;
    @ApiModelProperty(value = "创建视频时间",notes = "创建视频时间",required = true)
    private String createdTime;
    @ApiModelProperty(value = "开始录制时间",notes = "开始录制时间",required = true)
    private long startTime;
    @ApiModelProperty(value = "结束录制时间",notes = "结束录制时间",required = true)
    private long endTime;
    @ApiModelProperty(value = "文件大小",notes = "文件大小",required = true)
    private String fileSize;
    @ApiModelProperty(value = "时长（单位：秒）",notes = "时长（单位：秒）",required = true)
    private String duration;
    @ApiModelProperty(value = "录制文件码率（单位：字节）",notes = "录制文件码率（单位：字节）",required = true)
    private String bitrate;
    @ApiModelProperty(value = "分辨率",notes = "分辨率",required = true)
    private String resolution;
    @ApiModelProperty(value = "直播的场次ID",notes = "直播的场次ID",required = true)
    private String channelSessionId;
    @ApiModelProperty(value = "高",notes = "高",required = true)
    private String height;
    @ApiModelProperty(value = "宽",notes = "宽",required = true)
    private String width;
    @ApiModelProperty(value = "直播类型",notes = "直播类型",required = true)
    private String liveType;
    @ApiModelProperty(value = "m3u8文件地址",notes = "m3u8文件地址",required = true)
    private String m3u8;
    @ApiModelProperty(value = "MP4地址",notes = "MP4地址",required = true)
    private String mp4;

}
