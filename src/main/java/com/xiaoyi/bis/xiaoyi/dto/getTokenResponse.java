package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 获取token实体
 * @author CJ
 * @date 2019/11/11
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class getTokenResponse implements Serializable {

    private static final long serialVersionUID = -5440353303982331403L;

    @ApiModelProperty(value = "视频播放token",notes = "视频播放token",required = true)
    private String token;

}
