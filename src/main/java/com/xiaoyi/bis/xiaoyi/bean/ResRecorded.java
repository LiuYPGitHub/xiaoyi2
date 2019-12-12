package com.xiaoyi.bis.xiaoyi.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：录播完整版
 * @Author：KK
 * @Date：2019/11/25 11:06
 */
@Data
@ToString
public class ResRecorded implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseName;
    private Integer id;
    private String mp4;
    private String vid;
    private String cover;
    private String teacherName;
    private String studentNum;
    //1未开始 2录制中 3录制完成
    private String videoStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date regDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date startDate;
    //课程结束录制日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date endDate;
}