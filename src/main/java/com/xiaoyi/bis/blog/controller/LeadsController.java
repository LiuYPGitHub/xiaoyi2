package com.xiaoyi.bis.blog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.bis.blog.domain.Leads;
import com.xiaoyi.bis.blog.domain.LeadsLeIntenType;
import com.xiaoyi.bis.blog.service.LeadsService;
import com.xiaoyi.bis.common.controller.BaseController;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.common.utils.JsonMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collections;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 个人中心-资源管理-LEADS库
 * ping
 */
@Slf4j
@Validated
@RestController
@Api(value = "个人中心-资源管理-LEADS库", tags = {"leads"})
@RequestMapping("/leads")
public class LeadsController extends BaseController {

    @Autowired
    private LeadsService leadsService;
    @Autowired
    private Mapper mapper;

    /**
     * LEADS库 & 模糊查询
     *
     * @param realName
     * @param leParName
     * @param leParPhone
     * @param leChiName
     * @param leSchool
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "LEADS库 & 模糊查询", notes = "LEADS库 & 模糊查询")
    @RequestMapping(value = "/{realName}", method = RequestMethod.GET)
    public AjaxResult findrealName(@PathVariable String realName, String leParName, String leParPhone, String leChiName, String leSchool, int pageNum, int pageSize) {
        return AjaxResult.success(leadsService.getRealName(realName, leParName, leParPhone, leChiName, leSchool, pageNum, pageSize));
    }

    /**
     * 删除LEADS库
     *
     * @param leId
     * @throws
     */
    @ApiOperation(value = "删除LEADS库")
    @RequestMapping(value = "/delect", method = RequestMethod.PUT)
    public AjaxResult deleteLeads(@RequestParam String leId) {
        try {
            leadsService.updateLeads(leId);
            return AjaxResult.success("删除LEADS库成功");
        } catch (Exception e) {
            return AjaxResult.success("删除LEADS库失败");
        }
    }

    /**
     * LEADS库-编辑-回显
     *
     * @param leId
     * @return
     */
    @ApiOperation(value = "LEADS库-编辑-回显")
    @GetMapping("/echo")
    public AjaxResult getReleaseEcho(@RequestParam String leId) {
        Leads le = leadsService.finddEchoById(leId);
        LeadsLeIntenType leadsLeIntenType = JsonMapper.map(le, LeadsLeIntenType.class);
        leadsLeIntenType.setLeIntenType(StringUtils.isEmpty(le.getLeIntenType()) ? Collections.emptyList() : JSON.parseArray(le.getLeIntenType(), String.class));
        return AjaxResult.success(leadsLeIntenType);
    }

    /**
     * 个人中心-资源管理-添加LEADS库
     *
     * @param leads
     */
    @ApiOperation(value = "添加LEADS库", notes = "添加LEADS库")
    @PostMapping("/add")
    public AjaxResult addEvent(@RequestBody LeadsLeIntenType leads) {
        try {
            Leads le = mapper.map(leads, Leads.class);
            le.setLeIntenType(JSON.toJSONString(leads.getLeIntenType()));
            leadsService.addLeads(le);
            return AjaxResult.success("添加LEADS库成功");
        } catch (Exception e) {
            return AjaxResult.error("添加LEADS库失败");
        }
    }

    /**
     * 个人中心-资源管理-LEADS修改
     *
     * @param leads
     * @return
     */
    @ApiOperation(value = "个人中心-资源管理-LEADS修改", notes = "个人中心-资源管理-LEADS修改")
    @PutMapping(value = "/echo/update")
    public AjaxResult updateleads(@RequestBody LeadsLeIntenType leads) {
        try {
            Leads le = mapper.map(leads, Leads.class);
            le.setLeIntenType(JSON.toJSONString(leads.getLeIntenType()));
            leadsService.updateleads(le);
            return AjaxResult.success("LEADS修改成功");
        } catch (Exception e) {
            return AjaxResult.success("LEADS修改失败");
        }
    }

    /**
     * ping ecxel
     */
    int flag = 0;

