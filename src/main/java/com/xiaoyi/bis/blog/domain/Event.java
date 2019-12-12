package com.xiaoyi.bis.blog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Data
public class Event implements Serializable {
    private static final long serialVersionUID = 652559990726292713L;
    // 活动id
    private String evtId;
    // 用户id
    private String userId;
    // 活动标题
    private String evtTitle;
    // 活动类型
    private String evtType;
    // 活动方式 [0:线下 1:线上]
    private String evtWay;
    // 活动开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date evtStartTime;
    // 活动结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date evtEndTime;
    // 活动地址
    private String evtAddress;
    // 活动内容
    private String evtContent;
    // 活动图片
    private String evtImgUrl;
    // 活动状态 [0:正常 1:草稿箱 2:案例][当前时间>活动结束时间 为案例状态]
    private String evtStatus;
    // 审核状态 [0:通过 1:待审核 2:未通过]
    private String evtCheckStatus;
    // [1:正常 2:删除]
    private String isDelete;
    // 创建者
    private String createBy;
    // 创建时间
    private Date createTime;
    // 更新者
    private String updateBy;
    // 更新时间
    private Date updateTime;
    // 活动链接地址
    private String evtCoDdress;
    //
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date evtRelTime;
    //
    private String evtRelName;
}
//{
//        "createBy": "string",
//        "evtAddress": "活动地址",
//        "evtCoDdress": "活动链接地址",
//        "evtContent": "活动内容",
//        "evtEndTime": "2019-09-21 10:29:09",
//        "evtImgUrl": "string",
//        "evtRelName": "string",
//        "evtRelTime": "2019-09-21 10:29:09",
//        "evtStartTime": "2019-09-21 10:29:09",
//        "evtStatus": "0",
//        "evtTitle": "活动标题",
//        "evtType": "0",
//        "evtWay": "0",
//        "userId": "111111111"
//        }