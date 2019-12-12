package com.xiaoyi.bis.blog.controller.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description：动态请求参数
 * @Author：kk
 * @Date：2019/9/10 09:07
 */
@Data
public class ReqDynamic implements Serializable {
    private static final long serialVersionUID = 1L;

    //    动态id
    private String dynaId;

    private String userId;

    /**
     * 动态内容
     */
    private String dynaContent;
    /**
     * 动态状态 [0:正常 1:草稿]
     */
    private Integer dynaStatus;
    /**
     * 审核状态 [0:通过 1:待审核 2;未通过]
     */
    private Integer dynaCheckSta;
    /**
     * 用户类型 [0:KOL 1:名师]
     */
    private Integer dynaType;
    //    删除状态 [1:正常 2:删除]
    private Integer isDelete;

    //    创建者
    private String createBy;
    //    创建时间
    @JsonFormat(timezone = "GMT",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //    更新着
    private String updateBy;
    //    更新时间
    @JsonFormat(timezone = "GMT",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private List<ReqDynaImg> dynaImgs;

    @Data
    public static class ReqDynaImg {

        private String dynaImgId;

        /**
         * 动态活动id
         */
        private String dynaId;

        /**
         * 类型 [区分KOL 名师]
         */
        private String dynaImgType;

        /**
         * 图片状态 1:正常 2:删除 3:新增
         */
        private String imgState;

        private String imgUrl;
        // 图片来源
        private String imgSource;
        //    创建者
        private String createBy;
        //    创建时间
        @JsonFormat(timezone = "GMT",pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createTime;
        //    更新着
        private String updateBy;
        //    更新时间
        @JsonFormat(timezone = "GMT",pattern = "yyyy-MM-dd HH:mm:ss")
        private Date updateTime;
    }
}
