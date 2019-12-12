package com.xiaoyi.bis.user.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：合作
 * @Author：kk
 * @Date：2019/8/30 14:03
 */
@Data
public class CooperationBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cooperationId;
    private String orgId;
    private String orgName;
    private String orgTel;
    private String contacts;
    private String sex;
    private String codeId;
    private List<String> labels;
    private String cooperationObjId;
    private String cooperationObjType;
    private String cooperationObjInfo;
    private String cooperationObjName;
    private String cooperationObjHeadImg;
    private String cooperationObjState;
    private String cooperationObjNickName;
    private String info;
}
