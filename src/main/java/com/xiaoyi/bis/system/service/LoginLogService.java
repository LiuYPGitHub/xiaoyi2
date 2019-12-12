package com.xiaoyi.bis.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.system.domain.LoginLog;

public interface LoginLogService extends IService<LoginLog> {

    void saveLoginLog (LoginLog loginLog);
}
