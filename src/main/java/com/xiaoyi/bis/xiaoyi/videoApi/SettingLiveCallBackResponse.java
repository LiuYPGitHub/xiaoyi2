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
public class SettingLiveCallBackResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -3633555526671539619L;

    public SettingLiveCallBackResponse(boolean flag) {
        success();
        this.flag = flag;
    }

    @ApiModelProperty(value = "设置结果",notes = "true 成功 false 失败")
    private boolean flag;

}
