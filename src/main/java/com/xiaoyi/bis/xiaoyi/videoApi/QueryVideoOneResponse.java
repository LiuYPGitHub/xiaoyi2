package com.xiaoyi.bis.xiaoyi.videoApi;

import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIRecordFile;
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
public class QueryVideoOneResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -2465906255059043410L;
    @ApiModelProperty(value = "录播文件",notes = "录播文件")
    private APIRecordFile data;

}
