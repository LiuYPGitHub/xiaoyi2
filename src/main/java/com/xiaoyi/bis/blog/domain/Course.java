package com.xiaoyi.bis.blog.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 名师课程
 */
@Getter
@Setter
@ToString
public class Course implements Serializable {
    private static final long serialVersionUID = 6702322847187345529L;

    // 课程id
    private String coId;
    private String userId;
    // 课程标题
    private String coTitle;
    // 课程类型 [0:直播 1:录播]
    private String coType;
    // 课程状态 [0:正常 1:草稿]
    private String coStatus;
    // 课程链接
    private String coLinkUrl;
    // 课程图片 [banner]
    private String imgUrl;
    // [1:正常 2:删除]
    private String isDelete;
    // 创建者
    private String createBy;
    // 创建时间
    private Date createTime;
    // 更新着
    private String updateBy;
    // 更新时间
    private Date updateTime;

    private Date evtRelTime;

    private String evtRelName;
}
