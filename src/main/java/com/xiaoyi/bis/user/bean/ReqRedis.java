package com.xiaoyi.bis.user.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：kk
 * @Date：2019/10/24 11:14
 */
@Data
public class ReqRedis implements Serializable {

    private static final long serialVersionUID = 1L;
    private String key;
    private String value;
    private Long milliscends;
}
