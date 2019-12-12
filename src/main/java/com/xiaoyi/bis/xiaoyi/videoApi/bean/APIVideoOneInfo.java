package com.xiaoyi.bis.xiaoyi.videoApi.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author CJ
 * @date 2019/12/5
 */
@ToString
@Getter
@Setter
public class APIVideoOneInfo implements Serializable {

    private static final long serialVersionUID = -3959139849607719217L;
    @ApiModelProperty(value = "视频截图大图地址",notes = "视频截图大图地址")
    private List[] images_b;
    @ApiModelProperty(value = "视频截图",notes = "视频截图")
    private List[] images;
    @ApiModelProperty(value = "图片路径",notes = "图片路径")
    private List[] imageUrls;
    @ApiModelProperty(value = "视频标签",notes = "视频标签")
    private String tag;
    @ApiModelProperty(value = "视频地址",notes = "视频地址")
    private String mp4;
    @ApiModelProperty(value = "视频名称",notes = "视频名称")
    private String title;
    @ApiModelProperty(value = "视频码率数",notes = "视频码率数")
    private String df;
    @ApiModelProperty(value = "播放次数",notes = "播放次数")
    private Integer times;
    @ApiModelProperty(value = "视频编号",notes = "视频编号")
    private String vid;
    @ApiModelProperty(value = "分类id， 如1为根目录",notes = "分类id， 如1为根目录")
    private Integer cataid;
    @ApiModelProperty(value = "返回flash连接",notes = "返回flash连接")
    private String swf_link;
    @ApiModelProperty(value = "视频状态",notes = "60/61:已发布 10:等待编码 20:正在编码 50:等待审核 51:审核不通过 -1:已删除")
    private Integer status;
    @ApiModelProperty(value = "加密视频为1，非加密为0",notes = "加密视频为1，非加密为0")
    private String seed;
    @ApiModelProperty(value = "流畅码率mp4格式视频地址",notes = "流畅码率mp4格式视频地址")
    private String mp4_1;
    @ApiModelProperty(value = "流畅码率flv格式视频地址",notes = "流畅码率flv格式视频地址")
    private String flv1;
    @ApiModelProperty(value = "源文件",notes = "源文件")
    private String sourcefile;
    @ApiModelProperty(value = "视频宽度",notes = "视频宽度")
    private String playerwidth;
    @ApiModelProperty(value = "视频高度",notes = "视频高度")
    private String playerheight;
    @ApiModelProperty(value = "用户默认播放视频",notes = "用户默认播放视频")
    private String default_video;
    @ApiModelProperty(value = "时长",notes = "时长")
    private String duration;
    @ApiModelProperty(value = "视频首图",notes = "视频首图")
    private String first_image;
    @ApiModelProperty(value = "最佳分辨率",notes = "最佳分辨率")
    private String original_definition;
    @ApiModelProperty(value = "视频描述",notes = "视频描述")
    private String context;
    @ApiModelProperty(value = "视频上传日期",notes = "视频上传日期")
    private Date ptime;
    @ApiModelProperty(value = "源文件大小",notes = "源文件大小")
    private String source_filesize;
    @ApiModelProperty(value = "编码后各个清晰度视频的文件大小（单位：字节）",notes = "类型为array")
    private String[] filesize;
    @ApiModelProperty(value = "md5",notes = "md5")
    private String md5checksum;
    @ApiModelProperty(value = "索引文件",notes = "索引文件")
    private List[] hls;
    @ApiModelProperty(value = "预览视频id",notes = "预览视频id")
    private String previewVid;
    /*@ApiModelProperty(value = "上传者信息",notes = "上传者信息")
    private APIUploader uploader;*/
    @ApiModelProperty(value = "是否为源文件，否：0,是：1",notes = "否：0,是：1")
    private String keepsource;

}
