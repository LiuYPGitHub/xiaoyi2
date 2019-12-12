package com.xiaoyi.bis.xiaoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 录播详情
 * @author CJ
 * @date 2019/11/6
 */
@ToString
@Getter
@Setter
@TableName(value = "ty_record_detail")
public class RecordDetail implements Serializable {

    private static final long serialVersionUID = -7652309641339277937L;
    @TableId(value = "`id`",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "`course_id`")
    private Integer courseId;
    @TableField(value = "`lesson_id`")
    private String lessonId;
    @TableField(value = "`lesson_name`")
    private String lessonName;
    @TableField(value = "`lesson_desc`")
    private String lessonDesc;
    @TableField(value = "`lesson_order`")
    private String lessonNum;
    @TableField(value = "`code`")
    private String code;
    @TableField(value = "`org_code`")
    private String orgCode;
    @TableField(value = "`vid`")
    private String vid;
    @TableField(value = "`mp4`")
    private String mp4;
    //课节时长
    @TableField(value = "`duration`")
    private String duration;
    @TableField(value = "`record_hour`")
    private Integer recordHour;
    @TableField(value = "`created_at`")
    private Date createdAt;
    @TableField(value = "`updated_at`")
    private Date updatedAt;
    @TableField(value = "`is_revised`",exist = false)
    private Integer isRevised;
    //1允许试看 2不允许
    @TableField(value = "`allowTaste`")
    private Integer allowTaste;
    //课节状态 1编码中 2已完成
    @TableField(value = "`status`")
    private Integer status;

}
