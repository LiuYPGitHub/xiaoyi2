package com.xiaoyi.bis.xiaoyi.controller;

import com.xiaoyi.bis.common.annotation.ShowLogger;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.xiaoyi.constant.ExceptionConstant;
import com.xiaoyi.bis.xiaoyi.dto.PlayerVideoResponse;
import com.xiaoyi.bis.xiaoyi.exception.ServiceException;
import com.xiaoyi.bis.xiaoyi.service.XiaoYiOrderService;
import com.xiaoyi.bis.xiaoyi.util.CheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 校翼-数据中心接口
 */
@Api(tags = {"dataCenter"},value = "校翼")
@RestController
@Validated
@Slf4j
public class DataCenterController {

    @Autowired
    private XiaoYiOrderService xiaoYiOrderService;

    @ApiOperation(value = "数据中心-销售报表",notes = "数据中心-销售报表")
    @ShowLogger(info = "数据中心-销售报表")
    @RequestMapping(path = "/xiaoyi/data",method = RequestMethod.GET)
    public AjaxResult dataList(String orgCode){
        //CheckUtil.checkId(userId); @NotBlank @PathVariable String userId   /{userId}
        /*String siteName = xiaoYiOrderService.queryOrderByUid(uId);
        if(StringUtils.isEmpty(siteName)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"用户信息为空");
        }*/
        return AjaxResult.success(xiaoYiOrderService.getOrder(orgCode));
    }

}
