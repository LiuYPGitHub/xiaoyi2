package com.xiaoyi.bis.xiaoyi.videoApi;

import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIToken;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 获取保利威视频播放token返回实体
 * @author CJ
 * @date 2019/10/23
 */
@ToString
@Getter
@Setter
public class QueryVideoTokenResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -4589869526757707554L;
    @ApiModelProperty(value = "保利威视频播放token信息",notes = "保利威视频播放token信息")
    private APIToken apiToken;
}
