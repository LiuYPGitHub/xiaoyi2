package com.xiaoyi.bis.xiaoyi.videoApi.bean;

import com.xiaoyi.bis.xiaoyi.videoApi.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
@ToString
@Getter
@Setter
public class APIVideo extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 8713179893458751331L;
    @ApiModelProperty(value = "保利威视频编号",notes = "保利威视频编号")
    private String vid;
    @ApiModelProperty(value = "保利威视频下载地址(mp4格式)",notes = "保利威视频下载地址")
    private String fileUrl;
    @ApiModelProperty(value = "视频大小 单位是B",notes = "视频大小 单位是B")
    private Integer fileSize;
    @ApiModelProperty(value = "保利威频道类型",notes = "alone为普通直播，ppt为三分屏")
    private String type;
    @ApiModelProperty(value = "直播频道的场次ID",notes = "直播频道的场次ID")
    private String sessionId;
    @ApiModelProperty(value = "直播频道的ID",notes = "直播频道的ID")
    private String channelId;

}
