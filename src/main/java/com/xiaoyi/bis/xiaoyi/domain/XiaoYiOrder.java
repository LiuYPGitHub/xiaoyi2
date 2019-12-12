package com.xiaoyi.bis.xiaoyi.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 * @author CJ
 * @date 2019/10/16
 */
@ToString
@Getter
@Setter
public class XiaoYiOrder implements Serializable {

    private static final long serialVersionUID = -8898319627488967689L;

    private Integer id;
    private Date createDate;
    private String orgCode;
    private String siteName;
    private String channelName;
    private String channel;
    private String userName;
    private String userMobile;
    private String orderName;
    private String classType;
    private String orderNum;
    private String orderType;
    private String payStatus;
    private BigDecimal cost;
    private BigDecimal primeCost;
    private Date orderFinishDate;
    private Date orderExpireDate;

}
