package com.xiaoyi.bis.xiaoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 关联机构
 * @author CJ
 * @date 2019/12/4
 */
@ToString
@Getter
@Setter
@TableName(value = "`ty_orgs`")
public class Orgs implements Serializable {

    private static final long serialVersionUID = -3595560969926496259L;

    @TableId(value = "`id`",type = IdType.AUTO)
    private Integer id;
    //机构编号
    private String orgId;
    //机构名称
    private String orgName;
    //保利威频道编号
    private String channelId;
    //保利威分类编号
    private String cataId;
    //机构状态 1正常 2删除
    private Integer isDel;
    //是否启用 1启用 2禁用
    private Integer isFlag;
    //创建时间
    private Date createTime;

}
