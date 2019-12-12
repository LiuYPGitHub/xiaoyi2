package com.xiaoyi.bis.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.user.controller.common.ResPageInfo;
import com.xiaoyi.bis.user.domain.Follow;
import com.xiaoyi.bis.user.bean.FollowBean;

public interface FollowService extends IService<Follow> {

    /**
     * 根据类型筛选
     *
     * @param pageNumb
     * @param pageSize
     * @return
     */
    ResPageInfo<FollowBean> getFollowList(String userId, String followType, int pageNumb, int pageSize);

    /**
     * 关注
     *
     * @param userId
     * @param followType
     * @param followUserId
     */
    void addFollow(String userId, String followType, String followUserId);

    /**
     * 取关
     *
     * @param userId
     * @param followUserId
     */
    void unFollow(String userId, String followUserId);

    /**
     * 取关
     *
     * @param followId
     */
    void unFollow( String followId);

    /**
     * 统计关注
     *
     * @param userId
     * @return
     */
    Integer countFollow(String userId);
}
