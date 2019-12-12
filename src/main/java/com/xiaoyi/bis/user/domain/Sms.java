package com.xiaoyi.bis.user.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：短信
 * @Author：kk
 * @Date：2019/8/28 14:03
 */
@Data
@ToString
@TableName("lz_sms")
@Excel("用户信息表")
public class Sms implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "SMS_ID")
    private String smsId;
    private String smsCode;
    private String message;
    private String telNum;
    private String bId;
    private Date createTime;
    private Date updateTime;
}
