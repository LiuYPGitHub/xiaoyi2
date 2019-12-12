package com.xiaoyi.bis.blog.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * code
 */
@Getter
@Setter
@ToString
public class Code implements Serializable {
    private static final long serialVersionUID = -3176152398138122389L;

    // 地域编码
    private String areaCode;
    // 地域名
    private String areaName;
    // 地域父节点编码
    private String areaParentCode;
    // 地域级别
    private String areaLevel;
    // 0:无效，1:有效
    private String areaFlag;
    // 创建者
    private String createBy;
    // 创建时间
    private Date createTime;
    // 更新着
    private String updateBy;
    // 更新时间
    private Date updateTime;
}
