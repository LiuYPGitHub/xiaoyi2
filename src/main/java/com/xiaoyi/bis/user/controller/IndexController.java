package com.xiaoyi.bis.user.controller;

import com.alibaba.fastjson.JSON;
import com.xiaoyi.bis.common.controller.BaseController;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.common.utils.JsonMapper;
import com.xiaoyi.bis.user.bean.ResUserInfo;
import com.xiaoyi.bis.user.bean.SysUserInfoBean;
import com.xiaoyi.bis.user.bean.SysUserInfoListBean;
import com.xiaoyi.bis.user.controller.common.ResPageInfo;
import com.xiaoyi.bis.user.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Collections;

/**
 * @Description：首页
 * @Author：kk
 * @Date：2019/8/29 13:13
 */
@Api(tags = "index", value = "KOL,名师")
@Slf4j
@Validated
@RestController
@RequestMapping("/web")
public class IndexController extends BaseController {

    private @Autowired
    IndexService indexService;

    @ApiOperation(value = "KOL，名师列表", notes = "类型切换 KOL，名师列表")
    @GetMapping("/index/list")
    public AjaxResult followList(@RequestParam String type,
                                 @RequestParam("pageNumb") Integer pageNumb,
                                 @RequestParam("pageSize") Integer pageSize){

        final ResPageInfo<SysUserInfoListBean> sysUserInfoList = indexService.getIndexList(type, pageNumb, pageSize);
        return AjaxResult.success(sysUserInfoList);
    }

    @ApiOperation(value = " KOL，名师详情", notes = "KOL，名师详情")
    @GetMapping("index/getUserInfo")
    public AjaxResult getUserInfo(@NotBlank(message = "{required}") @RequestParam String userId,
                                  @NotBlank(message = "{required}") @RequestParam String otherId) {
        log.info("KOL，名师详情入参：{},{}" + userId, otherId);
        final SysUserInfoBean sysUserInfo = indexService.getSysUserInfo(userId, otherId);
        final ResUserInfo resUserInfo = JsonMapper.map(sysUserInfo, ResUserInfo.class);
        resUserInfo.setLabels(StringUtils.isEmpty(sysUserInfo.getLabels()) ? Collections.emptyList() : JSON.parseArray(sysUserInfo.getLabels(), String.class));
        return AjaxResult.success(resUserInfo);
    }

}
