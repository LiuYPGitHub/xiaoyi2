package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

/**
 * 分页信息返回实体
 * @author CJ
 * @date 2019/10/17
 */
@ToString
@Getter
@Setter
public class PageResponse implements Serializable {

    private static final long serialVersionUID = 4983393420183378531L;

    @ApiModelProperty(value = "当前页码",notes = "当前页码")
    private Integer pageIndex;
    @ApiModelProperty(value = "页面大小",notes = "页面大小")
    private Integer pageSize;
    @ApiModelProperty(value = "总页数",notes = "总页数")
    private Integer pageCount;
    @ApiModelProperty(value = "总记录数",notes = "总记录数")
    private Integer totalCount;
    @ApiModelProperty(value = "分页数据",notes = "分页数据")
    private Object list;

}
