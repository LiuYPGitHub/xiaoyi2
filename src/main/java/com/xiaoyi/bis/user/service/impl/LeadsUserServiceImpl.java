package com.xiaoyi.bis.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.common.dict.dao.DictCodeMapper;
import com.xiaoyi.bis.common.dict.domain.Dict;
import com.xiaoyi.bis.common.dict.service.DictService;
import com.xiaoyi.bis.common.enums.DataStatus;
import com.xiaoyi.bis.common.service.CacheService;
import com.xiaoyi.bis.common.utils.MD5Util;
import com.xiaoyi.bis.common.utils.SnowflakeIdGenerator;
import com.xiaoyi.bis.common.utils.StringUtils;
import com.xiaoyi.bis.system.manager.UserManager;
import com.xiaoyi.bis.user.bean.LoginUserBean;
import com.xiaoyi.bis.user.dao.FollowMapper;
import com.xiaoyi.bis.user.dao.LeadsUserInfoMapper;
import com.xiaoyi.bis.user.dao.LeadsUserMapper;
import com.xiaoyi.bis.user.domain.Follow;
import com.xiaoyi.bis.user.domain.LeadsUser;
import com.xiaoyi.bis.user.domain.LeadsUserInfo;
import com.xiaoyi.bis.user.service.LeadsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description：leads
 * @Author：kk
 * @Date：2019/8/29 13:34
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LeadsUserServiceImpl extends ServiceImpl<LeadsUserMapper, LeadsUser> implements LeadsUserService {

    private @Autowired
    CacheService cacheService;
    private @Autowired
    UserManager userManager;
    private @Autowired
    LeadsUserInfoMapper leadsUserInfoMapper;
    private @Autowired
    LeadsUserMapper leadsUserMapper;
    private @Autowired
    FollowMapper followMapper;
    private @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;
    private @Autowired
    DictService dictService;
    private @Autowired
    DictCodeMapper dictMapper;

    private @Value("${file.urlPrefix}")
    String urlPrefix;
    private @Value("${file.displayImg}")
    String displayImg;

    @Override
    @Transactional
    public LeadsUser register(String telNum) throws Exception {

        final LeadsUserInfo leadsUserInfo = new LeadsUserInfo();
        leadsUserInfo.setUserInfoId(snowflakeIdGenerator.nextId() + "");
        leadsUserInfo.setIsDelete(DataStatus.Enable.getValue());
        leadsUserInfoMapper.insert(leadsUserInfo);

        final LeadsUser leadsUser = new LeadsUser();
        leadsUser.setUserId(snowflakeIdGenerator.nextId() + "");
        leadsUser.setTelNum(telNum);
        leadsUser.setUserState("1");
        leadsUser.setUserInfoId(leadsUserInfo.getUserInfoId());
        leadsUser.setIsDelete(DataStatus.Enable.getValue());
        this.save(leadsUser);

        // 将用户相关信息保存到 Redis中
        userManager.loadUserRedisCache(leadsUser);
        return leadsUser;
    }

    @Override
    public LeadsUser findByTel(String tel) {
        return baseMapper.selectOne(new LambdaQueryWrapper<LeadsUser>().eq(LeadsUser::getTelNum, tel).eq(LeadsUser::getIsDelete, "1"));
    }

    @Override
    public void chooseType(String userId, String codeId) throws Exception {

        final String codeNo = dictService.getCodeNo(codeId);
        final LeadsUser leadsUser = baseMapper.selectById(userId);
        leadsUser.setUserType(codeNo);
        if (StringUtils.equals(codeNo, "3")) {
            final LeadsUserInfo leadsUserInfo = leadsUserInfoMapper.selectById(leadsUser.getUserInfoId());
            leadsUserInfo.setIsOrg("1");
            leadsUserInfo.setIsAccount("1");
            leadsUserInfo.setOrgState("1");
            leadsUserInfoMapper.updateById(leadsUserInfo);
            // 关联机构
            leadsUser.setOrgId(leadsUser.getUserInfoId());
        }
        baseMapper.updateById(leadsUser);
        // 重新缓存用户信息
        cacheService.saveSysUser(leadsUser.getTelNum());
    }

    @Override
    public boolean updatePassword(String telNum, String password) throws Exception {

        // final LeadsUser currentUser = FebsUtil.getCurrentUser();
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("tel_num", telNum);
        columnMap.put("is_delete", DataStatus.Enable.getValue());
        final List<LeadsUser> leadsUsers = baseMapper.selectByMap(columnMap);

        if (CollectionUtils.isNotEmpty(leadsUsers)) {
            final LeadsUser leadsUser = leadsUsers.get(0);
            /*if (StringUtils.isEmpty(leadsUser.getPassword())) {
                return false;
            }*/
            leadsUser.setPassword(MD5Util.encrypt(password));
            this.baseMapper.update(leadsUser, new LambdaQueryWrapper<LeadsUser>().eq(LeadsUser::getTelNum, telNum).eq(LeadsUser::getUserId, leadsUser.getUserId()));
            // 重新缓存用户信息
            cacheService.saveSysUser(telNum);
            return true;
        }
        return false;

    }

    @Override
    public LoginUserBean getLoginUser(String userId) {

        final LeadsUser leadsUser = baseMapper.selectById(userId);
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_info_id", leadsUser.getUserInfoId());
        final List<LeadsUserInfo> leadsUserInfos = leadsUserInfoMapper.selectByMap(columnMap);
        final LoginUserBean loginUserBean = new LoginUserBean();
        if (CollectionUtils.isNotEmpty(leadsUserInfos)) {
            final LeadsUserInfo leadsUserInfo = leadsUserInfos.get(0);
            loginUserBean.setTelNum(leadsUser.getTelNum());
            loginUserBean.setHeadImgUrl(StringUtils.isBlank(leadsUserInfo.getHeadImgUrl()) ? urlPrefix + displayImg : leadsUserInfo.getHeadImgUrl());
            loginUserBean.setRealName(leadsUserInfo.getRealName() == null? "姓名" :leadsUserInfo.getRealName());
            loginUserBean.setUserId(userId);
            loginUserBean.setRole(leadsUser.getRole());
            loginUserBean.setOrgCode(StringUtils.isBlank(leadsUserInfo.getUserInfoId()) ? "" : leadsUser.getOrgId());
            loginUserBean.setUserType(leadsUser.getUserType());
            loginUserBean.setPasswordTips(StringUtils.isEmpty(leadsUser.getPassword()) ? 1 : 2);
            loginUserBean.setUserTypeTips(StringUtils.isEmpty(leadsUser.getUserType()) ? 1 : 2);
            final Integer followCount = followMapper.selectCount(new LambdaQueryWrapper<Follow>().eq(Follow::getUserInfoId, userId).eq(Follow::getFollowState, "1"));
            loginUserBean.setFollowCount(followCount);
        }
        return loginUserBean;
    }

    @Override
    public LeadsUser getLeadsUser(String userId) {
        return leadsUserMapper.selectById(userId);
    }

    @Override
    public String getOrgState(String userInfoId) {

        final String orgState = leadsUserInfoMapper.selectById(userInfoId).getOrgState();
        return orgState;
    }

    @Override
    public List<Dict> getUserType() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("code_type", "user");
        columnMap.put("code_flay", DataStatus.Enable.getValue());
        final List<Dict> dicts = dictMapper.selectByMap(columnMap);
        return dicts;
    }
}
















