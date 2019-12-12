package com.xiaoyi.bis.xiaoyi.videoApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/24
 */
@ToString
@Getter
@Setter
public class FileForwardSaveCallBackRequest implements Serializable {

    private static final long serialVersionUID = 6378528579601398197L;
    @ApiModelProperty(value = "频道号ID",notes = "频道号ID")
    private String channelId;
    @ApiModelProperty(value = "转存成功的视频ID",notes = "转存成功的视频ID")
    private String vid;
    @ApiModelProperty(value = "视频标题",notes = "视频标题")
    private String title;
    @ApiModelProperty(value = "视频时长 格式为 hh:mm:ss",notes = "视频时长 格式为 hh:mm:ss")
    private String duration;
    @ApiModelProperty(value = "视频文件大小",notes = "单位为byte")
    private String fileSize;
    @ApiModelProperty(value = "13位的时间戳",notes = "13位的时间戳")
    private String timestamp;
    @ApiModelProperty(value = "校验的加密字符串",notes = "校验的加密字符串")
    private String sign;
    @ApiModelProperty(value = "录制的场次和时间对应的数组字符串",notes = "录制的场次和时间对应的数组字符串")
    private String sessionIds;
    @ApiModelProperty(value = "转存对应的录制文件id",notes = "转存对应的录制文件id")
    private String fileId;
    @ApiModelProperty(value = "转存回放唯一的id",notes = "转存回放唯一的id")
    private String videoId;
    @ApiModelProperty(value = "转存的录制来源。",notes = "manual-云录制，auto-自动录制，merge-合并，clip-裁剪")
    private String origin;
    @ApiModelProperty(value = "回放对应的单个场次id",notes = "回放对应的单个场次id")
    private String sessionId;
    @ApiModelProperty(value = "用户id",notes = "用户id")
    private String userId;
    @ApiModelProperty(value = "转存成功返回success",notes = "转存成功返回success")
    private String status;
    @ApiModelProperty(value = "文件名称",notes = "文件名称")
    private Integer id;

}
