package com.xiaoyi.bis.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.user.domain.Order;

import java.util.Date;
import java.util.List;

public interface OrderService extends IService<Order> {

    List<Order> listOrder(String channelName, String courseName, String userMobile, String payStatus, Date orderStartDate, Date orderEndDate, String orderBy, String orgCode, Integer pageNum, Integer pageSize);

}
