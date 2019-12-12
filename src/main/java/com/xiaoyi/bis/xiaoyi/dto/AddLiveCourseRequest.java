package com.xiaoyi.bis.xiaoyi.dto;

import com.xiaoyi.bis.xiaoyi.bean.LiveLessonBean;
import com.xiaoyi.bis.xiaoyi.bean.RecordLessonBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 添加直播课程入参实体
 * @author CJ
 * @date 2019/10/17
 */
@ToString
@Getter
@Setter
public class AddLiveCourseRequest extends AddCourseRequest implements Serializable {

    private static final long serialVersionUID = 7071663149827338830L;
    //lessonName,startDate,endDate
    @ApiModelProperty(value = "直播课节列表json数据",notes = "直播课节列表json数据",required = true)
    private String lessons;
    @ApiModelProperty(value = "课程编号",notes = "课程编号")
    private Integer courseId;

}
