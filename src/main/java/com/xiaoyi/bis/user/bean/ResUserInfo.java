package com.xiaoyi.bis.user.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ResUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userInfoId;
    private String contacts;
    private String userId;
    @Length(min=2, max=10, message="长度必须在2-10之间")
    private String realName;
    @Length(min=2, max=10, message="长度必须在2-10之间")
    private String nickName;
    private String headImgUrl;
    private String educational;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String province;
    private String city;
    private String org;
    private String personalInfo;
    private String personalBg;
    private String userType;
    private String userTypeName;
    private String followState;
    private String cooperationState;

    private List<String> labels;
    private Integer followCount;
    private List<ResumeBean> resume;

    @Data
    public static class ResumeBean {
        private static final long serialVersionUID = 1L;
        private String resumeId;
        private String userId;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date startDate;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date endDate;
        private String company;
        private String title;
        private String resumeState;
        private String totalDate;

    }
}
