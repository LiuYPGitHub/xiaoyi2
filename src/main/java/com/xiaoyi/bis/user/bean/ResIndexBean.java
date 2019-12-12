package com.xiaoyi.bis.user.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResIndexBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userInfoId;
    private String userId;
    private String realName;
    private String nickName;
    private String headImgUrl;
    private String dynamicImg;
    private String personalInfo;
    private List<String> labels;

}
