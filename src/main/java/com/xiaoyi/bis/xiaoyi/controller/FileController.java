package com.xiaoyi.bis.xiaoyi.controller;

import com.xiaoyi.bis.common.annotation.ShowLogger;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.xiaoyi.constant.ExceptionConstant;
import com.xiaoyi.bis.xiaoyi.dto.GetLiveCourseCaseImageResponse;
import com.xiaoyi.bis.xiaoyi.exception.ServiceException;
import com.xiaoyi.bis.xiaoyi.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 校翼-文件相关接口
 * @author CJ
 * @date 2019/10/17
 */
@Api(tags = {"fileProcess"},value = "校翼")
@RestController
@Slf4j
public class FileController {

    //文件相关配置参数
    @Value(value = "${xiaoYiFile.rootPath}")
    private String rootPath;
    @Value(value = "${xiaoYiFile.urlPrefix}")
    private String urlPrefix;
    @Value(value = "${xiaoYiFile.filePath}")
    private String filePath;
    @Value(value = "${xiaoYiFile.liveImage}")
    private String liveImage;
    @Value(value = "${xiaoYiFile.recordImage}")
    private String recordImage;
    @Value(value = "${xiaoYiFile.studentTemplate}")
    private String studentTemplate;

    @Autowired
    private HttpServletRequest request;

    /**
     * 读取本地文件并下载
     */
    @ApiOperation(value = "下载直播课程封面示例图",notes = "下载直播课程封面示例图")
    @ShowLogger(info = "下载直播课程封面示例图")
    @RequestMapping(path = "/xiaoyi/file/download/liveImage",method = RequestMethod.GET)
    public void downloadCaseImage(HttpServletResponse response) throws IOException {
        //String read="http://120.132.68.197:8080/xy_temp/7451573528707_.pic_hd.jpg";
        log.info("直播课程封面示例图位置:"+rootPath + filePath + liveImage);
        FileUtil.download(rootPath + filePath + liveImage,"liveCourseCase.png",request,response);
    }

    /**
     * 读取本地文件并下载
     */
    @ApiOperation(value = "下载录播课程封面示例图",notes = "下载录播课程封面示例图")
    @ShowLogger(info = "下载录播课程封面示例图")
    @RequestMapping(path = "/xiaoyi/file/download/recordImage",method = RequestMethod.GET)
    public void downloadRecordImage(HttpServletResponse response) throws IOException {
        log.info("直播课程封面示例图位置:"+rootPath + filePath + recordImage);
        FileUtil.download(rootPath + filePath + recordImage,"recordCourseCase.png",request,response);
    }

    /**
     * 读取本地文件并下载
     */
    @ApiOperation(value = "下载导入学生填写模板",notes = "下载导入学生填写模板")
    @ShowLogger(info = "下载导入学生填写模板")
    @RequestMapping(path = "/xiaoyi/file/download/studentTemplate",method = RequestMethod.GET)
    public void downloadStudentTemplate(HttpServletResponse response) throws IOException {
        //String read="http://120.132.68.197:8080/xy_temp/student.xlsx";
        log.info("导入学生填写模板位置:"+rootPath + filePath + studentTemplate);
        FileUtil.download(rootPath + filePath + studentTemplate,"studentInputTemplate.xlsx",request,response);
    }

    @ApiOperation(value = "获取直播课程封面示例图",notes = "获取直播课程封面示例图")
    @ShowLogger(info = "获取直播课程封面示例图")
    @RequestMapping(path = "/xiaoyi/file/getLiveCourseCaseImage",method = RequestMethod.GET)
    public AjaxResult getLiveCourseCaseImage(){
        //String read="http://120.132.68.197:8080/xy_temp/7451573528707_.pic_hd.jpg";
        GetLiveCourseCaseImageResponse response=new GetLiveCourseCaseImageResponse();
        response.setImageUrl(urlPrefix + filePath + liveImage);
        log.info("直播课程封面示例图返回:"+urlPrefix + filePath + liveImage);
        return AjaxResult.success(response);
    }

    @ApiOperation(value = "下载远程mp4地址视频接口",notes = "下载远程mp4地址视频接口")
    @ShowLogger(info = "下载远程mp4地址视频接口")
    @RequestMapping(path = "/xiaoyi/file/downloadVideoFile",method = RequestMethod.GET)
    public void downloadVideoFile(@RequestParam String mp4,HttpServletResponse response) throws UnsupportedEncodingException {
        log.info("正在下载远程mp4地址视频:"+mp4);
        if(!StringUtils.isEmpty(mp4)){
            FileUtil.httpDownload(mp4,"video.mp4",response);
        }else{
            throw new ServiceException(ExceptionConstant.EXCEPTION_FileError,"MP4地址为空");
        }
    }

}
