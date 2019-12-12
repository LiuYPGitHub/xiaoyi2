package com.xiaoyi.bis.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.user.dao.ChannelMapper;
import com.xiaoyi.bis.user.domain.OrderChannel;
import com.xiaoyi.bis.user.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：通道
 * @Author：kk
 * @Date：2019/11/1 10:57
 */
@Slf4j
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, OrderChannel> implements ChannelService {

    @Override
    public List<OrderChannel> listChannel() {

        final List<OrderChannel> channels = baseMapper.selectList(new LambdaQueryWrapper<OrderChannel>());
        return channels;
    }
}
