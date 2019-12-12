package com.xiaoyi.bis.report.dao;


import com.xiaoyi.bis.report.domain.CourXy;
import com.xiaoyi.bis.report.domain.Orders;

import java.util.List;

/**
 * Report form
 */

public interface ReportMapper {

    int coPending(String orgCode);

    int online(String orgCode);

    int notThrough(String orgCode);

    int sumSales(String orgCode);

    int ownChannelSales(String orgCode);

    int wings(String orgCode);

    List<Orders> students(String orgCode);

    List<Orders> ownChannel(String orgCode);

    List<Orders> wingsOwn(String orgCode);

    List<CourXy> CourseName(String orgCode);

    List countCourseName(String orgCode);

    List<String> dateFormat();

    List<String> sales14(String courseName,String orgCode);

    List<String> countSales14(String courseName,String orgCode);

    List<String> institutions6();

    List<String> totalSales(String orgCode);

    List<String> totalSalesNo(String orgCode);

    List<String> totalSalesYes(String orgCode);

    List<String> selectSiteName(String orgCode);
}
