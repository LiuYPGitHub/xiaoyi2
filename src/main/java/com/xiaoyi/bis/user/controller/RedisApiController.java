package com.xiaoyi.bis.user.controller;

import com.xiaoyi.bis.common.service.RedisService;
import com.xiaoyi.bis.user.bean.ReqRedis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description：redis
 * @Author：kk
 * @Date：2019/10/24 10:08
 */
@Api(tags = "redis", value = "redis 信息")
@Slf4j
@Validated
@RestController
@RequestMapping("/api/redis")
@Deprecated
public class RedisApiController {

    private @Autowired
    RedisService redisService;

    @ApiOperation(value = "getInfo")
    @PostMapping("getInfo")
    public String getInfo(@RequestBody ReqRedis reqRedis) throws Exception {
        return this.redisService.get(reqRedis.getKey());
    }

    @ApiOperation(value = "setInfo")
    @PostMapping("setInfo")
    public String setInfo(@RequestBody ReqRedis reqRedis) throws Exception {
        return this.redisService.set(reqRedis.getKey(), reqRedis.getValue());
    }

}
