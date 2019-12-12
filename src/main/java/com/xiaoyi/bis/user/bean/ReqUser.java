package com.xiaoyi.bis.user.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：请求参数
 * @Author：kk
 * @Date：2019/8/28 14:03
 */
@Data
public class ReqUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
}
