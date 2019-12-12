package com.xiaoyi.bis.xiaoyi.dto;

import com.xiaoyi.bis.xiaoyi.bean.RecordCourseBean;
import com.xiaoyi.bis.xiaoyi.bean.RecordLessonBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.List;

/**
 * 添加录播课程入参实体
 * @author CJ
 * @date 2019/11/1
 */
@ToString
@Getter
@Setter
public class AddRecordCourseRequest extends AddCourseRequest implements Serializable {

    private static final long serialVersionUID = -4686999450818256517L;
    //修改录播接口必传参数
    @ApiModelProperty(value = "课程编号",notes = "课程编号",required = false)
    private Integer courseId;
    @ApiModelProperty(value = "智能录播编号",notes = "智能录播编号",required = false)
    private Integer recordId;
    @ApiModelProperty(value = "课节列表-完整版json数据",notes = "课节列表-完整版json数据",required = false)
    private String courses;
    @ApiModelProperty(value = "课节列表-精编版json数据",notes = "课节列表-精编版json数据",required = false)
    private String lessons;

}
