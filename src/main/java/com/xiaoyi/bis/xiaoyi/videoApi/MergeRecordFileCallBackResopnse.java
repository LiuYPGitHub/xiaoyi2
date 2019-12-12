package com.xiaoyi.bis.xiaoyi.videoApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/24
 */
@ToString
@Getter
@Setter
public class MergeRecordFileCallBackResopnse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 4660129178058658487L;

    private boolean flag;

}
