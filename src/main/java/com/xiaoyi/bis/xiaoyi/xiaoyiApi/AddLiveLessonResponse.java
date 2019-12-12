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
public class AddLiveLessonResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 4565733746158118110L;

    public AddLiveLessonResponse(String lessonId) {
        success();
        this.lessonId = lessonId;
    }

    @ApiModelProperty(value ="课节编号",notes = "课节编号",required = true)
    private String lessonId;

}
