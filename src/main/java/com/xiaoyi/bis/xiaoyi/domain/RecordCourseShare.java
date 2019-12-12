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
 * @author CJ
 * @date 2019/11/7
 */
@ToString
@Getter
@Setter
@TableName(value = "ty_record_share")
public class RecordCourseShare implements Serializable {

    private static final long serialVersionUID = 961860342647796415L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String studentName;
    @TableField(value = "mobile")
    private String mobile;
    @TableField(value = "course_name")
    private String courseName;
    @TableField(value = "class_id")
    private String classId;
    @TableField(value = "valid_start_date")
    private String validStartDate;
    @TableField(value = "valid_end_date")
    private String validEndDate;
    @TableField(value = "updated_at")
    private Date updatedAt;
    @TableField(value = "created_at")
    private Date createdAt;

}
