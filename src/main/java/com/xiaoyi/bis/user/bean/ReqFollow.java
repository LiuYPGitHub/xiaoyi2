package com.xiaoyi.bis.user.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description：合作
 * @Author：kk
 * @Date：2019/8/30 14:03
 */
@Data
public class ReqFollow implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank
    private String userId;
    @NotBlank
    private String followUserId;
    private String followType;

}
