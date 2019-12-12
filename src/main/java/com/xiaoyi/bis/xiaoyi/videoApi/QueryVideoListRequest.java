package com.xiaoyi.bis.xiaoyi.videoApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * @author CJ
 * @date 2019/10/23
 */
@ToString
@Getter
@Setter
public class QueryVideoListRequest implements Serializable {

    private static final long serialVersionUID = -7269011535187576174L;

    @ApiModelProperty(value = "频道编号",notes = "频道编号",required = true,position = 1)
    private String channelId;
    @ApiModelProperty(value = "开始日期 yyyy-MM-dd",notes = "开始日期 yyyy-MM-dd",required = false,position = 2)
    private Date startDate;
    @ApiModelProperty(value = "结束日期 yyyy-MM-dd",notes = "结束日期 yyyy-MM-dd",required = false,position = 3)
    private Date endDate;
    @ApiModelProperty(value = "直播的场次ID",notes = "直播的场次ID",required = false,position = 4)
    private String sessionId;

}
