package com.xiaoyi.bis.report.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Setter
@Getter
public class CourseIndexResponse implements Serializable {

    private List<String> courseNames;
    private List<Integer> courseDatas;

}
