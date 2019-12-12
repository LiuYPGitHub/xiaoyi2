package com.xiaoyi.bis.blog.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@TableName("lz_pf_dynamic_img")
public class DynamicImg implements Serializable {
    private static final long serialVersionUID = -7089805989192401495L;
    // 图片id
    private String dynaImgId;
    // 类型 [区分KOL 名师]
    private String dynaImgType;
    // 动态活动id
    private String dynaId;
    // 图片
    private String imgUrl;
    // 图片来源
    private String imgSource;
    // [1:正常 2:删除]
    private Integer isDelete;
    // 创建者
    private String createBy;
    // 创建时间
    private Date createTime;
    // 更新者
    private String updateBy;
    // 更新时间
    private Date updateTime;


}
