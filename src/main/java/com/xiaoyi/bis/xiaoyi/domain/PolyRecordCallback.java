package com.xiaoyi.bis.xiaoyi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 保利威视智能录播回调记录表实体
 * @author CJ
 * @date 2019/11/6
 */
@ToString
@Getter
@Setter
@TableName(value = "ty_poly_record_callback")
public class PolyRecordCallback implements Serializable {

    @TableId(value = "`id`")
    private Integer id;
    @TableField(value = "`channelId`")
    private String channelId;
    @TableField(value = "`fileUrl`")
    private String fileUrl;
    @TableField(value = "`timestamp`")
    private String timestamp;
    @TableField(value = "`sign`")
    private String sign;
    @TableField(value = "`fileId`")
    private String fileId;
    @TableField(value = "`origin`")
    private String origin;
    @TableField(value = "`hasRtcRecord`")
    private String hasRtcRecord;
    @TableField(value = "`created_at`")
    private Date createdAt;
    @TableField(value = "`datetime`")
    private Date dateTime;
    @TableField(value = "`startTime`")
    private Date startTime;
    @TableField(value = "`endTime`")
    private Date endTime;

}
