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
@TableName("lz_area_code")
@Excel("用户信息表")
public class Area implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "AREA_CODE")
    private String areaCode;
    private String areaName;
    private String areaParentCode;
    private String areaLevel;
    private String areaFlag;

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
