package com.xiaoyi.bis.user.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Description：首页动态
 * @Author：KK
 * @Date：2019/11/16 16:05
 */
@Data
@ToString
@TableName("lz_pf_dynamic_index")
@Excel("首页动态")
public class DynamicIndex {
    private String dynamicId;
    private String userId;
    private String dynaId;
    private String dynaContent;
    private String isDelete;
    private String dynaType;
    private String dynaStatus;

    private Date createTime;
    private Date updateTime;
}