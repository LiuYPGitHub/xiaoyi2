package com.xiaoyi.bis.xiaoyi.videoApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author CJ
 * @date 2019/10/24
 */
@ToString
@Getter
@Setter
public class FileForwardSaveRequest extends BaseCheck implements Serializable {

    private static final long serialVersionUID = 5489190436988023434L;

    @ApiModelProperty(value = "文件编号集合",notes = "文件编号集合",required = true,position = 2)
    private List<String> fileIds;
    @ApiModelProperty(value = "频道ID",notes = "频道ID")
    private String channelId;
    @ApiModelProperty(value = "转存到点播的目录ID",notes = "默认为点播的根目录ID",required = false)
    private String cataId;
    @ApiModelProperty(value = "合并成功或失败回调的url",notes = "合并成功或失败回调的url",required = false,position = 3)
    private String callbackUrl;

}
