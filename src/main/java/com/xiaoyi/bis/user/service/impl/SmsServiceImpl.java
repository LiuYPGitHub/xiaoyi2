package com.xiaoyi.bis.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.user.dao.SmsMapper;
import com.xiaoyi.bis.user.domain.Sms;
import com.xiaoyi.bis.user.service.SmsService;
import org.springframework.stereotype.Service;

/**
 * @Description：短信
 * @Author：kk
 * @Date：2019/8/28 14:40
 */
@Service
public class SmsServiceImpl extends ServiceImpl<SmsMapper, Sms> implements SmsService {

    @Override
    public String sendSms(String phone, Integer code) {

        return null;
    }
}
