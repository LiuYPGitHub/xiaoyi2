package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

import com.xiaoyi.bis.xiaoyi.videoApi.BaseResponse;
import com.xiaoyi.bis.xiaoyi.xiaoyiApi.bean.APICourseDetail;
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
public class QueryLiveCourseDetailResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -362898019921373750L;

    public QueryLiveCourseDetailResponse(APICourseDetail course) {
        success();
        this.course = course;
    }

    @ApiModelProperty(value ="课程详情信息",notes = "课程详情信息",required = true)
    private APICourseDetail course;

}
