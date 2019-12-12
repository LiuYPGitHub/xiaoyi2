package com.xiaoyi.bis.user.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class IndexBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userInfoId;
    private String userId;
    private String realName;
    private String nickName;
    private String headImgUrl;
    private String dynamicImg;
    private String personalInfo;
    private String labels;

}
