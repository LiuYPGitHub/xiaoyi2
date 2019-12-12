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
 * 智能录播课节分享学生实体
 * @author CJ
 * @date 2019/11/6
 */
@ToString
@Getter
@Setter
@TableName(value = "ty_record_detail_share")
public class RecordDetailShare implements Serializable {

    private static final long serialVersionUID = 5210264189928498509L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer courseId;
    private String lessonId;
    private String lessonName;
    @TableField(value = "student_name")
    private String studentName;
    @TableField(value = "mobile")
    private String mobile;

    @TableField(value = "`code`")
    private String code;
    private String orgCode;
    @TableField(value = "`vid`")
    private String vid;
    @TableField(value = "`mp4`")
    private String mp4;

    private Integer recordHour;

    private Date createdAt;
    private Date updatedAt;
    private Integer isRevised;

}
