package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/12
 */
@ToString
@Getter
@Setter
public class AddTeacherRequest implements Serializable {

    private static final long serialVersionUID = -7491570460208435503L;

    public AddTeacherRequest() {}

    public AddTeacherRequest(String userId,String siteName) {
        this.orient="暂无";
        this.note="暂无";
        this.userId = userId;
        this.siteName=siteName;
    }

    @ApiModelProperty(value ="帐号名,目前只支持手机号",notes = "帐号名,目前只支持手机号",required = true)
    private String userId;
    @ApiModelProperty(value ="机构名称",notes = "机构名称",required = true)
    private String siteName;
    @ApiModelProperty(value ="专业或方向",notes = "专业或方向",required = true)
    private String orient;
    @ApiModelProperty(value ="备注",notes = "备注",required = true)
    private String note;

}
