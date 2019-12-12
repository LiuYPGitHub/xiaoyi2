package com.xiaoyi.bis.xiaoyi.videoApi;

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
public class StartLiveCallBackRequest implements Serializable {

    private static final long serialVersionUID = -1292893500597803614L;

    @ApiModelProperty(value = "频道ID",notes = "频道ID",required = true)
    private String channelId;
    @ApiModelProperty(value = "录制文件地址",notes = "录制文件地址",required = true)
    private String fileUrl;
    @ApiModelProperty(value = "13位的时间戳",notes = "13位的时间戳",required = true)
    private String timestamp;
    @ApiModelProperty(value = "校验的加密字符串，生成的规则md5(AppSecret+timestamp)",notes = "AppSecret是直播系统的用密匙",required = true)
    private String sign;
    @ApiModelProperty(value = "录制唯一的id",notes = "录制唯一的id",required = true)
    private String fileId;
    @ApiModelProperty(value = "录制来源",notes = "manual-云录制，auto-自动录制，merge-合并，clip-裁剪",required = true)
    private String origin;
    @ApiModelProperty(value = "该字段只对开启云录制功能有用",notes = "'Y':表示该场直播录制同时存在云录制和自动录制，'N':该场直播只有自动录制",required = true)
    private String hasRtcRecord;

}
