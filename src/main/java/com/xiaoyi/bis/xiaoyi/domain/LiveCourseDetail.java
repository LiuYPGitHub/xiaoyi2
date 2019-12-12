package com.xiaoyi.bis.xiaoyi.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * 直播课详情
 * @author CJ
 * @date 2019/10/14
 */
@ToString
@Getter
@Setter
public class LiveCourseDetail implements Serializable {

    private static final long serialVersionUID = -2009426713491210474L;

    private Integer id;
    private Integer courseId;
    private String lessonId;
    private String lessonName;
    private String code;
    private Integer lessonNum;
    /*private Date lessonDate;*/
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    //机构编号
    private String orgCode;

}
