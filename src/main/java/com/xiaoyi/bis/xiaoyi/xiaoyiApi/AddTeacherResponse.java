package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

import com.xiaoyi.bis.xiaoyi.videoApi.BaseResponse;
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
public class AddTeacherResponse extends BaseResponse implements Serializable{

    private static final long serialVersionUID = -361950491916298320L;

    public AddTeacherResponse(String teacherId) {
        success();
        this.teacherId = teacherId;
    }

    @ApiModelProperty(value ="接口状态码",notes = "1成功,0失败",required = true)
    private String returnCode;
    @ApiModelProperty(value ="接口信息",notes = "接口信息",required = true)
    private String returnMessage;
    @ApiModelProperty(value ="教师编号",notes = "教师编号",required = true)
    private String teacherId;

}
