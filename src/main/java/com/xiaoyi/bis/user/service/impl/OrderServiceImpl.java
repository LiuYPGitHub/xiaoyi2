package com.xiaoyi.bis.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.xiaoyi.bis.user.dao.OrderMapper;
import com.xiaoyi.bis.user.domain.Order;
import com.xiaoyi.bis.user.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description：订单
 * @Author：kk
 * @Date：2019/11/1 09:47
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public List<Order> listOrder(String channelName, String courseName, String userMobile, String payStatus, Date orderStartDate, Date orderEndDate, String orderBy, String orgCode, Integer pageNum, Integer pageSize) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(channelName))
            queryWrapper.lambda().like(Order::getChannelName, channelName);
        if (StringUtils.isNotBlank(userMobile))
            queryWrapper.lambda().like(Order::getUserMobile, userMobile);
        if (StringUtils.isNotBlank(courseName))
            queryWrapper.lambda().like(Order::getCourseName, courseName);
        if (StringUtils.isNotBlank(payStatus))
            queryWrapper.lambda().eq(Order::getPayStatus, payStatus);
        if (orderEndDate != null && orderStartDate != null) {
            queryWrapper.lambda().ge(Order::getOrderFinishDate, orderStartDate);
            queryWrapper.lambda().le(Order::getOrderFinishDate, orderEndDate);
        }
        if (StringUtils.equals("1", orderBy)) {
            queryWrapper.lambda().orderByAsc(Order::getOrderFinishDate);
        } else {
            queryWrapper.lambda().orderByDesc(Order::getOrderFinishDate);
        }
        queryWrapper.lambda().eq(Order::getOrgCode, orgCode);

        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        final List<Order> orders = baseMapper.selectList(queryWrapper);
        return orders;
    }
}
