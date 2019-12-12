package com.xiaoyi.bis.xiaoyi.xiaoyiApi.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 课程订单实体类
 * @author CJ
 * @date 2019/10/14
 */
@ToString
@Getter
@Setter
public class APICourseOrder implements Serializable {

    private static final long serialVersionUID = 7753071251708604762L;

    @ApiModelProperty(value = "机构名称",notes = "机构名称",required = true)
    private String siteName;
    @ApiModelProperty(value = "支付方式",notes = "支付方式",required = true)
    private String payType;
    @ApiModelProperty(value = "用户渠道",notes = "用户渠道",required = true)
    private String userChannel;
    @ApiModelProperty(value = "用户手机号",notes = "用户手机号",required = true)
    private String userMobile;
    @ApiModelProperty(value = "原始费用",notes = "原始费用",required = true)
    private BigDecimal classCost;
    @ApiModelProperty(value = "支付金额",notes = "支付金额",required = true)
    private BigDecimal totalCost;
    @ApiModelProperty(value = "用户名称",notes = "用户名称",required = true)
    private String userName;
    @ApiModelProperty(value = "商品订单号",notes = "商品订单号",required = true)
    private String orderNum;
    @ApiModelProperty(value = "支付状态",notes = "支付状态",required = true)
    private String payStatus;
    @ApiModelProperty(value = "订单完成日期",notes = "订单完成日期",required = true)
    private Date orderFinishDate;
    @ApiModelProperty(value = "退款状态",notes = "退款状态",required = true)
    private String refundName;
    @ApiModelProperty(value = "退款时间",notes = "退款时间",required = true)
    private Date refundDate;

}
