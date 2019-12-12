package com.xiaoyi.bis.user.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description：订单渠道号
 * @Author：kk
 * @Date：2019/10/28 10:35
 */
@Data
@ToString
@TableName("ty_order_channel")
public class OrderChannel implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String channelName;
    private String channel;
}
