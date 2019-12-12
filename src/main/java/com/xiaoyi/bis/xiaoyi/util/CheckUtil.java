package com.xiaoyi.bis.xiaoyi.util;

import com.xiaoyi.bis.user.domain.LeadsUser;
import com.xiaoyi.bis.user.service.LeadsUserService;
import com.xiaoyi.bis.xiaoyi.constant.ExceptionConstant;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourse;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourseDetail;
import com.xiaoyi.bis.xiaoyi.dto.AddLiveCourseRequest;
import com.xiaoyi.bis.xiaoyi.exception.ServiceException;
import com.xiaoyi.bis.xiaoyi.xiaoyiEnum.CourseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 检查参数工具类
 * @author CJ
 * @date 2019/10/17
 */
@Component
@Slf4j
public class CheckUtil {

    @Autowired
    private LeadsUserService leadsUserService;

    public boolean checkUserIsExists(String userId){
        LeadsUser leadsUser = leadsUserService.getLeadsUser(userId);
        return StringUtils.isEmpty(leadsUser)?false:true;
    }

    /**
     * 检查是否为空
     * @param num 统计数据
     * @return
     */
    public static int checkNumIsNull(Integer num){
        if(StringUtils.isEmpty(num)){
            return  0;
        }else {
            return num;
        }
    }

    /**
     * 检查编号是否合法
     * @param id 编号
     */
    public static void checkId(String id){
        if(StringUtils.isEmpty(id)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"用户编号参数为空");
        }
    }

    /**
     * byte数组转换成16进制字符串
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据文件流判断图片类型
     * @return jpg/png/gif/bmp
     */
    public static void checkImageFile(MultipartFile teachersFile,MultipartFile courseCaseFile) throws IOException {
        if (!checkImage((FileInputStream) teachersFile.getInputStream())) {
            String fileName = teachersFile.getOriginalFilename();
            log.error(fileName+"图片格式错误");
            throw new ServiceException(ExceptionConstant.EXCEPTION_FileError,fileName+"文件格式非法");
        }else if (!checkImage((FileInputStream) courseCaseFile.getInputStream())) {
            String fileName = courseCaseFile.getOriginalFilename();
            log.error(fileName+"图片格式错误");
            throw new ServiceException(ExceptionConstant.EXCEPTION_FileError,fileName+"文件格式非法");
        }
    }

    /**
     * 根据文件流判断图片类型
     * @return jpg/png/gif/bmp
     */
    public static void checkImageFile(MultipartFile teachersFile) throws IOException {
        if (!checkImage((FileInputStream) teachersFile.getInputStream())) {
            String fileName = teachersFile.getOriginalFilename();
            log.error(fileName+"图片格式错误");
            throw new ServiceException(ExceptionConstant.EXCEPTION_FileError,fileName+"文件格式非法");
        }
    }

    /**
     * 根据文件流判断图片类型
     * @param fis
     * @return jpg/png/gif/bmp
     */
    public static Boolean checkImage(FileInputStream fis) {
        boolean flag=false;
        //读取文件的前几个字节来判断图片格式
        byte[] b = new byte[4];
        try {
            fis.read(b, 0, b.length);
            String type = bytesToHexString(b).toUpperCase();
            if (type.contains("FFD8FF")) {
                flag = true;
            } else if (type.contains("89504E47")) {
                flag = true;
            } else if (type.contains("47494638")) {
                flag = true;
            } else if (type.contains("424D")) {
                flag = true;
            }else{
                flag = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 依据后缀名判断读取的是否为Excel文件
     * @param filePath
     * @return
     */
    public static  boolean checkExcel(String filePath) {
        if (filePath.matches("^.+\\.(?i)(xls)$") || filePath.matches("^.+\\.(?i)(xlsx)$")) {
            return true;
        }else{
            throw new ServiceException(ExceptionConstant.EXCEPTION_FileError,filePath+"文件格式非法");
        }
    }

    public static void checkAddLiveCourseRequest(MultipartFile liveCourseFile, MultipartFile teachersFile, MultipartFile courseCaseFile, AddLiveCourseRequest request) throws IOException {
        String fileName = liveCourseFile.getOriginalFilename();
        CheckUtil.checkExcel(fileName);
        CheckUtil.checkImageFile(teachersFile,courseCaseFile);
        if(StringUtils.isEmpty(liveCourseFile)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_FileError,"课程表格文件为空");
        }else if(StringUtils.isEmpty(teachersFile)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"直播课程教师图片为空");
        }else if(StringUtils.isEmpty(teachersFile.getOriginalFilename())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"直播课程教师图片为空");
        }else if(StringUtils.isEmpty(courseCaseFile)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"直播课程封面图片为空");
        }else if(StringUtils.isEmpty(courseCaseFile.getOriginalFilename())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"直播课程封面图片为空");
        }else if(StringUtils.isEmpty(request.getUserId())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"登录用户参数为空");
        }else if(StringUtils.isEmpty(request.getClassType())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"课程类型参数为空");
        }else if(StringUtils.isEmpty(request.getClassTags().size() <= 0)) {
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "标签参数为空");
        }else if(!CourseType.国际教育.name().equals(request.getClassType())&&!CourseType.学科教育.name().equals(request.getClassType())&&!CourseType.素质教育.name().equals(request.getClassType())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "课程类型参数非法");
        }
    }

    public static void checkAddLiveCourse(LiveCourse course){
        if(StringUtils.isEmpty(course.getCourseName())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "课程名称为空");
        }
        if(StringUtils.isEmpty(course.getPrice())||course.getPrice().compareTo(BigDecimal.ZERO)  == 0){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "课程售价为空");
        }
        if(StringUtils.isEmpty(course.getClassHour())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "课程时数为空");
        }
        if(StringUtils.isEmpty(course.getEnrollStartDate())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "报名开始时间为空");
        }
        if(StringUtils.isEmpty(course.getEnrollEndDate())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "报名结束时间为空");
        }
        if(StringUtils.isEmpty(course.getTeacherInfo())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "机构介绍为空");
        }
        if(StringUtils.isEmpty(course.getAccount())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "手机号码为空");
        }
        if(StringUtils.isEmpty(course.getTeacherName())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "教师名称为空");
        }
        if(StringUtils.isEmpty(course.getCourseFeature())||"||".equals(course.getCourseFeature())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "课程亮点为空");
        }
        if(StringUtils.isEmpty(course.getClassDifficult())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "课程难度为空");
        }
        if(StringUtils.isEmpty(course.getClassInfo())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "适合年级为空");
        }
        if(StringUtils.isEmpty(course.getCourseObj())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "学习目标为空");
        }
        if(StringUtils.isEmpty(course.getTextBook())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "配套讲义为空");
        }
        if(StringUtils.isEmpty(course.getCourseContent())){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "学习内容为空");
        }
        List<LiveCourseDetail> details = course.getLiveCourseDetails();
        if(StringUtils.isEmpty(details)||details.size()<=0){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "课节内容为空");
        }
        for (LiveCourseDetail detail : details) {
            if(StringUtils.isEmpty(detail.getLessonName())){
                throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "课节名称为空");
            }
            /*if(StringUtils.isEmpty(detail.getLessonDate())){
                throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "课节日期为空");
            }
            if(StringUtils.isEmpty(detail.getStartTime())){
                throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "课节开始时间为空");
            }
            if(StringUtils.isEmpty(detail.getEndTime())){
                throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL, "课节结束时间为空");
            }*/
        }
    }

}
