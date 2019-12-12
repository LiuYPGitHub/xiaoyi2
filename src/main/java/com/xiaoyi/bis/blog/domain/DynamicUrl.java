package com.xiaoyi.bis.blog.domain;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * KOL动态\名师动态 详情 最新动态
 */
@Getter
@Setter
@ToString
@Data
public class DynamicUrl implements Serializable {
    private static final long serialVersionUID = -7261445364133583455L;
    //    动态id
    private String dynaId;
    //    user_id
    @ApiModelProperty("用户ID")
    private String userId;
    //    动态内容
    private String dynaContent;
    //    发布时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dynaTime;
    //    动态状态 [0:正常 1:草稿]
    private Integer dynaStatus;
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
    //    图片
    private String imgUrl;

//    public void setImgUrl(JSONArray parseArray) {
//
//    }

//    private List<String> imgUrl;
//
//    @Data
//    public static class ReqDy {
//        private String imgUrl;
//    }
}
