package com.xiaoyi.bis.xiaoyi.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/11/4
 */
@ToString
@Getter
@Setter
public class StudentBean implements Serializable {

    private static final long serialVersionUID = 7724758636904808571L;
    private String recordId;
    private String studentName;
    private String studentPhone;

}
