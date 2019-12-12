package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 获取智能录播MP4视频实体
 * @author CJ
 * @date 2019/11/8
 */
@ToString
@Getter
@Setter
public class PlayerVideoResponse implements Serializable {

    private static final long serialVersionUID = -896074150385039849L;

    @ApiModelProperty(value = "所属课程",notes = "所属课程")
    private Integer courseId;
    @ApiModelProperty(value = "获取状态",notes = "200:成功 400:获取失败")
    private String code;
    @ApiModelProperty(value = "状态信息",notes = "成功/失败信息")
    private String mess;
    @ApiModelProperty(value = "播放视频所需保利威视Vid",notes = "Vid")
    private String vid;
    @ApiModelProperty(value = "播放视频所需保利威视mp4地址",notes = "mp4")
    private String mp4;

}
