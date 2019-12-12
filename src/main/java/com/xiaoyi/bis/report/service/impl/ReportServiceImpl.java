package com.xiaoyi.bis.report.service.impl;

import com.xiaoyi.bis.report.dao.ReportMapper;
import com.xiaoyi.bis.report.domain.CourXy;
import com.xiaoyi.bis.report.domain.Orders;
import com.xiaoyi.bis.report.dto.CourseIndexResponse;
import com.xiaoyi.bis.report.dto.DateResponse;
import com.xiaoyi.bis.report.dto.institutions6Response;
import com.xiaoyi.bis.report.service.ReportService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Report form
 */

@Log4j2
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportMapper reportMapper;

    @Override
    public int coPending(String orgCode){
        int org = reportMapper.coPending(orgCode);
        return org;
    }

    @Override
    public int online(String orgCode){
        return reportMapper.online(orgCode);
    }

    @Override
    public int notThrough(String orgCode) {
        return reportMapper.notThrough(orgCode);
    }

    @Override
    public int sumSales(String orgCode) {
        int sa = reportMapper.sumSales(orgCode);
        return sa;
    }

    @Override
    public int ownChannelSales(String orgCode) {
        int ow =  reportMapper.ownChannelSales(orgCode);
        return ow;
    }

    @Override
    public int wings(String orgCode){
        int wi = reportMapper.wings(orgCode);
        return wi;
    }

    @Override
    public int students(String orgCode) {
        List<Orders> streams = reportMapper.students(orgCode);
        return streams.size();
    }

    @Override
    public int ownChannel(String orgCode){
        List<Orders> ownChannel = reportMapper.ownChannel(orgCode);
        return ownChannel.size();
    }

    @Override
    public int wingsOwn(String orgCode) {
        List<Orders> wingsOwn = reportMapper.wingsOwn(orgCode);
        return wingsOwn.size();
    }

    @Override
    public CourseIndexResponse CourseName(String orgCode) {
        CourseIndexResponse response = new CourseIndexResponse();
        List<String> names = new ArrayList<>();
        List<CourXy> courses = reportMapper.CourseName(orgCode);
        for (CourXy cour : courses) {
            names.add(cour.getCourseName());
        }
        List countCourseName = reportMapper.countCourseName(orgCode);
        Collections.reverse(names);
        Collections.reverse(countCourseName);
        response.setCourseNames(names);
        response.setCourseDatas(countCourseName);
        return response;
    }

    @Override
    public DateResponse dateFormat(String courseName,String orgCode){
        DateResponse date = new DateResponse();
        List<String> from = reportMapper.dateFormat();
        List<String> sales14 = reportMapper.sales14(courseName,orgCode);
        List<String> countSales14 = reportMapper.countSales14(courseName,orgCode);
        List<String> selectSiteName = reportMapper.selectSiteName(orgCode);
        date.setDateFormat(from);
        date.setSales14(sales14);
        date.setSelectSiteName(selectSiteName);
        date.setCountSales14(countSales14);
        return date;
    }

    @Override
    public institutions6Response institutions6(String orgCode){
        institutions6Response inst = new institutions6Response();
        List<String> institutions6= reportMapper.institutions6();
        List<String> totalSales = reportMapper.totalSales(orgCode);
        List<String> totalSalesNo = reportMapper.totalSalesNo(orgCode);
        List<String> totalSalesYes = reportMapper.totalSalesYes(orgCode);
        inst.setInstitutions6(institutions6);
        inst.setTotalSales(totalSales);
        inst.setTotalSalesNo(totalSalesNo);
        inst.setTotalSalesYes(totalSalesYes);
        return inst;
    }
}