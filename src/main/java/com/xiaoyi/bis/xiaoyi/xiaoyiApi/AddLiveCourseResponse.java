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
public class AddLiveCourseResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -6020679951401419894L;

    public AddLiveCourseResponse(String classId) {
        success();
        this.classId = classId;
    }

    @ApiModelProperty(value ="课程编号",notes = "课程编号",required = true)
    private String classId;

}
