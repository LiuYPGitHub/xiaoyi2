package com.xiaoyi.bis.xiaoyi.videoApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/11/19
 */
@ToString
@Getter
@Setter
public class QueryVideoByVidRequest implements Serializable {

    private static final long serialVersionUID = 560382624449383175L;

    @ApiModelProperty(value = "保利威视频编号",notes = "保利威视频编号",required = true,position = 1)
    private String vid;

}
