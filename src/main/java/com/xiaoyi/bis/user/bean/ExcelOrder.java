
package com.xiaoyi.bis.user.bean;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description：用户订单
 * @Author：kk
 * @Date：2019/10/28 10:35
 */
@Data
public class ExcelOrder {
    @ColumnWidth(28)
    @ExcelProperty(value = "订单名称")
    private String orderName;

    @ColumnWidth(20)
    @ExcelProperty(value = "订单号")
    private String orderNum;

    @ColumnWidth(12)
    @ExcelProperty(value = "订单类型")
    private String orderType;

    @ExcelIgnore
    private String orderChannel;

    @ColumnWidth(12)
    @ExcelProperty(value = "机构名")
    private String siteName;

    @ExcelIgnore
    private String orgCode;

    @ColumnWidth(15)
    @ExcelProperty(value = "渠道号")
    private String channelName;

    @ColumnWidth(28)
    @ExcelProperty(value = "课程名称")
    private String courseName;

    @ColumnWidth(12)
    @ExcelProperty(value = "订单类型")
    private String classType;

    @ColumnWidth(15)
    @ExcelProperty(value = "用户")
    private String userName;

    @ColumnWidth(15)
    @ExcelProperty(value = "用户手机号")
    private String userMobile;

    @ColumnWidth(12)
    @ExcelProperty(value = "支付方式")
    private String payType;

    @ColumnWidth(12)
    @ExcelProperty(value = "支付状态")
    private String payStatus;

    @ColumnWidth(12)
    @ExcelProperty(value = "支付金额")
    private String cost;

    @ColumnWidth(12)
    @ExcelProperty(value = "原始费用")
    private String primeCost;

    @ColumnWidth(12)
    @ExcelProperty(value = "退款状态")
    private String refundName;

    @ColumnWidth(20)
    @ExcelProperty(value = "退款时间")
    private String refundDate;

    @ColumnWidth(20)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "订单完成时间")
    private Date orderFinishDate;


}
