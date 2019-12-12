package com.xiaoyi.bis.xiaoyi.videoApi;

import com.xiaoyi.bis.xiaoyi.videoApi.bean.APINode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author CJ
 * @date 2019/10/22
 */
@ToString
@Getter
@Setter
public class QueryVideoClassResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 1496020666836084605L;
    @ApiModelProperty(value = "视频分类目录数据",notes = "视频分类目录数据")
    private List<APINode> data;

}
