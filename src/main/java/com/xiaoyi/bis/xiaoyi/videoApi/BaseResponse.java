package com.xiaoyi.bis.xiaoyi.videoApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/22
 */
@ToString
@Getter
@Setter
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = -9042219228726526092L;

    @ApiModelProperty(value ="成功/失败代码",notes = "成功/失败代码")
    private String code;
    @ApiModelProperty(value ="成功/失败状态",notes = "成功/失败状态")
    private String status;
    @ApiModelProperty(value ="成功/失败信息",notes = "成功/失败信息")
    private String message;

    public void success(){
        this.code="200";
        this.status="success";
        this.message="成功";
    }

    public void failed(String mess){
        this.code="0";
        this.status="failed";
        this.message=mess;
    }

}
