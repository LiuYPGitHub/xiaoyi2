package com.xiaoyi.bis.common.dict.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：字典
 * @Author：kk
 * @Date：2019/8/28 14:03
 */
@Data
@TableName("lz_code_list")
//@Excel("字典表")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "CODE_ID")
    private String codeId;
    private String codeType;
    private String codeNo;
    private String codeName;
    private String codeFlay;

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
