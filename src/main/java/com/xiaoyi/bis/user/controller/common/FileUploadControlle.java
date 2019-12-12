package com.xiaoyi.bis.user.controller.common;

import com.xiaoyi.bis.common.domain.FebsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description：上传文件
 * @Author：kk
 * @Date：2019/8/29 13:13
 */
@Api(tags = "upload", value = "上传")
@Slf4j
@Controller
@RequestMapping("/file")
public class FileUploadControlle {

    private @Value("${file.rootPath}")
    String rootPath;
    private @Value("${file.urlPrefix}")
    String urlPrefix;
    private @Value("${file.imgPath}")
    String imgs;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public FebsResponse upload(@RequestParam("file") MultipartFile file,
                               HttpServletResponse response) {
        long srt = System.currentTimeMillis();
        log.info("文件上传请求开始");
        try {
            // 文件写入本地磁盘
            final String filepath = rootPath + imgs;
            final String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            FileUtils.writeByteArrayToFile(new File(filepath + filename), file.getBytes());

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Content-Type", "application/json; Charset=utf-8");
            long end = System.currentTimeMillis();
            log.info("文件上传请求结束，写入磁盘（{}），耗时：{}ms", rootPath, (end - srt));

            // 生成文件的访问路径
            final String fileUrl = urlPrefix + imgs + filename;
            log.info("图片路径：{}", fileUrl);
            return new FebsResponse().message("选择成功").data(fileUrl);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new FebsResponse().message("文件上传失败");

        }
    }

    @ApiOperation(value = "上传图片", notes = "上传图片")
    @RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
    @ResponseBody
    public FebsResponse imgUpload(@RequestParam("file") MultipartFile file, String imgSource) {
        // 文件写入本地磁盘
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");
        final String dateFormatPath = simpleDateFormat.format(new Date());
        final String filepath = rootPath + imgs + imgSource + "/" + dateFormatPath;
       /* final String filename = System.currentTimeMillis() + ".jpg";*/
        String name = file.getOriginalFilename();
        String prefix = name.substring(name.lastIndexOf(".") + 1);
        String fileName = imgSource + "_" + System.currentTimeMillis() + "." + prefix;

        log.info("name:{},prefix:{},fileName:{}", name, prefix, fileName);
        File dest = new File(filepath + fileName);

        try {
            if ("gif".equals(prefix.toLowerCase())) { // gif压缩
                FileUtils.writeByteArrayToFile(dest, file.getBytes());
            } else {
                log.info("格式 jpeg:{}", prefix);
                Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(dest);
            }
        } catch (Exception e) {
            try {
                log.info("保存:{}", prefix);
                FileUtils.writeByteArrayToFile(dest, file.getBytes());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        return new FebsResponse().message("上传成功").data(urlPrefix + imgs + imgSource + "/" + dateFormatPath + (fileName));

    }

}
