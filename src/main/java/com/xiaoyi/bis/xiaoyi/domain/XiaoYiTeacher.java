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
 * 课程关联教师
 * @author CJ
 * @date 2019/10/23
 */
@ToString
@Getter
@Setter
@TableName(value = "ty_teachers")
public class XiaoYiTeacher implements Serializable {

    private static final long serialVersionUID = -5644328002101332634L;
    @TableId(value = "id",type = IdType.AUTO)
    private String tid;
    private String mobile;
    private String teacherId;
    private String orgCode;

}
