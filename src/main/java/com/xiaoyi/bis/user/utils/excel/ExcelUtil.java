package com.xiaoyi.bis.user.utils.excel;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Description：excel
 * @Author：kk
 * @Date：2019/11/4 09:36
 */
public class ExcelUtil {

    public static void simpleWrite(HttpServletResponse response, List<?> data, Object object, String fileName) {

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileNameq = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileNameq + ".xlsx");
            EasyExcel.write(response.getOutputStream(), object.getClass()).sheet(fileName).doWrite(data);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
/*

    public static void simpleRead(String fileName) {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, StudentBean.class, new DemoDataListener()).sheet().doRead();
    }
*/

    /**
     * 将文件输出到浏览器（导出文件）
     *
     * @param response 响应
     */
    private static void getOutputStream(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        fileName = new String((fileName + System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

    }


}
