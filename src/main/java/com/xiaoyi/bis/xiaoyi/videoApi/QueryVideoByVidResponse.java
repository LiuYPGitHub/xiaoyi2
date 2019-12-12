package com.xiaoyi.bis.xiaoyi.videoApi;

import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIVideo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/11/19
 */
@ToString
@Getter
@Setter
public class QueryVideoByVidResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -2465906255059043410L;
    @ApiModelProperty(value = "录播文件",notes = "录播文件")
    private APIVideo data;

}
