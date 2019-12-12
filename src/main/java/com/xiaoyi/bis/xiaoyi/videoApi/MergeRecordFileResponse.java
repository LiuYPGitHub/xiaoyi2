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
public class MergeRecordFileResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 286547263462678884L;
    @ApiModelProperty(value = "处理反馈",notes = "处理反馈")
    private String data;

}
