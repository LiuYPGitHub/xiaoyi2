package com.xiaoyi.bis.xiaoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaoyi.bis.xiaoyi.dao.LiveOrderMapper;
import com.xiaoyi.bis.xiaoyi.dao.RecordCourseMapper;
import com.xiaoyi.bis.xiaoyi.dao.RecordDetailMapper;
import com.xiaoyi.bis.xiaoyi.domain.*;
import com.xiaoyi.bis.xiaoyi.dto.PageResponse;
import com.xiaoyi.bis.xiaoyi.dto.QueryRecordCourseRequest;
import com.xiaoyi.bis.xiaoyi.service.RecordCourseService;
import com.xiaoyi.bis.xiaoyi.util.FindUtil;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoTokenAPI;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoTokenRequest;
import com.xiaoyi.bis.xiaoyi.videoApi.QueryVideoTokenResponse;
import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ
 * @date 2019/11/7
 */
@Transactional
@Service
@Slf4j
public class RecordCourseServiceImpl extends ServiceImpl<RecordCourseMapper, RecordCourse> implements RecordCourseService {

    @Autowired
    private RecordCourseService recordCourseService;
    @Autowired
    private QueryVideoTokenAPI queryVideoTokenAPI;
    @Autowired
    private RecordCourseMapper recordCourseMapper;
    @Autowired
    private RecordDetailMapper recordDetailMapper;
    @Autowired
    private LiveOrderMapper liveOrderMapper;
    //录播课程添翼申学访问前缀
    @Value(value = "${tysxService.recordCourseAccessPrefix}")
    private String recordCourseAccessPrefix;
    /**
     * 课程标签处理
     * @param classTag
     * @return
     */
    @Override
    public List<String> classTagsProcess(String classTag) {
        if(!StringUtils.isEmpty(classTag)){
            String[] split = classTag.split("&");
            List<String> classTags=new ArrayList<>();
            for (String s : split) {
                if(!StringUtils.isEmpty(s)){
                    classTags.add(s);
                }
            }
            return classTags;
        }
        return null;
    }

    /**
     * 获取视频播放token
     * @param vid 保利威视频编号
     * @return
     */
    @Override
    public String getToken(String vid) {
        QueryVideoTokenResponse response = queryVideoTokenAPI.process(new QueryVideoTokenRequest(vid));
        if(StringUtils.isEmpty(response)){
            return null;
        }else{
            APIToken apiToken = response.getApiToken();
            if(StringUtils.isEmpty(apiToken)){
                return null;
            }else{
                return apiToken.getToken();
            }
        }
    }

    /**
     * 添加录播课程
     * @param recordCourse
     * @return
     */
    @Override
    public int saveRecordCourse(RecordCourse recordCourse) {
        return recordCourseMapper.insert(recordCourse);
    }

    /**
     * 修改录播课程
     * @param recordCourse
     * @return
     */
    @Override
    public int updateRecordCourse(RecordCourse recordCourse) {
        return recordCourseMapper.updateById(recordCourse);
    }

    /**
     * 根据智能录播编号获取数据
     * @param recordId
     * @return
     */
    @Override
    public RecordCourse selectByRecordId(Integer recordId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("record_id",recordId);
        queryWrapper.eq("is_online","2");
        return recordCourseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据编号获取数据
     * @param id
     * @return
     */
    @Override
    public RecordCourse selectById(Integer id) {
        RecordCourse course = recordCourseMapper.selectById(id);
        return course;
    }

    /**
     * 根据用户编号获取手机号码
     * @param uId
     * @return
     */
    @Override
    public String getMobileByUid(String uId) {
        return liveOrderMapper.selectMobileByUid(uId);
    }

    /**
     * 获取录播课程列表
     * @param request
     * @return
     */
    @Override
    public PageResponse queryRecordCourseByLike(QueryRecordCourseRequest request) {
        Page<RecordCourse> objects = PageHelper.startPage(request.getPageIndex(),request.getPageSize());
        QueryWrapper<RecordCourse> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(request.getCourseIsOnline())){
            Integer isOnline = request.getCourseIsOnline();
            if(isOnline!=null){
                queryWrapper.eq("is_online","1");
            }
        }
        if(!StringUtils.isEmpty(request.getStatus())&& request.getStatus() != -1){
            Integer status = request.getStatus();
            queryWrapper.eq("`status`",status);
        }
        if(!StringUtils.isEmpty(request.getOrdCode())){
            queryWrapper.eq("org_code",request.getOrdCode());
        }else{
            queryWrapper.eq("creator",request.getUserId());
        }
        //getQueryCourseLike(queryWrapper,request.getKey());
        if(!StringUtils.isEmpty(request.getKey())){
            queryWrapper.like("`course_name`",request.getKey());
            queryWrapper.or().like("`teacher_name`",request.getKey());
            if(!StringUtils.isEmpty(request.getCourseIsOnline())){
                Integer isOnline = request.getCourseIsOnline();
                if(isOnline!=null){
                    queryWrapper.eq("is_online","1");
                }
            }
            if(!StringUtils.isEmpty(request.getStatus())&& request.getStatus() != -1){
                Integer status = request.getStatus();
                queryWrapper.eq("`status`",status);
            }
            if(!StringUtils.isEmpty(request.getOrdCode())){
                queryWrapper.eq("org_code",request.getOrdCode());
            }else{
                queryWrapper.eq("creator",request.getUserId());
            }
        }
        if(!StringUtils.isEmpty(request.getOrderType()) && request.getOrderType() == 1){
            queryWrapper.orderByDesc("created_at");
        }else if(!StringUtils.isEmpty(request.getOrderType()) && request.getOrderType() == 2){
            queryWrapper.orderByAsc("created_at");
        }
        //queryWrapper.isNotNull("vid");
        queryWrapper.eq("is_online","1");
        queryWrapper.eq("is_del","1");
        queryWrapper.eq("is_show","1");
        recordCourseMapper.selectList(queryWrapper);
        PageResponse response=new PageResponse();
        response.setPageIndex(objects.getPageNum());
        response.setPageSize(objects.getPageSize());
        response.setPageCount(objects.getPages());
        response.setTotalCount((int) objects.getTotal());
        List<RecordCourse> recordCourses = objects.getResult();
        for (RecordCourse recordCourse : recordCourses) {
            recordCourse.setCourseType(2);
            //设置课程标签
            if(!StringUtils.isEmpty(recordCourse.getClassTag())){
                List<String> tagsProcess = recordCourseService.classTagsProcess(recordCourse.getClassTag());
                recordCourse.setClassTags(tagsProcess);
            }
            //设置课程星星数字等级
            String classDifficult = recordCourse.getClassDifficult();
            if(!StringUtils.isEmpty(classDifficult)){
                int count = FindUtil.findCount(classDifficult, "★");
                recordCourse.setClassDifficultCount(count);
            }
            if(!StringUtils.isEmpty(recordCourse.getClassId())){
                recordCourse.setCopyUrl(recordCourseAccessPrefix+recordCourse.getClassId());
            }
            //绑定相关课节
            QueryWrapper<RecordDetail> queryDetailWrapper=new QueryWrapper<>();
            queryDetailWrapper.eq("course_id",recordCourse.getId());
            List<RecordDetail> details = recordDetailMapper.selectList(queryDetailWrapper);
            recordCourse.setRecordDetails(details);
        }
        response.setList(recordCourses);
        return response;
    }

}
