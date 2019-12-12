package com.xiaoyi.bis.report.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class Orders implements Serializable {

    private static final long serialVersionUID = 8078141477896149359L;

    // 自增id
    private Integer id;
    // 课程名称
    private String courseName;
    // 订单渠道号
    private String orderChannel;
    // 支付方式
    private String payType;
    // 退款状态
    private String refundName;
    // 退款时间
    private String refundDate;
    // 生成日期
    private Date createDate;
    // 机构code
    private String orgCode;
    // 机构名
    private String siteName;
    // 渠道名
    private String channelName;
    // 渠道
    private String channel;
    // 用户名
    private String userName;
    // 用户电话
    private String userMobile;
    // 订单名称
    private String orderName;
    // 订单类型
    private String classType;
    // 订单号
    private String orderNum;
    // 订单类型
    private String orderType;
    // 支付方式
    private String payStatus;
    // 支付金额
    private Integer cost;
    // 原始费用
    private Integer primeCost;
    // 订单完成时间
    private Date orderFinishDate;
    // 订单过期时间
    private String orderExpireDate;
}