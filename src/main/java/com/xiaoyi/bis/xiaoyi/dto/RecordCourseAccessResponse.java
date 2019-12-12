package com.xiaoyi.bis.xiaoyi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 获取指定录播课程添翼申学访问地址实体
 * @author CJ
 * @date 2019/11/12
 */
@ToString
@Getter
@Setter
public class RecordCourseAccessResponse implements Serializable {

    private static final long serialVersionUID = -869916664170872829L;

    @ApiModelProperty(value = "添翼申学访问Url",notes = "添翼申学访问Url")
    private String accessUrl;

}
