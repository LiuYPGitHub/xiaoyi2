package com.xiaoyi.bis.xiaoyi.xiaoyiApi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/31
 */
@ToString
@Getter
@Setter
public class AddUserCourseRequest implements Serializable {

    private static final long serialVersionUID = 3573556292133116008L;

    private String classId;
    private String channel;
    private String mobilephone;

}
