package com.xiaoyi.bis.xiaoyi.dto;

import com.xiaoyi.bis.xiaoyi.constant.PageConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 分页信息入参实体
 * @author CJ
 * @date 2019/10/17
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 4983393420183378531L;

    @ApiModelProperty(value = "当前页码",notes = "当前页码")
    private Integer pageIndex;
    @ApiModelProperty(value = "页面大小",notes = "页面大小")
    private Integer pageSize;

    public void processPageRequest(){
        if(this.pageIndex == null){
            this.pageIndex = PageConstant.PAGE_INDEX;
        }
        if(this.pageSize == null){
            this.pageSize = PageConstant.PAGE_SIZE;
        }
    }

}
