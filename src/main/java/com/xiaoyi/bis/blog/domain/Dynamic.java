package com.xiaoyi.bis.blog.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * KOL动态\名师动态
 */
@Getter
@Setter
@ToString
@TableName("lz_pf_dynamic")
//@ApiModel("动态实体")
public class Dynamic implements Serializable {
    private static final long serialVersionUID = -7261445364133583455L;
    //    动态id
    private String dynaId;
    //    user_id
    @ApiModelProperty("用户ID")
    private String userId;
    //    动态内容
    private String dynaContent;
    //    发布时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")// timezone = "GMT",
    private Date dynaTime;
    //    动态状态 [0:正常 1:草稿]
    private String  dynaStatus;
    //    审核状态 [0:通过 1:待审核 2;未通过]
    private Integer dynaCheckSta;
    //    用户类型 [0:KOL 1:名师]
    private Integer dynaType;
    //    删除状态 [1:正常 2:删除]
    private Integer isDelete;
    //    创建者
    private String createBy;
    //    创建时间
    private Date createTime;
    //    更新着
    private String updateBy;
    //    更新时间
    private Date updateTime;
    //
    private Date evtRelTime;
    //
    private String evtRelName;
}
