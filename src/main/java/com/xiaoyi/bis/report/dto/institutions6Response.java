package com.xiaoyi.bis.report.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Setter
@Getter
public class institutions6Response implements Serializable {

    private List<String> institutions6;
    private List<String> totalSales;
    private List<String> totalSalesNo;
    private List<String> totalSalesYes;
}
