package com.xiaoyi.bis.xiaoyi.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author CJ
 * @date 2019/10/12
 */
@ToString
@Getter
@Setter
public class ServiceException extends RuntimeException{

    private String code;
    private String mess;

    public ServiceException(String code, String message) {
        super(code +" "+ message);
        this.code=code;
        this.mess=message;
    }

}
