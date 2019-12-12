package com.xiaoyi.bis.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.common.dict.domain.Dict;
import com.xiaoyi.bis.user.bean.LoginUserBean;
import com.xiaoyi.bis.user.domain.LeadsUser;

import java.util.List;


public interface LeadsUserService extends IService<LeadsUser> {

    /**
     * 注册用户
     *
     * @param telNum
     * @throws Exception
     */
    LeadsUser register(String telNum) throws Exception;

    /**
     * 通过tel查找用户
     *
     * @param tel
     * @return LeadsUser
     */
    LeadsUser findByTel(String tel);

    /**
     * 选择身份
     *
     * @param userId
     * @param codeId
     */
    void chooseType(String userId, String codeId) throws Exception;

    /**
     * 更新用户密码
     *
     * @param telNum   用户名
     * @param password 新密码
     */
    boolean updatePassword(String telNum, String password) throws Exception;

    /**
     * 通过 userId 查看登录用户
     *
     * @param userId
     * @return
     */
    LoginUserBean getLoginUser(String userId);

    /**
     * 通过 userId 获取粒子用户
     *
     * @param userId
     * @return
     */
    LeadsUser getLeadsUser(String userId);


    /**
     * 获取机构状态
     *
     * @param userInfoId
     * @return
     */
    String getOrgState(String userInfoId);

    /**
     * 选择身份类型
     *
     * @return
     */
    List<Dict> getUserType();
}
