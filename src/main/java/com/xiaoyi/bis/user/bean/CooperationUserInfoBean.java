package com.xiaoyi.bis.user.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：用户数据
 * @Author：kk
 * @Date：2019/8/30 13:43
 */
@Data
public class CooperationUserInfoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String userInfoId;
    private String userName;
    private String nickName;
    private String headImg;
    private String telNum;
    private String contacts;
    private String userType;
    private String info;
    private String labels;

}
