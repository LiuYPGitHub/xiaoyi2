package com.xiaoyi.bis.blog.controller.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description：动态
 * @Author：kk
 * @Date：2019/9/10 09:16
 */
@Data
public class DynamicBeanUrl implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 动态id
     */
    private String dynaId;

    private String userId;
    /**
     * 动态内容
     */
    private String dynaContent;
    /**
     * 动态状态 [0:正常 1:草稿]
     */
    private String dynaStatus;
    /**
     * 审核状态 [0:通过 1:待审核 2;未通过]
     */
    private Integer dynaCheckSta;
    /**
     * 用户类型 [0:KOL 1:名师]
     */
    private Integer dynaType;

    //    创建者
    private String createBy;
    //    创建时间
//    @JsonFormat(timezone = "GMT",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //    更新着
    private String updateBy;
    //    更新时间
//    @JsonFormat(timezone = "GMT",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private List<String> dynaImgs;
//
//    @Data
//    public static class DynaImgBean {
//        private String imgUrl;
//    }


}
