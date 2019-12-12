package com.xiaoyi.bis.user.bean;

import lombok.Data;

/**
 * @Description：短信
 * @Author：kk
 * @Date：2019/8/28 14:03
 */
@Data
public class SmsBean {

    private String code;
    private String msg;
    private String smsId;
}
