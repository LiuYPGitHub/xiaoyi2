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
@TableName("lz_pf_user_info")
@Excel("用户信息表")
public class LeadsUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "USER_INFO_ID")
    private String userInfoId;

    private String userId;

    private String orgId;

    private String isOrg;

    private String isAccount;

    private String orgState;

    private String realName;

    private String nickName;

    private String contacts;

    private String headImgUrl;

    private String educational;

    private Date birthday;

    private String labels;

    private String province;

    private String city;

    private String org;

    private String phone;

    private String address;

    private String personalInfo;

    private String personalBg;

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
