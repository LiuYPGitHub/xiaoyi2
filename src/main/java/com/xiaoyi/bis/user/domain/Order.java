package com.xiaoyi.bis.user.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("ty_orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID")
    private int id;
    private String orderName;
    private String orderNum;

    private String orderType;

    private String orderChannel;

    private String siteName;

    private String orgCode;

    private String channel;

    private String channelName;

    private String courseName;

    private String classType;

    private String userName;

    private String userMobile;

    private String payType;

    private String payStatus;

    private String cost;

    private String primeCost;

    private String refundName;

    private String refundDate;

    private Date orderFinishDate;

    private Date orderExpireDate;

    private Date createDate;

}
