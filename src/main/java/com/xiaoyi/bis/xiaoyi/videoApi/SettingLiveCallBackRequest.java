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
public class SettingLiveCallBackRequest implements Serializable {

    private static final long serialVersionUID = -8717793213111763631L;
    @ApiModelProperty(value = "回调地址url",notes = "回调地址url",required = false)
    private String url;

}
