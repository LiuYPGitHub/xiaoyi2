package com.xiaoyi.bis.xiaoyi.videoApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/23
 */
@ToString
@Getter
@Setter
public class QueryVideoOneRequest implements Serializable {

    private static final long serialVersionUID = 560382624449383175L;
    @ApiModelProperty(value = "频道编号",notes = "频道编号",required = true,position = 1)
    private String channelId;
    @ApiModelProperty(value = "文件编号",notes = "文件编号",required = true,position = 2)
    private String fileId;

}
