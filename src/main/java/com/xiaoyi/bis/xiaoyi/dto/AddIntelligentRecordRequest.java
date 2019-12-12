package com.xiaoyi.bis.xiaoyi.dto;

import com.xiaoyi.bis.xiaoyi.bean.IntelligentRecordLessonBean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.List;

/**
 * 添加智能录播-精编版入参实体类
 *
 * @author CJ
 * @date 2019/11/4
 */
@ToString
@Getter
@Setter
public class AddIntelligentRecordRequest implements Serializable {

    private static final long serialVersionUID = -790485742103228330L;

    @ApiModelProperty(value = "当前登录用户编号", notes = "当前登录用户编号", required = true)
    private String userId;
    @ApiModelProperty(value = "课程名称", notes = "课程名称", required = true)
    private String courseName;
    @ApiModelProperty(value = "总时长", notes = "总时长", required = true)
    private Integer courseMins;
    @ApiModelProperty(value = "总课时", notes = "总课时", required = true)
    private Integer courseCount;
    @ApiModelProperty(value = "班级名称", notes = "班级名称", required = true)
    private String className;
    @ApiModelProperty(value = "教师名称", notes = "教师名称", required = true)
    private String teacherName;
    @ApiModelProperty(value = "学生人数", notes = "学生人数", required = true)
    private Integer studentCount;
    @ApiModelProperty(value = "课节json字符串", notes = "课节json字符串", required = false)
    private  String lessons;

}
