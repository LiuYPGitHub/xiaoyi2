package com.xiaoyi.bis.xiaoyi.util;

import com.xiaoyi.bis.xiaoyi.constant.ExceptionConstant;
import com.xiaoyi.bis.xiaoyi.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 文件处理工具类
 * @author CJ
 * @date 2019/10/17
 */
@Component
@Slf4j
public class FileUtil {

    @Value(value = "${xiaoYiFile.rootPath}")
    private String rootPath;
    @Value(value = "${xiaoYiFile.urlPrefix}")
    private String urlPrefix;
    @Value(value = "${xiaoYiFile.uploadPath}")
    private String uploadPath;

    private static FileUtil fileUtil;

    @PostConstruct
    public void init(){
        fileUtil=this;
    }

    /**
     * 下载本地文件
     * @param readPath
     * @param fileName
     * @throws IOException
     */
    public static void download(String readPath, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("content-type", "application/octet-stream");
        //response.setContentType("application/octet-stream");
        //response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        try {
            String browser = request.getHeader("User-Agent");
            if (-1 < browser.indexOf("MSIE 6.0") || -1 < browser.indexOf("MSIE 7.0")) {
                // IE6, IE7 浏览器
                response.setHeader("content-disposition", "attachment;filename="
                        + new String(fileName.getBytes(), "ISO8859-1"));
            } else if (-1 < browser.indexOf("MSIE 8.0")) {
                // IE8
                response.setHeader("content-disposition", "attachment;filename="
                        + URLEncoder.encode(fileName, "UTF-8"));
            } else if (-1 < browser.indexOf("MSIE 9.0")) {
                // IE9
                response.setHeader("content-disposition", "attachment;filename="
                        + URLEncoder.encode(fileName, "UTF-8"));
            } else if (-1 < browser.indexOf("Chrome")) {
                // 谷歌
                response.setHeader("content-disposition",
                        "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
            } else if (-1 < browser.indexOf("Safari")) {
                // 苹果
                response.setHeader("content-disposition", "attachment;filename="
                        + new String(fileName.getBytes(), "ISO8859-1"));
            } else {
                // 火狐或者其他的浏览器
                response.setHeader("content-disposition",
                        "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            InputStream inputStream=new FileInputStream(readPath);
            OutputStream outputStream = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) > 0) {
                //将缓冲区的数据输出到客户端浏览器
                outputStream.write(buffer,0,len);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        }catch (FileNotFoundException e){
            log.error("该文件不存在 readPath:"+readPath);
            throw new ServiceException(ExceptionConstant.EXCEPTION_FileError,"该文件不存在 readPath:"+readPath);
        } catch (IOException e) {
            log.error("文件操作异常 readPath:"+readPath);
            throw new ServiceException(ExceptionConstant.EXCEPTION_FileError,"文件操作异常 readPath:"+readPath);
        }
    }

    /**
     * 上传文件至本地
     * @param file
     * @return
     * @throws IOException
     */
    public static String upload(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + file.getOriginalFilename();
        String path = fileUtil.rootPath + fileUtil.uploadPath + fileName;
        file.transferTo(new File(path));
        log.info("图片位置:" + path);
        String resultPath =  fileUtil.urlPrefix + fileUtil.uploadPath + fileName;
        log.info("访问地址:" + resultPath);
        return resultPath;
    }

    /**
     * 下载远程mp4格式视频
     * @param httpUrl
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    public static boolean httpDownload(String httpUrl,String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        // 1.下载网络文件
        int byteRead;
        URL url=null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            //2.获取链接
            URLConnection conn = url.openConnection();
            //3.输入流
            InputStream inStream = conn.getInputStream();
            //3.写入文件
            OutputStream os = response.getOutputStream();

            byte[] buffer = new byte[1024];
            while ((byteRead = inStream.read(buffer)) != -1) {
                os.write(buffer, 0, byteRead);
            }
            inStream.close();
            os.close();
            return true;
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            log.error("文件不存在"+e.getMessage());
            return false;
        } catch (IOException e) {
            //e.printStackTrace();
            log.error("文件下载异常"+e.getMessage());
            return false;
        }
    }

}
