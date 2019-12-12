package com.xiaoyi.bis.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyi.bis.user.domain.Follow;

import java.util.List;

public interface FollowMapper extends BaseMapper<Follow> {

    /**
     * 关注列表
     */
    List<Follow> selectFollowList(Follow follow);

}