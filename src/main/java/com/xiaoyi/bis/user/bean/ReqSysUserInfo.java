package com.xiaoyi.bis.user.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ReqSysUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userInfoId;
    private String realName; //机构/真实名字
    private String nickName; //昵称
    private String contacts; //联系人
    private String headImgUrl;
    private String educational;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String city;
    private String org; //所在机构
    private String personalInfo;
    private String personalBg;

    private List<String> labels;
    private List<ReqResume> resume;

    @Data
    public static class ReqResume {
        private static final long serialVersionUID = 1L;
        private String resumeId;
        private String userInfoId;
        private String userId;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date startDate;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date endDate;
        private String company;
        private String title;
        private String resumeState; //1：正常 2: 删除

    }
}
