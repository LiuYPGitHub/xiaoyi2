package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 查询智能录播课程入参实体
 *
 * @author CJ
 * @date 2019/11/11
 */
@ToString
@Getter
@Setter
public class QueryIntelligentRecordCourseRequest extends BaseCourseRequest implements Serializable {

    private static final long serialVersionUID = 2806449658986050190L;
    @ApiModelProperty(value = "智能录播类型1:完整版 2:精编版", notes = "1:完整版 2:精编版", required = true)
    private Integer courseType;
    @ApiModelProperty(value = "所属机构", notes = "所属机构", required = false)
    private String orgCode;
    @ApiModelProperty(value = "1未开始 2录制中 3录制完成", notes = "录制状态", required = false)
    private String videoStatus;
    private String userId;

}
