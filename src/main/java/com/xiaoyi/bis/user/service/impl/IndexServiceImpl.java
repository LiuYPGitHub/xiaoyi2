package com.xiaoyi.bis.web.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.xiaoyi.bis.blog.dao.DynamicImgMapper;
import com.xiaoyi.bis.blog.dao.DynamicMapper;
import com.xiaoyi.bis.blog.domain.Dynamic;
import com.xiaoyi.bis.blog.domain.DynamicImg;
import com.xiaoyi.bis.common.enums.CooperationStatus;
import com.xiaoyi.bis.common.enums.DataStatus;
import com.xiaoyi.bis.common.enums.FollowStatus;
import com.xiaoyi.bis.common.utils.SnowflakeIdGenerator;
import com.xiaoyi.bis.user.bean.SysUserInfoBean;
import com.xiaoyi.bis.user.bean.SysUserInfoListBean;
import com.xiaoyi.bis.user.controller.common.ResPageInfo;
import com.xiaoyi.bis.user.dao.*;
import com.xiaoyi.bis.user.domain.*;
import com.xiaoyi.bis.user.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description：首页KOL，名师
 * @Author：kk
 * @Date：2019/8/29 13:34
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class IndexServiceImpl extends ServiceImpl<IndexMapper, LeadsUserInfo> implements IndexService {
    private @Autowired
    LeadsUserMapper leadsUserMapper;
    private @Autowired
    LeadsUserInfoMapper leadsUserInfoMapper;
    private @Autowired
    FollowMapper followMapper;
    private @Autowired
    ResumeMapper resumeMapper;
    private @Autowired
    DynamicMapper dynamicMapper;
    private @Autowired
    DynamicImgMapper dynamicImgMapper;
    private @Autowired
    CooperationMapper cooperationMapper;
    private @Autowired
    DynamicIndexMapper dynamicIndexMapper;
    private @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;

    private @Value("${file.urlPrefix}")
    String urlPrefix;
    private @Value("${file.displayImg}")
    String displayImg;

    @Override
    public ResPageInfo<SysUserInfoListBean> getIndexList(String type, int pageNumb, int pageSize) {
        PageHelper.startPage(pageNumb, pageSize);
        final List<DynamicIndex> dynamicIndices = dynamicIndexMapper.selectList(new LambdaQueryWrapper<DynamicIndex>()
                .eq(DynamicIndex::getDynaType, type)
                .eq(DynamicIndex::getDynaStatus, "0")
                .eq(DynamicIndex::getIsDelete, DataStatus.Enable.getValue())
                .orderByDesc(DynamicIndex::getUpdateTime));

        final List<SysUserInfoListBean> collect = dynamicIndices.stream().map(e -> {
            final SysUserInfoListBean sysUserInfoListBean = new SysUserInfoListBean();
            final List<DynamicImg> dynamicImgs = dynamicImgMapper.selectList(new LambdaQueryWrapper<DynamicImg>().eq(DynamicImg::getDynaId, e.getDynaId())
                    .eq(DynamicImg::getIsDelete, DataStatus.Enable.getValue()).orderByDesc(DynamicImg::getCreateTime));

            sysUserInfoListBean.setDynamicImg(CollectionUtils.isNotEmpty(dynamicImgs) ? urlPrefix + displayImg : dynamicImgs.get(0).getImgUrl());
            sysUserInfoListBean.setDynamicContent(e.getDynaContent());

            final LeadsUser leadsUser = leadsUserMapper.selectById(e.getUserId());
            final List<LeadsUserInfo> leadsUserInfos = leadsUserInfoMapper.selectList(new LambdaQueryWrapper<LeadsUserInfo>().eq(LeadsUserInfo::getUserInfoId, leadsUser.getUserInfoId()));
            leadsUserInfos.stream().findFirst().ifPresent(ev -> {
                        sysUserInfoListBean.setPersonalInfo(ev.getPersonalInfo());
                        sysUserInfoListBean.setUserId(e.getUserId());
                        sysUserInfoListBean.setUserInfoId(ev.getUserInfoId());
                        sysUserInfoListBean.setRealName(StringUtils.isEmpty(ev.getRealName()) ? "姓名" : ev.getRealName());
                        sysUserInfoListBean.setNickName(StringUtils.isBlank(ev.getRealName()) ? "昵称" : ev.getNickName());
                        sysUserInfoListBean.setHeadImgUrl(StringUtils.isBlank(ev.getHeadImgUrl()) ? urlPrefix + displayImg : ev.getHeadImgUrl());
                        List<String> strings = new ArrayList<>();
                        if (StringUtils.isNotEmpty(ev.getLabels())) {
                            final List<String> stringList = JSON.parseArray(ev.getLabels(), String.class);
                            strings = stringList.stream().limit(2).collect(Collectors.toList());
                        }
                        sysUserInfoListBean.setLabels(StringUtils.isEmpty(ev.getLabels()) ? Collections.emptyList() : strings);
                    }
            );
            return sysUserInfoListBean;
        }).collect(Collectors.toList());

        PageInfo<DynamicIndex> appsPageInfo = new PageInfo<>(dynamicIndices);
        final ResPageInfo<SysUserInfoListBean> resPageInfo = new ResPageInfo<>();
        resPageInfo.setContent(collect);
        resPageInfo.setTotal(appsPageInfo.getTotal());
        resPageInfo.setPages(appsPageInfo.getPages());
        resPageInfo.setSize(appsPageInfo.getSize());
        return resPageInfo;

    }

    @Override
    public void saveOrUpdateDynamicIndex(String userId, String dynaId, String dynaContent, String dynaStatus, String dynaType) {

        final List<DynamicIndex> dynamicIndices = dynamicIndexMapper.selectList(new LambdaQueryWrapper<DynamicIndex>().eq(DynamicIndex::getUserId, userId));
        if (CollectionUtils.isNotEmpty(dynamicIndices) && StringUtils.equals("0", dynaStatus)) {
            final DynamicIndex dynamicIndex = new DynamicIndex();
            dynamicIndex.setUserId(userId);
            dynamicIndex.setDynaId(dynaId);
            dynamicIndex.setDynaContent(dynaContent);
            dynamicIndex.setDynaType(dynaType);
            dynamicIndex.setDynaStatus(dynaStatus);
            dynamicIndex.setIsDelete(DataStatus.Enable.getValue());
            dynamicIndexMapper.update(dynamicIndex, new LambdaQueryWrapper<DynamicIndex>().eq(DynamicIndex::getUserId, userId));
        } else if (StringUtils.equals("0", dynaStatus)) {
            final DynamicIndex dynamicIndex = new DynamicIndex();
            dynamicIndex.setDynamicId(snowflakeIdGenerator.nextId() + "");
            dynamicIndex.setUserId(userId);
            dynamicIndex.setDynaId(dynaId);
            dynamicIndex.setDynaContent(dynaContent);
            dynamicIndex.setDynaType(dynaType);
            dynamicIndex.setDynaStatus(dynaStatus);
            dynamicIndex.setIsDelete(DataStatus.Enable.getValue());
            dynamicIndexMapper.insert(dynamicIndex);
        }
    }


    @Override
    public void updateDynamicIndex(String dynaId) {

        final DynamicIndex dynamicIndex = new DynamicIndex();
        dynamicIndex.setDynaId(dynaId);
        dynamicIndex.setIsDelete(DataStatus.Delete.getValue());
        dynamicIndexMapper.update(dynamicIndex, new LambdaQueryWrapper<DynamicIndex>().eq(DynamicIndex::getDynaId, dynaId));

        //dynaId 判断首页动态 有 获取最新的第二条数据 保存到 DynamicIndex
        final List<DynamicIndex> dynamicIndices = dynamicIndexMapper.selectList(new LambdaQueryWrapper<DynamicIndex>().eq(DynamicIndex::getDynaId, dynaId));
        dynamicIndices.stream().findFirst().ifPresent(e -> {
            final List<Dynamic> dynamics = dynamicMapper.selectList(new LambdaQueryWrapper<Dynamic>()
                    .eq(Dynamic::getUserId, e.getUserId())
                    .eq(Dynamic::getDynaStatus, "0")
                    .eq(Dynamic::getDynaCheckSta, "0")
                    .eq(Dynamic::getIsDelete, DataStatus.Enable.getValue())
                    .orderByDesc(Dynamic::getUpdateTime));

            dynamics.stream().filter(ev -> !ev.getDynaId().equals(dynaId)).findFirst().ifPresent(ev -> {
                final DynamicIndex dy = new DynamicIndex();
                dy.setUserId(e.getUserId());
                dy.setDynaId(ev.getDynaId());
                dy.setDynaContent(ev.getDynaContent());
                dy.setIsDelete(DataStatus.Enable.getValue());
                dy.setUpdateTime(ev.getUpdateTime());
                dynamicIndexMapper.update(dy, new LambdaQueryWrapper<DynamicIndex>().eq(DynamicIndex::getUserId, e.getUserId()));
            });
        });

    }

    @Override
    public void releaseDynamic(String dynaId) {

        final DynamicIndex entity = new DynamicIndex();
        entity.setDynaId(dynaId);
        entity.setDynaStatus("0");
        dynamicIndexMapper.update(entity, new LambdaQueryWrapper<DynamicIndex>().eq(DynamicIndex::getDynaId, dynaId));

        // DynamicIndex 有数据 发布更新 无 保存
        final List<Dynamic> dynamics = dynamicMapper.selectList(new LambdaQueryWrapper<Dynamic>().eq(Dynamic::getDynaId, dynaId));
        dynamics.stream().findFirst().ifPresent(e -> {
            final List<DynamicIndex> dynamicIndices = dynamicIndexMapper.selectList(new LambdaQueryWrapper<DynamicIndex>().eq(DynamicIndex::getUserId, e.getUserId()));
            if (CollectionUtils.isNotEmpty(dynamicIndices)) {
                final DynamicIndex dynamicIndex = new DynamicIndex();
                dynamicIndex.setUserId(e.getUserId());
                dynamicIndex.setDynaId(e.getDynaId());
                dynamicIndex.setDynaContent(e.getDynaContent());
                dynamicIndex.setDynaType(String.valueOf(e.getDynaType()));
                dynamicIndex.setDynaStatus("0");
                dynamicIndex.setIsDelete(DataStatus.Enable.getValue());
                dynamicIndex.setUpdateTime(e.getUpdateTime());
                dynamicIndexMapper.update(dynamicIndex, new LambdaQueryWrapper<DynamicIndex>().eq(DynamicIndex::getUserId, e.getUserId()));
            } else {
                final DynamicIndex dynamicIndex = new DynamicIndex();
                dynamicIndex.setDynamicId(snowflakeIdGenerator.nextId() + "");
                dynamicIndex.setUserId(e.getUserId());
                dynamicIndex.setDynaId(e.getDynaId());
                dynamicIndex.setDynaContent(e.getDynaContent());
                dynamicIndex.setDynaType(String.valueOf(e.getDynaType()));
                dynamicIndex.setDynaStatus("0");
                dynamicIndex.setIsDelete(DataStatus.Enable.getValue());
                dynamicIndexMapper.insert(dynamicIndex);
            }
        });

    }

    @Override
    public SysUserInfoBean getSysUserInfo(String userId, String otherId) {

        Map<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("user_id", otherId);
        columnMap.put("is_delete", DataStatus.Enable.getValue());
        final List<LeadsUser> leadsUsers = leadsUserMapper.selectByMap(columnMap);
        final List<SysUserInfoBean> collect = leadsUsers.stream().map(e -> {
            final SysUserInfoBean sysUserInfoBean = new SysUserInfoBean();
            sysUserInfoBean.setUserId(e.getUserId());
            Map<String, Object> cMap = new HashMap<>(16);
            cMap.put("user_info_id", e.getUserInfoId());
            final List<LeadsUserInfo> leadsUserInfos = baseMapper.selectByMap(cMap);
            if (CollectionUtils.isNotEmpty(leadsUserInfos)) {
                final LeadsUserInfo info = leadsUserInfos.get(0);
                //关注状态
                Map<String, Object> coMap = Maps.newHashMap();
                coMap.put("user_info_id", userId);
                coMap.put("follow_user_id", otherId);
                coMap.put("follow_state", FollowStatus.Follow.getValue());
                final List<Follow> follows = followMapper.selectByMap(coMap);
                // 2 未关注
                sysUserInfoBean.setFollowState(CollectionUtils.isNotEmpty(follows) ? "1" : "2");
                sysUserInfoBean.setLabels(info.getLabels());
                //合作状态
                Map<String, Object> colMap = new HashMap<>(16);
                colMap.put("org_id", userId);
                colMap.put("cooperation_obj_id", otherId);
                colMap.put("cooperation_obj_state", CooperationStatus.Cooperation.getValue());
                final List<Cooperation> cooperation = cooperationMapper.selectByMap(colMap);
                // 2 未合作
                sysUserInfoBean.setCooperationState(CollectionUtils.isNotEmpty(cooperation) ? "1" : "2");

                sysUserInfoBean.setRealName(StringUtils.isEmpty(info.getRealName()) ? "姓名" : info.getRealName());
                sysUserInfoBean.setNickName(StringUtils.isEmpty(info.getNickName()) ? "昵称" : info.getNickName());
                sysUserInfoBean.setHeadImgUrl(StringUtils.isBlank(info.getHeadImgUrl()) ? urlPrefix + displayImg : info.getHeadImgUrl());
                sysUserInfoBean.setPersonalInfo(info.getPersonalInfo());
                sysUserInfoBean.setPersonalBg(info.getPersonalBg());
                sysUserInfoBean.setUserType(e.getUserType());
                //履历
                final List<Resume> resumes = resumeMapper.selectList(new LambdaQueryWrapper<Resume>().eq(Resume::getUserInfoId, otherId).eq(Resume::getIsDelete, DataStatus.Enable.getValue()).orderByDesc(Resume::getStartDate));
                if (CollectionUtils.isNotEmpty(resumes)) {
                    final List<SysUserInfoBean.ResumeBean> collect1 = resumes.stream().map(res -> {
                        final SysUserInfoBean.ResumeBean resumeBean = new SysUserInfoBean.ResumeBean();
                        /*if (res.getStartDate() != null && res.getEndDate() != null) {
                            final LocalDate startDate = DateUtils.UDateToLocalDate(res.getStartDate());
                            final LocalDate endDate = DateUtils.UDateToLocalDate(res.getEndDate());
                            Period periodToNextJavaRelease =
                                    Period.between(startDate, endDate);
                            String years = StringUtils.equals(String.valueOf(periodToNextJavaRelease.getYears()), "0") ? "" : periodToNextJavaRelease.getYears() + "年";
                            String months = StringUtils.equals(String.valueOf(periodToNextJavaRelease.getMonths()), "0") ? "" : periodToNextJavaRelease.getMonths() + "月";
                            resumeBean.setTotalDate(years + months);
                        }*/
                        resumeBean.setResumeId(res.getResumeId());
                        resumeBean.setCompany(res.getCompany());
                        resumeBean.setEndDate(res.getEndDate());
                        resumeBean.setStartDate(res.getStartDate());
                        resumeBean.setTitle(res.getTitle());
                        return resumeBean;
                    }).collect(Collectors.toList());
                    sysUserInfoBean.setResume(collect1);
                }
            }

            return sysUserInfoBean;
        }).collect(Collectors.toList());

        return collect.get(0);
    }
}
