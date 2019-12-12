package com.xiaoyi.bis.xiaoyi.videoApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author CJ
 * @date 2019/10/23
 */
@ToString
@Getter
@Setter
public class MergeRecordFileRequest implements Serializable {

    private static final long serialVersionUID = 1621429985180625517L;
    @ApiModelProperty(value = "频道编号",notes = "频道编号",required = true,position = 1)
    private String channelId;
    @ApiModelProperty(value = "文件编号集合",notes = "文件编号集合",required = true,position = 2)
    private List<String> fileIds;
    @ApiModelProperty(value = "合并成功或失败回调的url",notes = "合并成功或失败回调的url",required = false,position = 3)
    private String callbackUrl;
    @ApiModelProperty(value = "传入Y,自动转存到对应点播分类下",notes = "(直播回放-频道号-场次)",required = false,position = 4)
    private String autoConvert;
    @ApiModelProperty(value = "y:合并MP4文件,n或者不传:合并m3u8文件",notes = "Y:合并MP4 N:合并m3u8",required = false,position = 5)
    private String mergeMp4;

}
