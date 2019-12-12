package com.xiaoyi.bis.xiaoyi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 添加智能录播-完整版入参实体类
 *
 * @author CJ
 * @date 2019/11/4
 */
@ToString
@Getter
@Setter
public class AddIntelligentRecordAllRequest implements Serializable {

    private static final long serialVersionUID = -3315112264104981500L;

    @NotBlank(message = "{userId不能为空}")
    @ApiModelProperty(value = "当前登录用户编号", notes = "当前登录用户编号", required = true)
    private String userId;
    @NotBlank(message = "{课程名称不能为空}")
    @Length(min = 1, max = 50, message = "{限1-50个字包含符号和数字}")
    @ApiModelProperty(value = "课程名称", notes = "课程名称", required = true)
    private String courseName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "录制开始时间", notes = "yyyy-MM-dd HH:mm:ss", required = true, position = 2)
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "录制结束时间", notes = "yyyy-MM-dd HH:mm:ss", required = true, position = 3)
    private Date endDate;
    @ApiModelProperty(value = "总课时", notes = "总课时", required = false)
    private Integer courseCount;
    @ApiModelProperty(value = "班级名称", notes = "班级名称", required = true)
    private String className;
    @ApiModelProperty(value = "授课老师", notes = "教师名称", required = true)
    @NotBlank(message = "{老师不能为空}")
    @Length(min = 1, max = 50, message = "{限1-50个字包含符号和数字}")
    private String teacherName;
    @ApiModelProperty(value = "学生人数", notes = "学生人数", required = true)
    private Integer studentCount;
    private String videoStatus;

}
