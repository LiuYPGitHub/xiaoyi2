package com.xiaoyi.bis.xiaoyi.bean;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

/**
 * 添加智能录播-精编版课程所属课节实体
 * @author CJ
 * @date 2019/11/20
 */
@ToString
@Getter
@Setter
public class IntelligentRecordLessonBean implements Serializable {

    private static final long serialVersionUID = 8829339556825446228L;

    @ApiModelProperty(value = "课节名称", notes = "课节名称", required = true)
    private String lessonName;
//    @ApiModelProperty(value = "课节描述", notes = "课节描述", required = true)
//    private String lessonDesc;
    @ApiModelProperty(value = "保利威视频编号", notes = "保利威视频编号", required = true)
    private String vid;
    @ApiModelProperty(value = "mp4地址", notes = "mp4地址", required = true)
    private String mp4;

}

