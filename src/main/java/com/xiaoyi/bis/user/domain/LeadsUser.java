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
@TableName("lz_pf_user")
@Excel("用户信息表")
public class LeadsUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "USER_ID")
    private String userId;

    private String userInfoId;

    private String userName;

    private String password;

    private String telNum;

    private String userState;

    private String userType;

    private String isDelete;

    private String role;

    private String orgId;

    private String platform;

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