    @ResponseBody
    @ApiOperation(value = "导入")
    @RequestMapping(value = "/withSimple", method = RequestMethod.POST)
    public String uploadExcel(@RequestParam("file") MultipartFile file, String createBy) {
        int count = 0;
        int code = 0;

        StringJoiner buffer = new StringJoiner("\n");
        JSONObject jsonObject = new JSONObject();
        try {
            String name = file.getOriginalFilename();
            if (name != null) {
                InputStream inputStream = file.getInputStream();
                Workbook book = null;
                if (isExcel2003(name)) {
                    book = new HSSFWorkbook(inputStream);
                }
                if (isExcel2007(name)) {
                    book = new XSSFWorkbook(inputStream);
                }
                Sheet sheet = book.getSheetAt(0);
                int allRowNum = sheet.getLastRowNum();
                if (allRowNum == 0) {
                    flag = 100;//flag是进度条的值
                    buffer.add("导入文件数据为空");
                }
                for (int i = 1; i <= allRowNum; i++) {
                    if (flag < 100) {
                        flag = flag + (100 / i);
                    } else {
                        flag = 100;
                    }
                    //加载状态值，当前进度
                    Leads le = new Leads();//我需要插入的数据类型
                    Row row = sheet.getRow(i); //获取第i行
                    if (row != null) {
                        Cell cell1 = row.getCell(0); //获取第1个单元格的数据
                        Cell cell2 = row.getCell(1);
                        Cell cell3 = row.getCell(2);
                        Cell cell4 = row.getCell(3);
//                        Cell cell5 = row.getCell(4);
//                        Cell cell6 = row.getCell(5);
                        Cell cell5 = row.getCell(4);
                        Cell cell6 = row.getCell(5);
                        Cell cell7 = row.getCell(6);
                        if (StringUtils.isEmpty(cell1.getStringCellValue())) {//家长姓名
                            buffer.add("第" + i + "行的第1列的数据不能为空");
                        } else {
                            le.setLeParName(cell1.getStringCellValue());
                        }
                        if (StringUtils.isEmpty(cell2.getStringCellValue())) {//家长电话
                            buffer.add("第" + i + "行的第2列的数据不能为空");
                        } else {
                            le.setLeParPhone(cell2.getStringCellValue());

                        }
                        if (StringUtils.isEmpty(cell3.getStringCellValue())) {//孩子姓名
                            buffer.add("第" + i + "行的第3列的数据不能为空");
                        } else {
                            le.setLeChiName(cell3.getStringCellValue());

                        }
                        if (StringUtils.isEmpty(cell4.getStringCellValue())) {//孩子年龄
                            buffer.add("第" + i + "行的第4列的数据不能为空");
                        } else {
                            le.setLeChiAge(cell4.getStringCellValue());

                        }
//                        if (StringUtils.isEmpty(cell5.getStringCellValue())) {//孩子性别
//                            buffer.add("第" + i + "行的第5列的数据不能为空");
//                        } else {
//                            le.setLeChiGen(cell5.getStringCellValue());
//
//                        }
//                        if (StringUtils.isEmpty(cell6.getStringCellValue())) {//就读年级
//                            buffer.add("第" + i + "行的第6列的数据不能为空");
//                        } else {
//                            le.setGrade(cell6.getStringCellValue());
//                        }
                        if (StringUtils.isEmpty(cell5.getStringCellValue())) {//就读学校
                            buffer.add("第" + i + "行的第5列的数据不能为空");
                        } else {
                            le.setLeSchool(cell5.getStringCellValue());

                        }
                        if (StringUtils.isEmpty(cell6.getStringCellValue())) {//所在城市（省市区？
                            buffer.add("第" + i + "行的第6列的数据不能为空");
                        } else {
                            le.setCityInput(cell6.getStringCellValue());

                        }
                        if (StringUtils.isEmpty(cell7.getStringCellValue())) {//意向课程
                            buffer.add("第" + i + "行的第7列的数据不能为空");
                        } else {
                            String[] aa = cell7.getStringCellValue().split("-");
                            String as = JSON.toJSONString(aa);
                            String a2 = as.replace("[", "").replace("]", "").replaceAll("\"", "");
                            int douQiqn = a2.indexOf(",");
                            String dd = getSubUtilSimple(as, ",(.*?),");
                            int douZhong = dd.replaceAll("\"", "").length();
                            int zong = a2.length();
                            int douHou = a2.lastIndexOf(",");
                            douHou = zong - douHou;
                            if (douQiqn <= 10 && douZhong <= 10 && douHou <= 10) {
                                le.setLeIntenType(cell7.getStringCellValue());
                            } else {
                                buffer.add("第" + i + "行的第7列的数据异常,里面每个小标签限1-10个字!");
                            }
                        }
                        if (le.getLeParName() != null && le.getLeParPhone() != null && le.getLeChiName() != null && le.getLeChiAge() != null  && le.getLeSchool() != null && le.getCityInput() != null && le.getLeIntenType() != null) {
                            count++;
                            leadsService.addLeadsExcel(le, createBy);
                        }
                    }
                }
                jsonObject.put("count", "共计" + allRowNum + "条数据，导入成功" + count + "条数据，导入失败" + (allRowNum - count) + "条");
                code = 200;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObject.put("code", code);
        jsonObject.put("message", buffer.toString());
        return jsonObject.toString();
    }

    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public static String getSubUtilSimple(String soap,String rgex){
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while(m.find()){
            return m.group(1);
        }
        return "";
    }

    @ApiOperation(value = "LEADS查看")
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public AjaxResult view(String leId) {
        Leads le = leadsService.view(leId);
        LeadsLeIntenType leadsLeIntenType = JsonMapper.map(le, LeadsLeIntenType.class);
        leadsLeIntenType.setLeIntenType(StringUtils.isEmpty(le.getLeIntenType()) ? Collections.emptyList() : JSON.parseArray(le.getLeIntenType(), String.class));
        return AjaxResult.success(leadsLeIntenType);
    }

    /**
     * 定时年龄\年龄每年入学时间加一级
     */
//    @Scheduled(cron = "0 0/1 * * * ?")  // 0 0 0 1 9 ? *
//    public void timingGradeAge() {
//        leadsService.timingGradeAge();
//        log.info(">>>>>-升学年级和年龄同步完成-<<<<<");
//    }

}