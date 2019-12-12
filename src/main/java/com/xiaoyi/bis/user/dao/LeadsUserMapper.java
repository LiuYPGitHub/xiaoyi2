package com.xiaoyi.bis.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyi.bis.user.domain.LeadsUser;

public interface LeadsUserMapper extends BaseMapper<LeadsUser> {

    /**
     * 获取单个用户详情
     *
     * @param telNum 手机号
     * @return 用户信息
     */
    LeadsUser findSysUserDetail(String telNum);
}