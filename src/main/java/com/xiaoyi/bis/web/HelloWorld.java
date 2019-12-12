package com.xiaoyi.bis.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("校翼API接口文档")
@RestController
public class HelloWorld {

    /**
     * swagger例子
     */
    @ApiOperation(value = "测试swagger",notes="hello 入口")
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() {
        return "Hello Spring Boot!";
    }
}