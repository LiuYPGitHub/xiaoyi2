package com.xiaoyi.bis.xiaoyi.videoApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/12/5
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class GetVideoOneInfoRequest implements Serializable {

    private static final long serialVersionUID = -2575373205507820893L;

    @ApiModelProperty(value = "保利威视频编号",notes = "保利威视频编号",required = true)
    private String vid;

}
