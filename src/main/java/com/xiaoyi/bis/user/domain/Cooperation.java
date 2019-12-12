package com.xiaoyi.bis.user.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@TableName("lz_pf_cooperation")
@Excel("合作")
public class Cooperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "COOPERATION_ID")
    private String cooperationId;
    private String orgId;
    private String orgName;
    private String orgTel;
    private String contacts;
    private String sex;
    private String type;
    private String cooperationObjId;
    private String cooperationObjState;
    private String info;

    private String isDelete;

    /**
     * 创建者
     */
    private String createBy;

    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    private Date updateTime;


}
