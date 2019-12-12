package com.xiaoyi.bis.report.service;


import com.xiaoyi.bis.report.dto.CourseIndexResponse;
import com.xiaoyi.bis.report.dto.DateResponse;
import com.xiaoyi.bis.report.dto.institutions6Response;

/**
 * Report form
 */

public interface ReportService{

    int coPending(String orgCode);

    int online(String orgCode);

    int notThrough(String orgCode);

    int sumSales(String orgCode);

    int ownChannelSales(String orgCode);

    int wings(String orgCode);

    int students(String orgCode);

    int ownChannel(String orgCode);

    int wingsOwn(String orgCode);

    CourseIndexResponse CourseName(String orgCode);

    DateResponse dateFormat(String courseName,String orgCode);

    institutions6Response institutions6(String orgCode);

}
