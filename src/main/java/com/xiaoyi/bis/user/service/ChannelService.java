package com.xiaoyi.bis.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.user.domain.OrderChannel;

import java.util.List;

public interface ChannelService extends IService<OrderChannel> {

    List<OrderChannel> listChannel();

}
