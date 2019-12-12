package com.xiaoyi.bis.xiaoyi.videoApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author CJ
 * @date 2019/10/23
 */
@ToString
@Getter
@Setter
public class QueryUserChannelsResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 8210989745823315340L;

    @ApiModelProperty(value = "频道编号集合",notes = "频道编号集合")
    private List<String> data;

}
