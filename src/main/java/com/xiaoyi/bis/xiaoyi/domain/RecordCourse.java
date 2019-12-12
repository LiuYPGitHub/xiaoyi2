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
 * 录播
 * @author CJ
 * @date 2019/11/4
 */
@ToString
@Getter
@Setter
@TableName(value = "ty_record_course")
public class RecordCourse implements Serializable {

    private static final long serialVersionUID = 7402420892943532500L;
    @TableId(value = "`id`",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "`created_at`")
    private Date createdAt;
    @TableField(value = "`org_code`")
    private String orgCode;
    @TableField(value = "`status`")
    //课程状态(1待审核 2未通过 3已下架 4已上架)
    private Integer status;
    @TableField(value = "`code`")
    private String code;
    @TableField(value = "`class_id`")
    private String classId;
    @TableField(value = "`course_name`")
    private String courseName;
    @TableField(value = "`teacher_name`")
    private String teacheName;
    @TableField(value = "`teacher_info`")
    private String teacherInfo;
    @TableField(value = "`desc`")
    private String desc;
    private String classType;
    @TableField(exist = false)
    private List<String> classTags;
    private String classTag;
    private String classDifficult;
    @TableField(exist = false)
    private Integer classDifficultCount;
    private String coverImg;
    private String courseIntroduceImg;
    private Integer courseNum;
    private Integer classHour;
    private String courseObj;
    //课程特色
    private String courseFeature;
    //课程内容
    private String courseContent;
    //课程适用学生
    @TableField(value = "`class`")
    private String classInfo;
    //讲义/教材
    @TableField(value = "`textbook`")
    private String textBook;
    //课程售价
    @TableField(value = "`price`")
    private BigDecimal price;
    //是否删除
    @TableField(value = "`is_del`")
    private Integer isDel;
    //是否上线
    @TableField(value = "`is_online`")
    private Integer isOnline;
    //是否显示(1显示 0不显示)
    @TableField(value = "`is_show`")
    private Integer isShow;
    //课程创建日期
    @TableField(value = "`reg_date`")
    private java.util.Date createDate;
    /*//课程开始日期
    @TableField(value = "`start_date`")
    private java.util.Date startDate;
    //课程结束日期
    @TableField(value = "`end_date`")
    private java.util.Date endDate;*/

    private java.util.Date updatedAt;
    //教师照片
    @TableField(value = "`teacher_img`")
    private String teacherImage;
    //是否显示评价
    private String showEvaluation;
    private String createMobile;
    private java.util.Date enrollStartDate;
    private java.util.Date enrollEndDate;
    private BigDecimal cost;
    //学生数
    private String studentNum;
    //分类编号
    @TableField(value = "cataId")
    private String cataId;
    //保利文件名称
    private String polyTitle;
    @TableField(value = "vid")
    private String vid;
    @TableField(value = "fileSize")
    private String fileSize;
    //课程有效期(单位:天)
    @TableField(value = "`expirationDuration`")
    private String expirationDuration;
    //保利威录播编号
    private Integer recordId;
    //录播合并前课节
    private String lessonIds;
    //视频类型 1完整 2精编
    @TableField(value = "`is_revised`")
    private Integer isRevised;
    //课程类型 1:直播 2:录播
    @TableField(exist = false)
    private Integer courseType;
    @TableField(exist = false)
    private PageResponse response;
    @TableField(exist = false)
    private List<RecordDetail> recordDetails;
    @TableField(value = "mp4")
    private String mp4;
    @TableField(value = "`creator`")
    private String createName;
    @TableField(value = "`max_count`")
    private Integer maxCount;
    @TableField(exist = false)
    private String copyUrl;

}
