package com.xiaoyi.bis.user.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.blog.util.DateFormat;
import com.xiaoyi.bis.blog.util.DateUtils;
import com.xiaoyi.bis.common.controller.BaseController;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.common.exception.FebsException;
import com.xiaoyi.bis.user.bean.ExcelOrder;
import com.xiaoyi.bis.user.bean.ResOrder;
import com.xiaoyi.bis.user.controller.common.ResPageInfo;
import com.xiaoyi.bis.user.domain.Order;
import com.xiaoyi.bis.user.domain.OrderChannel;
import com.xiaoyi.bis.user.service.ChannelService;
import com.xiaoyi.bis.user.service.OrderService;
import com.xiaoyi.bis.user.utils.excel.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description：订单
 * @Author：kk
 * @Date：2019/10/1 10:13
 */
@Api(tags = "order", value = "订单")
@Slf4j
@Validated
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private @Autowired
    OrderService orderService;
    private @Autowired
    ChannelService channelService;
    private @Autowired
    Mapper mapper;

    @ApiOperation(value = "订单列表", notes = "订单列表")
    @GetMapping("/list")
    @ResponseBody
    public AjaxResult orderList(@RequestParam(value = "channelName", required = false) String channelName,
                                @RequestParam(value = "courseName", required = false) String courseName,
                                @RequestParam(value = "userMobile", required = false) String userMobile,
                                @RequestParam(value = "payStatus", required = false) String payStatus,
                                @RequestParam(value = "orderBy", required = false) String orderBy,
                                @RequestParam(value = "orderStartDate", required = false) String orderStartDate,
                                @RequestParam(value = "orderEndDate", required = false) String orderEndDate,
                                @RequestParam(value = "orgCode") String orgCode,
                                @RequestParam("pageNumb") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        log.info("订单列表入参:{} ", payStatus);
        Date start = null;
        Date end = null;
        if (StringUtils.isNotBlank(orderStartDate) && StringUtils.isNotBlank(orderEndDate)) {
            start = DateUtils.getStartDate(DateUtils.stringToDate(orderStartDate, DateFormat.DEFAULT));
            end = DateUtils.getFinallyDate(DateUtils.stringToDate(orderEndDate, DateFormat.DEFAULT));
        }
        log.info("时间:{} " + start);
        final List<Order> orders = orderService.listOrder(channelName, courseName, userMobile, payStatus, start, end, orderBy, orgCode, pageNum, pageSize);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        final List<ResOrder> collect = orders.stream().map(e -> {
            final ResOrder map = mapper.map(e, ResOrder.class);
            return map;
        }).collect(Collectors.toList());

        final ResPageInfo<ResOrder> resPageInfo = new ResPageInfo<>();
        mapper.map(pageInfo, resPageInfo);
        resPageInfo.setContent(collect);
        return AjaxResult.success(resPageInfo);
    }

    @ApiOperation(value = "导出订单", notes = "导出订单")
    @GetMapping("excel")
    public AjaxResult export(@RequestParam(value = "channelName", required = false) String channelName,
                             @RequestParam(value = "courseName", required = false) String courseName,
                             @RequestParam(value = "userMobile", required = false) String userMobile,
                             @RequestParam(value = "payStatus", required = false) String payStatus,
                             @RequestParam(value = "orderBy", required = false) String orderBy,
                             @RequestParam(value = "orderStartDate", required = false) String orderStartDate,
                             @RequestParam(value = "orderEndDate", required = false) String orderEndDate,
                             @RequestParam(value = "orgCode") String orgCode,
                             HttpServletResponse response) throws FebsException {

        Date start = null;
        Date end = null;
        if (StringUtils.isNotBlank(orderStartDate) && StringUtils.isNotBlank(orderEndDate)) {
            start = DateUtils.getStartDate(DateUtils.stringToDate(orderStartDate, DateFormat.DEFAULT));
            end = DateUtils.getFinallyDate(DateUtils.stringToDate(orderEndDate, DateFormat.DEFAULT));
        }
        try {
            final List<Order> orders = orderService.listOrder(channelName, courseName, userMobile, payStatus, start, end, orderBy, orgCode, null, null);
            final List<ExcelOrder> collect = orders.stream().map(e -> {
                final ExcelOrder map = mapper.map(e, ExcelOrder.class);
                return map;
            }).collect(Collectors.toList());
            ExcelUtil.simpleWrite(response, collect, new ExcelOrder(), "用户列表");
            return AjaxResult.success();

        } catch (Exception e) {
            log.error("导出Excel失败{}", e.getMessage());
            throw new FebsException("导出Excel失败");
        }

    }

    @ApiOperation(value = "渠道列表", notes = "渠道列表")
    @GetMapping("channel/list")
    @ResponseBody
    public AjaxResult channelList() {

        final List<OrderChannel> orderChannels = channelService.listChannel();
        return AjaxResult.success(orderChannels);
    }

}
