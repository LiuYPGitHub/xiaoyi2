package com.xiaoyi.bis.xiaoyi.videoApi.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 文件目录节点
 * @author CJ
 * @date 2019/10/22
 */
@ToString
@Getter
@Setter
public class APINode implements Serializable {

    private static final long serialVersionUID = -1139222930852484185L;
    @ApiModelProperty(value = "分类名和该分类下视频总数的组合",notes = "分类名和该分类下视频总数的组合")
    private String text;
    @ApiModelProperty(value = "分类名",notes = "分类名")
    private String cataname;
    @ApiModelProperty(value = "分类树,显示从根目录到该目录每一层的分类id",notes = "分类树,显示从根目录到该目录每一层的分类id")
    private String catatree;
    @ApiModelProperty(value = "分类id,1根目录",notes = "分类id,1根目录")
    private String cataid;
    @ApiModelProperty(value = "上一级分类id，根目录的上一级分类为0",notes = "上一级分类id，根目录的上一级分类为0")
    private String parentid;
    @ApiModelProperty(value = "此分类及其子分类视频总数",notes = "此分类及其子分类视频总数")
    private String videos;
    @ApiModelProperty(value = "该分类的子分类",notes = "该分类的子分类")
    private List<APINode> nodes;

}
