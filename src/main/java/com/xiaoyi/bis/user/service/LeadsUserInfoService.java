package com.xiaoyi.bis.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.user.domain.LeadsUserInfo;
import com.xiaoyi.bis.user.bean.SysUserInfoBean;

import java.util.List;


public interface LeadsUserInfoService extends IService<LeadsUserInfo> {

    /**
     * 完善用户信息
     *
     * @param sysUserInfoBean throws Exception
     */
    void updateUserInfo(SysUserInfoBean sysUserInfoBean) throws Exception;

    /**
     * 删除履历
     *
     * @param resumeId
     */
    void updateResume(String resumeId);

    /**
     * 通过id查找用户
     *
     * @param userId
     * @return LeadsUser
     */
    SysUserInfoBean findById(String userId) throws Exception;

    /**
     * 根据类型筛选kol，名师
     *
     * @param type
     * @return
     */
    List<LeadsUserInfo> getSysUserInfo(String type);
}
