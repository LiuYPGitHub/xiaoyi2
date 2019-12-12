package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 检查智能录播-精编版分享入参实体
 * @author CJ
 * @date 2019/11/7
 */
@ToString
@Getter
@Setter
public class CheckShareStudentResponse implements Serializable {

    private static final long serialVersionUID = 5199351817101708405L;

    @ApiModelProperty(value = "完整版检查结果列表",notes = "完整版检查结果列表")
    private List<CheckShareStudentAll> checkShareStudentAlls;

}
