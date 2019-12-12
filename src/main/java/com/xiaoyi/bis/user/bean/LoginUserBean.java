package com.xiaoyi.bis.user.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUserBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userId;
    private String orgCode;
    private String realName;
    private String telNum;
    private String userType;
    private String headImgUrl;
    private String role;//角色
    private Integer followCount;
    private Integer userTypeTips;//为 1 时提示选择身份
    private Integer passwordTips;//为 1 时提示选择设置密码


}
