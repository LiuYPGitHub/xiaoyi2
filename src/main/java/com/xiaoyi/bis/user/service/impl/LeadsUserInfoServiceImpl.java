package com.xiaoyi.bis.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.common.dict.domain.Dict;
import com.xiaoyi.bis.common.dict.service.DictService;
import com.xiaoyi.bis.common.enums.DataStatus;
import com.xiaoyi.bis.common.enums.DictType;
import com.xiaoyi.bis.common.exception.FebsException;
import com.xiaoyi.bis.common.utils.SnowflakeIdGenerator;
import com.xiaoyi.bis.user.bean.SysUserInfoBean;
import com.xiaoyi.bis.user.dao.FollowMapper;
import com.xiaoyi.bis.user.dao.LeadsUserInfoMapper;
import com.xiaoyi.bis.user.dao.LeadsUserMapper;
import com.xiaoyi.bis.user.dao.ResumeMapper;
import com.xiaoyi.bis.user.domain.*;
import com.xiaoyi.bis.user.service.AreaService;
import com.xiaoyi.bis.user.service.LeadsUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description：leads
 * @Author：kk
 * @Date：2019/8/29 13:34
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LeadsUserInfoServiceImpl extends ServiceImpl<LeadsUserInfoMapper, LeadsUserInfo> implements LeadsUserInfoService {

    private @Autowired
    LeadsUserInfoMapper leadsUserInfoMapper;
    private @Autowired
    LeadsUserMapper leadsUserMapper;
    private @Autowired
    ResumeMapper resumeMapper;
    private @Autowired
    FollowMapper followMapper;
    private @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;
    private @Autowired
    DictService dictService;
    private @Autowired
    AreaService areaService;
    private @Autowired
    Mapper mapper;

    private @Value("${file.urlPrefix}")
    String urlPrefix;
    private @Value("${file.displayImg}")
    String displayImg;

    @Override
    public void updateUserInfo(SysUserInfoBean sysUserInfoBean) {

        final LeadsUserInfo userInfo = mapper.map(sysUserInfoBean, LeadsUserInfo.class);
        if (CollectionUtils.isNotEmpty(sysUserInfoBean.getResume())) {
            for (SysUserInfoBean.ResumeBean resumeBean : sysUserInfoBean.getResume()) {
                final Resume resume = mapper.map(resumeBean, Resume.class);
                resume.setUserInfoId(resumeBean.getUserId());
                resume.setIsDelete(DataStatus.Enable.getValue());
                if (StringUtils.isNotEmpty(resumeBean.getResumeId())) {
                    resumeMapper.updateById(resume);
                } else if (StringUtils.isNotEmpty(resumeBean.getResumeId()) && StringUtils.equals(resumeBean.getResumeState(), "2")) {
                    resume.setIsDelete(DataStatus.Delete.getValue());
                    resumeMapper.updateById(resume);
                } else {
                    resume.setResumeId(snowflakeIdGenerator.nextId() + "");
                    resumeMapper.insert(resume);
                }
            }
        }
        final String province = areaService.getAreaName(null, sysUserInfoBean.getProvince());
        final String city = areaService.getAreaName(null, sysUserInfoBean.getCity());
        userInfo.setCity(sysUserInfoBean.getCity());
        userInfo.setIsDelete(DataStatus.Enable.getValue());
        baseMapper.updateById(userInfo);
    }

    @Override
    public void updateResume(String resumeId) {

        final Resume resume = new Resume();
        resume.setResumeId(resumeId);
        resume.setIsDelete(DataStatus.Delete.getValue());
        resumeMapper.updateById(resume);

    }

    @Override
    public SysUserInfoBean findById(String userId) throws Exception {

        final LeadsUser leadsUser = leadsUserMapper.selectOne(new LambdaQueryWrapper<LeadsUser>().eq(LeadsUser::getUserId, userId).eq(LeadsUser::getIsDelete, DataStatus.Enable.getValue()));
        if (leadsUser == null) {
            throw new FebsException("该用户不存在或已删除");
        }
        Map<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("user_info_id", leadsUser.getUserInfoId());
        final List<LeadsUserInfo> leadsUserInfos = leadsUserInfoMapper.selectByMap(columnMap);
        if (CollectionUtils.isNotEmpty(leadsUserInfos)) {
            final LeadsUserInfo leadsUserInfo = leadsUserInfos.get(0);
            final SysUserInfoBean map = mapper.map(leadsUserInfo, SysUserInfoBean.class);
            final Dict dictCode = dictService.getDictCode(DictType.USER.getInfo(), leadsUser.getUserType());
            if (dictCode != null) {
                map.setUserTypeName(dictCode.getCodeName());
                map.setUserType(dictCode.getCodeId());
            }
            if (StringUtils.isNotEmpty(leadsUserInfo.getCity())) {
                final Area areaCode = areaService.getAreaCode(leadsUserInfo.getCity());
                if (areaCode != null) {
                    map.setCity(areaCode.getAreaName());
                    map.setProvince(StringUtils.equals(areaCode.getAreaParentCode(), "0") ? areaCode.getAreaName() : areaService.getAreaCode(areaCode.getAreaParentCode()).getAreaName());
                }
            }

            final Integer followCount = followMapper.selectCount(new LambdaQueryWrapper<Follow>().eq(Follow::getUserInfoId, userId).eq(Follow::getFollowState, "1"));
            map.setFollowCount(followCount);
            map.setRealName(StringUtils.isEmpty(leadsUserInfo.getRealName()) ? "姓名" : leadsUserInfo.getRealName());
            map.setNickName(StringUtils.isEmpty(leadsUserInfo.getNickName()) ? "昵称" : leadsUserInfo.getNickName());
            map.setHeadImgUrl(StringUtils.isBlank(leadsUserInfo.getHeadImgUrl()) ? urlPrefix + displayImg : leadsUserInfo.getHeadImgUrl());
            //履历
            final List<Resume> resumes = resumeMapper.selectList(new LambdaQueryWrapper<Resume>().eq(Resume::getUserInfoId, userId).eq(Resume::getIsDelete, DataStatus.Enable.getValue()).orderByDesc(Resume::getStartDate));
            if (CollectionUtils.isNotEmpty(resumes)) {
                final List<SysUserInfoBean.ResumeBean> collect1 = resumes.stream().map(res -> {
                    final SysUserInfoBean.ResumeBean resumeBean = new SysUserInfoBean.ResumeBean();
                    resumeBean.setCompany(res.getCompany());
                    resumeBean.setEndDate(res.getEndDate());
                    resumeBean.setStartDate(res.getStartDate());
                    resumeBean.setTitle(res.getTitle());
                    resumeBean.setUserId(userId);
                    resumeBean.setResumeId(res.getResumeId());
                    resumeBean.setResumeState("1");
                    return resumeBean;
                }).collect(Collectors.toList());
                map.setResume(collect1);
            } else {
                map.setResume(Collections.emptyList());
            }
            return map;
        }
        return null;
    }

    @Override
    public List<LeadsUserInfo> getSysUserInfo(String type) {

        Map<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("user_type", type);
        columnMap.put("is_delete", DataStatus.Enable.getValue());
        final List<LeadsUser> leadsUsers = leadsUserMapper.selectByMap(columnMap);
        final List<LeadsUserInfo> collect = leadsUsers.stream().map(e -> {
            final LeadsUserInfo leadsUserInfo = new LeadsUserInfo();
            leadsUserInfo.setUserId(e.getUserId());
            Map<String, Object> cMap = new HashMap<>(16);
            columnMap.put("user_info_id", e.getUserInfoId());
            final List<LeadsUserInfo> leadsUserInfos = baseMapper.selectByMap(cMap);
            if (CollectionUtils.isNotEmpty(leadsUserInfos)) {
                final LeadsUserInfo info = leadsUserInfos.get(0);
                leadsUserInfo.setPersonalInfo(info.getPersonalInfo());
                leadsUserInfo.setHeadImgUrl(StringUtils.isBlank(info.getHeadImgUrl()) ? urlPrefix + displayImg : info.getHeadImgUrl());
                leadsUserInfo.setRealName(info.getRealName());
                return leadsUserInfo;
            }
            return leadsUserInfo;
        }).collect(Collectors.toList());

        return collect;
    }


}

