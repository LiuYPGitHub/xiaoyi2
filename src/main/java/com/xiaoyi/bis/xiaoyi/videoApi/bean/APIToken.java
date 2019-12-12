package com.xiaoyi.bis.xiaoyi.videoApi.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 获取保利威token信息实体
 * @author CJ
 * @date 2019/11/25
 */
@ToString
@Getter
@Setter
public class APIToken implements Serializable {

    private static final long serialVersionUID = 8939837469757661207L;

    @ApiModelProperty(value = "保利威视频播放token",notes = "保利威视频播放token")
    private String token;
    @ApiModelProperty(value = "保利威appId",notes = "保利威appId")
    private String appId;
    @ApiModelProperty(value = "保利威用户编号",notes = "保利威用户编号")
    private String userId;
    @ApiModelProperty(value = "保利威视频编号",notes = "保利威视频编号")
    private String videoId;
    @ApiModelProperty(value = "观看者IP,为空则自动获取调用该接口时的IP",notes = "为空则自动获取调用该接口时的IP")
    private String viewerIp;
    @ApiModelProperty(value = "观看者ID，要求不同的观看者使用不同的ID",notes = "观看者ID，要求不同的观看者使用不同的ID")
    private String viewerId;
    @ApiModelProperty(value = "观看者名称",notes = "观看者名称")
    private String viewerName;
    @ApiModelProperty(value = "自定义的其它参数",notes = "自定义的其它参数")
    private String extraParams;
    @ApiModelProperty(value = "token 有效时长，单位为毫秒",notes = "token 有效时长，单位为毫秒")
    private String ttl;
    @ApiModelProperty(value = "token 创建时间",notes = "token 创建时间")
    private long createdTime;
    @ApiModelProperty(value = "token 过期时间",notes = "token 过期时间")
    private long expiredTime;
    @ApiModelProperty(value = "是否微信小程序播放，1为是，0为否。",notes = "是否微信小程序播放，1为是，0为否。")
    private Integer iswxa;
    @ApiModelProperty(value = "true:一次有效 false:有效期内可以多次验证",notes = "true 表示 token 仅一次有效（验证一次后，token 就失效了）。false 则表示在有效期内可以多次验证。")
    private boolean disposable;

}
