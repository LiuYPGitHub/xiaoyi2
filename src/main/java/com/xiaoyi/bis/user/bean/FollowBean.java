package com.xiaoyi.bis.user.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：机构关注
 * @Author：kk
 * @Date：2019/8/29 11:47
 */

@Data
public class FollowBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String followId;
    private String userInfoId;
    private String followUserId;
    private String followUserName;
    private String followPersonInfo;
    private String followPersonalBg;
    private String followState;
    private String followHeadImg;
    private String followNickName;
    private String isDelete;
    private String type;
    private List<String> labels;

   /* private List<ResumeBean> resume;

    @Data
    public static class ResumeBean {
        private static final long serialVersionUID = 1L;
        private String resumeId;
        private String userInfoId;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date startDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date endDate;
        private String company;
        private String title;

    }*/
}
