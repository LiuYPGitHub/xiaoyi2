package com.xiaoyi.bis.user.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysUserInfoListBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userInfoId;
    private String userId;
    private String realName;
    private String nickName;
    private String headImgUrl;
    private String dynamicContent;
    private String dynamicImg;
    private Date dynamicDate;
    private String personalInfo;
    private List<String> labels;

}
