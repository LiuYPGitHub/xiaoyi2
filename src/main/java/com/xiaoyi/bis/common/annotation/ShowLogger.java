package com.xiaoyi.bis.common.annotation;

import java.lang.annotation.*;
/**
 * 展示日志
 * @author CJ
 * @date 2019/10/17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ShowLogger {

    String info();

}
