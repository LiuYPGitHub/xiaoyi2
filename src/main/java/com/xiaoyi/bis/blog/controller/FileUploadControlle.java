//package com.xiaoyi.bis.blog.controller;
//
//import com.xiaoyi.bis.common.domain.FebsResponse;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import net.coobird.thumbnailator.Thumbnails;
//import org.apache.commons.io.FileUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * @Description：上传文件
// * @Author：kk
// * @Date：2019/8/29 13:13
// */
//@Api(tags = "upload", value = "上传")
//@Slf4j
//@Controller
//@RequestMapping("/fileq")
//public class FileUploadControlle {
//
//    private @Value("${file.rootpath}")
//    String rootpath;
//    private @Value("${file.urlprefix}")
//    String urlprefix;
//    private @Value("${file.imgpath}")
//    String imgs;
//
//    @RequestMapping(value = "/qupload", method = RequestMethod.POST)
//    @ResponseBody
//    public FebsResponse upload(@RequestParam("file") MultipartFile file,
//                               HttpServletResponse response) {
//        long srt = System.currentTimeMillis();
//        log.info("文件上传请求开始");
//        try {
//            // 文件写入本地磁盘
//            final String filepath = rootpath + imgs;
//            final String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//            FileUtils.writeByteArrayToFile(new File(filepath + filename), file.getBytes());
//
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Content-Type", "application/json; Charset=utf-8");
//            long end = System.currentTimeMillis();
//            log.info("文件上传请求结束，写入磁盘（{}），耗时：{}ms", rootpath, (end - srt));
//
//            // 生成文件的访问路径
//            final String fileUrl = urlprefix + imgs + filename;
//            log.info("图片路径：{}", fileUrl);
//            return new FebsResponse().message("选择成功").data(fileUrl);
//
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return new FebsResponse().message("文件上传失败");
//        }
//    }
//
//    @ApiOperation(value = "关注 KOL，名师", notes = "关注 KOL，名师")
//    @RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
//    @ResponseBody
//    public FebsResponse imgUpload(@RequestParam("file") MultipartFile file) {
//        // 文件写入本地磁盘
//        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");
//        final String dateFormatPath = simpleDateFormat.format(new Date());
//        final String filepath = rootpath + dateFormatPath;
//        final String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename() + ".jpg";
//        //String name =file.getOriginalFilename();
//        //String prefix=name.substring(name.lastIndexOf(".")+1);
//        //String filename=System.currentTimeMillis()+"."+prefix;
//        log.info("图片路径：{}", filepath + filename);
//        File dest = new File(filepath + filename); // 保存位置
//        try {
//            // 先尝试压缩并保存图片
//            Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(dest);
//        } catch (IOException e) {
//            try {
//                // 失败了再用springmvc自带的方式
//                FileUtils.writeByteArrayToFile(dest, file.getBytes());
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//        return new FebsResponse().message("选择成功").data(urlprefix + dateFormatPath + filename);
//
//    }
//
//}
