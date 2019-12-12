package com.xiaoyi.bis.xiaoyi.bean;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * 添加录播课程所属课节实体
 * @author CJ
 * @date 2019/11/20
 */
@ToString
@Getter
@Setter
public class LiveLessonBean extends LessonBean implements Serializable {

    private static final long serialVersionUID = 8829339556825446228L;

    //修改课节时必填
    @ApiModelProperty(value = "课节编号",required = false)
    private Integer lessionId;
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}

