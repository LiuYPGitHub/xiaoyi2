package com.xiaoyi.bis.xiaoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 课程分享学生实体
 * @author CJ
 * @date 2019/11/6
 */
@ToString
@Getter
@Setter
@TableName(value = "ty_share_students")
public class ShareStudent implements Serializable {

    private static final long serialVersionUID = 6707595821042454798L;
    @TableId(value = "`id`",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "`name`")
    private String name;
    @TableField(value = "`mobile`")
    private String mobile;
    @TableField(value = "`record_id`")
    private Integer recordId;
    @TableField(value = "`org_code`")
    private String orgCode;

}
