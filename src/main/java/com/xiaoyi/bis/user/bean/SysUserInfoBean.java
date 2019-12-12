package com.xiaoyi.bis.user.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysUserInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userInfoId;
    private String userId;
    private String realName;
    private String nickName;
    private String contacts;
    private String headImgUrl;
    private String educational;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    private String province;
    private String city;
    private String org;
    private String personalInfo;
    private String personalBg;
    private String userType;
    private String userTypeName;
    private String followState;
    private Integer followCount;
    private String cooperationState;

    private String labels;

    private List<ResumeBean> resume;

    @Data
    public static class ResumeBean {
        private static final long serialVersionUID = 1L;
        private String resumeId;
        private String userInfoId;
        private String userId;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date startDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date endDate;
        private String company;
        private String title;
        private String resumeState;
    }
}
