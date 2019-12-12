package com.xiaoyi.bis.user.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @Description：合作
 * @Author：kk
 * @Date：2019/8/30 14:03
 */
@Data
public class ReqCooperation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cooperationId;
    private String orgId;
    @Length(min=2, max=10, message="长度必须在2-10之间")
    private String orgName;
    private String orgTel;
    private String contacts;
    private String sex;
    private String codeId;
    private String cooperationObjId;
    private String cooperationObjState;
    private String cooperationObjType;
    private String info;
}
