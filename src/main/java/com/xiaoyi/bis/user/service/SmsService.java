package com.xiaoyi.bis.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.user.domain.Sms;

public interface SmsService extends IService<Sms> {

     String sendSms(String phone, Integer code);
}
