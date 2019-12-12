package com.xiaoyi.bis.user.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：用户订单
 * @Author：kk
 * @Date：2019/10/28 10:35
 */
@Data
@ToString
public class ReqOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private int orderId;

    /**
     * 机构
     */
    private String orgName;
    
    /**
     * 渠道
     */
    private String channelName;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 手机号
     */
    private String userMobile;

    /**
     * 支付状态
     */
    private String payStatus;


    /**
     * 订完完成时间
     */
    private Date orderStartDate;

    private Date orderEndDate;


}
