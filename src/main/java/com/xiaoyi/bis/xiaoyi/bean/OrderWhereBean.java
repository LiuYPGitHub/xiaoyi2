package com.xiaoyi.bis.xiaoyi.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 校翼-数据中心-订单SQL条件
 * @author CJ
 * @date 2019/10/15
 */
@ToString
@Getter
@Setter
public class OrderWhereBean implements Serializable {

    private static final long serialVersionUID = -595203935622975538L;
    //this 本月 last 上月
    private String month;
    //is 相等 no 等于当前用户所属机构
    private String flag;

    private String channelName;

}
