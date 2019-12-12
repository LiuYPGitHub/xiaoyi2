package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/12
 */
@ToString
@Getter
@Setter
public class AddLiveLessonRequest implements Serializable {

    private static final long serialVersionUID = -7910527745365155871L;
    @ApiModelProperty(value = "教师编号",notes = "教师编号",required = true)
    public String teacherId;
    @ApiModelProperty(value = "课程编号",notes = "课程编号",required = true)
    public String classId;
    @ApiModelProperty(value = "课节内容",notes = "课节内容",required = true)
    public String liveContent;
    @ApiModelProperty(value = "课节时长",notes = "课节时长",required = true)
    public String lessonHour;
    @ApiModelProperty(value = "课节数值 1-15",notes = "课节数值 1-15",required = true)
    public String liveManNumber;
    @ApiModelProperty(value = "课节开始时间",notes = "课节开始时间",required = true)
    public String liveStartDate;
    @ApiModelProperty(value = "课节结束时间",notes = "课节结束时间",required = true)
    public String liveEndDate;

}
