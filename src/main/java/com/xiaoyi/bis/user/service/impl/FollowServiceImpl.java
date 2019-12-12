package com.xiaoyi.bis.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.common.enums.DataStatus;
import com.xiaoyi.bis.common.enums.FollowStatus;
import com.xiaoyi.bis.common.utils.SnowflakeIdGenerator;
import com.xiaoyi.bis.user.bean.FollowBean;
import com.xiaoyi.bis.user.controller.common.ResPageInfo;
import com.xiaoyi.bis.user.dao.FollowMapper;
import com.xiaoyi.bis.user.dao.LeadsUserInfoMapper;
import com.xiaoyi.bis.user.dao.LeadsUserMapper;
import com.xiaoyi.bis.user.domain.Follow;
import com.xiaoyi.bis.user.domain.LeadsUser;
import com.xiaoyi.bis.user.domain.LeadsUserInfo;
import com.xiaoyi.bis.user.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description：机构关注
 * @Author：kk
 * @Date：2019/8/29 11:54
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    private @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;
    private @Autowired
    LeadsUserInfoMapper leadsUserInfoMapper;
    private @Autowired
    LeadsUserMapper leadsUserMapper;

    private @Value("${file.urlPrefix}")
    String urlPrefix;
    private @Value("${file.displayImg}")
    String displayImg;


    @Override
    public ResPageInfo<FollowBean> getFollowList(String userId, String followType, int pageNumb, int pageSize) {
        Page<Follow> page = new Page<>(pageNumb, pageSize);
        final IPage<Follow> followIPage = this.baseMapper.selectPage(page, new LambdaQueryWrapper<Follow>().eq(Follow::getUserInfoId, userId).eq(Follow::getFollowType, followType).eq(Follow::getFollowState, DataStatus.Enable.getValue()));
        final List<FollowBean> collect = followIPage.getRecords().stream().map(e -> {
            final FollowBean followBean = new FollowBean();
            followBean.setFollowId(e.getFollowId());
            followBean.setFollowState(e.getFollowState());

            final LeadsUser leadsUser = leadsUserMapper.selectById(e.getFollowUserId());
            final LeadsUserInfo leadsUserInfo = leadsUserInfoMapper.selectById(leadsUser.getUserInfoId());
            followBean.setFollowUserId(e.getFollowUserId());
            followBean.setFollowPersonalBg(leadsUserInfo.getPersonalBg());
            followBean.setFollowPersonInfo(leadsUserInfo.getPersonalInfo());
            followBean.setFollowUserName(StringUtils.isEmpty(leadsUserInfo.getRealName()) ? "姓名" : leadsUserInfo.getRealName());
            followBean.setFollowNickName(StringUtils.isEmpty(leadsUserInfo.getNickName()) ? "昵称" : leadsUserInfo.getNickName());
            followBean.setFollowHeadImg(StringUtils.isEmpty(leadsUserInfo.getHeadImgUrl()) ? urlPrefix + displayImg : leadsUserInfo.getHeadImgUrl());
            followBean.setType(e.getFollowType());
            followBean.setIsDelete(leadsUserInfo.getIsDelete());
            List<String> strings = new ArrayList<>();
            if (StringUtils.isNotEmpty(leadsUserInfo.getLabels())) {
                final List<String> stringList = JSON.parseArray(leadsUserInfo.getLabels(), String.class);
                strings = stringList.stream().limit(2).collect(Collectors.toList());
            }
            followBean.setLabels(StringUtils.isEmpty(leadsUserInfo.getLabels()) ? Collections.emptyList() : strings);
            return followBean;
        }).collect(Collectors.toList());

        final ResPageInfo<FollowBean> resPageInfo = new ResPageInfo<>();
        resPageInfo.setContent(collect);
        resPageInfo.setTotal(followIPage.getTotal());
        resPageInfo.setPages((int) followIPage.getPages());
        resPageInfo.setSize((int) followIPage.getSize());
        return resPageInfo;

    }


    @Override
    public void addFollow(String userId, String followType, String followUserId) {

        log.info("userId ,followUserId :{},{}" + userId, followUserId);
        // final LeadsUser currentUser = FebsUtil.getCurrentUser();
        final Follow follow = new Follow();
        follow.setFollowId(snowflakeIdGenerator.nextId() + "");
        follow.setUserInfoId(userId);
        follow.setFollowUserId(followUserId);
        follow.setFollowType(followType);
        follow.setFollowState(FollowStatus.Follow.getValue());
        follow.setIsDelete(DataStatus.Enable.getValue());
        //follow.setCreateBy(currentUser.getCreateBy());
        this.baseMapper.insert(follow);
    }

    @Override
    public void unFollow(String userId, String followUserId) {
        final Follow follow = new Follow();
        follow.setFollowState(FollowStatus.unFllow.getValue());
        baseMapper.update(follow, new LambdaQueryWrapper<Follow>().eq(Follow::getUserInfoId, userId).eq(Follow::getFollowUserId, followUserId));
        //this.baseMapper.updateById(follow);
    }


    @Override
    public void unFollow(String followId) {
        final Follow follow = new Follow();
        follow.setFollowId(followId);
        follow.setFollowState(FollowStatus.unFllow.getValue());
        baseMapper.updateById(follow);
    }

    @Override
    public Integer countFollow(String userId) {
        final Integer followCount = baseMapper.selectCount(new LambdaQueryWrapper<Follow>().eq(Follow::getUserInfoId, userId).eq(Follow::getFollowState, DataStatus.Enable.getValue()));
        return followCount;
    }
}
