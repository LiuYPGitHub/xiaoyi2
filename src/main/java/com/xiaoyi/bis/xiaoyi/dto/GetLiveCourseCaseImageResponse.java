package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 直播课程获取直播课程封面示例图实体
 * @author CJ
 * @date 2019/11/12
 */
@ToString
@Getter
@Setter
public class GetLiveCourseCaseImageResponse implements Serializable {

    private static final long serialVersionUID = -3351073528004912010L;
    @ApiModelProperty(value = "图片访问地址",notes = "图片访问地址")
    private String imageUrl;

}
