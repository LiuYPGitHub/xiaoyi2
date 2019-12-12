package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

import com.xiaoyi.bis.xiaoyi.videoApi.BaseResponse;
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
public class AddUserCourseResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -1702131360538418266L;

    @ApiModelProperty(value = "添加结果",notes = "true 成功 false 失败")
    private boolean flag;

}
