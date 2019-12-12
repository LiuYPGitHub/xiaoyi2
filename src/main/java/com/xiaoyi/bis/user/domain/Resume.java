package com.xiaoyi.bis.user.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：履历
 * @Author：kk
 * @Date：2019/8/28 14:03
 */
@Data
@ToString
@TableName("lz_pf_resume")
@Excel("履历表")
public class Resume implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "RESUME_ID")
    private String resumeId;
    private String userInfoId;
    private Date startDate;
    private Date endDate;
    private String company;
    private String title;
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
