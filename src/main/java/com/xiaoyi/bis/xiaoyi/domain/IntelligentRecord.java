package com.xiaoyi.bis.xiaoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 智能录播
 * @author CJ
 * @date 2019/11/5
 */
@ToString
@Getter
@Setter
@TableName(value = "ty_poly_record_course")
public class IntelligentRecord implements Serializable {

    private static final long serialVersionUID = 7402420892943532500L;
    @TableId(type= IdType.AUTO)
    @TableField(value = "`id`")
    private Integer id;
    private Date createdAt;
    private String orgCode;
    //视频状态(1未开始 2录制中 3录制完成)
    @TableField(value = "`video_status`")
    private String videoStatus;
    //课程状态(1待审核 2未通过 3已下架 4已上架)
    @TableField(value = "`status`")
    private Integer status;
    private String code;
   // private String classId;
    private String courseName;
    private String teacherName;
    //private String teacherInfo;
    @TableField(value = "`desc`", exist = false)
    private String desc;
    private Integer courseNum;
    //课程时长
    @TableField(exist = false)
    private Integer classHour;
    //课程分钟数
    @TableField(value = "`course_mins`")
    private Integer courseMins;
    //课程适用学生
    @TableField(value = "`class`")
    private String classInfo;
    //是否删除
    private Integer isDel;
    //是否显示(1显示 0不显示)
    private Integer isShow;
    //课程创建日期
    private java.util.Date regDate;
    //课程开始录制日期
    private java.util.Date startDate;
    //课程结束录制日期
    private java.util.Date endDate;
    private java.util.Date updatedAt;
    private String createMobile;
    //学生数
    private Integer studentNum;
    //分类编号
    @TableField(value = "cataId")
    private String cataId;
    //保利文件名称
    private String polyTitle;
    private String vid;
    @TableField(value = "fileSize")
    private String fileSize;
    @TableField(value = "duration")
    private String duration;
    @TableField(value = "sessionIds")
    private String sessionIds;
    @TableField(value = "fileId")
    private String fileId;
    @TableField(value = "channelId")
    private String channelId;
    @TableField(value = "mp4")
    private String mp4;
    @TableField(value = "m3u8")
    private String m3u8;
    private Integer isRevised;
    @TableField(value = "cover")
    private String cover;

    @TableField(exist = false)
    private PageResponse response;
    //智能录播关联课节信息
    @TableField(exist = false)
    private List<RecordDetail> details;
    //创建者
    private String creator;
}
