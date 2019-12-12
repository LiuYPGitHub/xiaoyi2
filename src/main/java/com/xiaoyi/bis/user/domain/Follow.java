package com.xiaoyi.bis.user.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：机构关注
 * @Author：kk
 * @Date：2019/8/29 11:47
 */

@Data
@ToString
@TableName("lz_pf_follow")
@Excel("用户信息表")
public class Follow implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "FOLLOW_ID")
    private String followId;
    private String userInfoId;
    private String followUserId;
    private String followState;
    private String followType;

    private String isDelete;
    /**
     * 创建者
     */
    private String createBy;

    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    private Date updateTime;
}
