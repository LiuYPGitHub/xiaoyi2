package com.xiaoyi.bis.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.common.dict.dao.DictCodeMapper;
import com.xiaoyi.bis.common.dict.domain.Dict;
import com.xiaoyi.bis.common.dict.service.DictService;
import com.xiaoyi.bis.common.enums.CooperationStatus;
import com.xiaoyi.bis.common.enums.DataStatus;
import com.xiaoyi.bis.common.utils.SnowflakeIdGenerator;
import com.xiaoyi.bis.user.bean.CooperationBean;
import com.xiaoyi.bis.user.bean.CooperationUserInfoBean;
import com.xiaoyi.bis.user.dao.CooperationMapper;
import com.xiaoyi.bis.user.dao.LeadsUserInfoMapper;
import com.xiaoyi.bis.user.dao.LeadsUserMapper;
import com.xiaoyi.bis.user.domain.Cooperation;
import com.xiaoyi.bis.user.domain.LeadsUser;
import com.xiaoyi.bis.user.domain.LeadsUserInfo;
import com.xiaoyi.bis.user.service.CooperationService;
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
 * @Description：合作
 * @Author：kk
 * @Date：2019/8/30 11:20
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CooperationServiceImpl extends ServiceImpl<CooperationMapper, Cooperation> implements CooperationService {

    private @Autowired
    LeadsUserInfoMapper leadsUserInfoMapper;
    private @Autowired
    LeadsUserMapper leadsUserMapper;
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
    public void saveCooperation(CooperationBean cooperationBean) {

        final Cooperation cooperation = new Cooperation();
        cooperation.setCooperationId(snowflakeIdGenerator.nextId() + "");
        cooperation.setContacts(cooperationBean.getContacts());
        cooperation.setOrgName(cooperationBean.getOrgName());
        cooperation.setOrgId(cooperationBean.getOrgId());
        cooperation.setOrgTel(cooperationBean.getOrgTel());

        if (StringUtils.isNotBlank(cooperationBean.getCodeId())) {
            final String codeNo = dictService.getCodeNo(cooperationBean.getCodeId());
            cooperation.setType(codeNo);
        } else {
            cooperation.setType(cooperationBean.getCooperationObjType());
        }
        cooperation.setSex(cooperationBean.getSex());
        cooperation.setCooperationObjId(cooperationBean.getCooperationObjId());
        cooperation.setCooperationObjState(cooperationBean.getCooperationObjState());
        cooperation.setInfo(cooperationBean.getInfo());
        cooperation.setIsDelete(DataStatus.Enable.getValue());
        cooperation.setCooperationObjState(CooperationStatus.Cooperation.getValue());
        this.baseMapper.insert(cooperation);

    }

    @Override
    public CooperationBean getCooperationInfo(String userId, String cooperationObjId) {

        final CooperationBean cooperationBean = new CooperationBean();
        if (StringUtils.isNotEmpty(userId)) {
            final CooperationUserInfoBean cooperationUserInfoBean = getCooperationUserInfoBean(userId);
            cooperationBean.setOrgName(cooperationUserInfoBean.getUserName());
            cooperationBean.setOrgId(cooperationUserInfoBean.getUserId());
            cooperationBean.setOrgTel(cooperationUserInfoBean.getTelNum());
            cooperationBean.setContacts(cooperationUserInfoBean.getContacts());

            if (StringUtils.isNotEmpty(cooperationObjId)) {
                final CooperationUserInfoBean cooperationUserInfoBean1 = getCooperationUserInfoBean(cooperationObjId);
                cooperationBean.setCooperationObjId(cooperationUserInfoBean1.getUserId());
                cooperationBean.setCooperationObjType(cooperationUserInfoBean1.getUserType());
                cooperationBean.setCooperationObjInfo(cooperationUserInfoBean1.getInfo());

                cooperationBean.setCooperationObjName(StringUtils.isEmpty(cooperationUserInfoBean1.getUserName()) ? "姓名" : cooperationUserInfoBean1.getUserName());
                cooperationBean.setCooperationObjNickName(StringUtils.isEmpty(cooperationUserInfoBean1.getNickName()) ? "昵称" : cooperationUserInfoBean1.getNickName());
                cooperationBean.setCooperationObjHeadImg(StringUtils.isEmpty(cooperationUserInfoBean1.getHeadImg()) ? urlPrefix + displayImg : cooperationUserInfoBean1.getHeadImg());
                List<String> strings = new ArrayList<>();
                if (StringUtils.isNotEmpty(cooperationUserInfoBean1.getLabels())) {
                    final List<String> stringList = JSON.parseArray(cooperationUserInfoBean1.getLabels(), String.class);
                    strings = stringList.stream().limit(2).collect(Collectors.toList());
                }
                cooperationBean.setLabels(StringUtils.isEmpty(cooperationUserInfoBean1.getLabels()) ? Collections.emptyList() : strings);
            }
        }
        return cooperationBean;
    }


    @Override
    public CooperationUserInfoBean getCooperationUserInfoBean(String userId) {

        final CooperationUserInfoBean cooperationUserInfoBean = new CooperationUserInfoBean();

        final LeadsUser leadsUser = leadsUserMapper.selectById(userId);
        final LeadsUserInfo leadsUserInfo = leadsUserInfoMapper.selectById(leadsUser.getUserInfoId());
        cooperationUserInfoBean.setUserId(userId);
        cooperationUserInfoBean.setUserInfoId(leadsUser.getUserInfoId());
        cooperationUserInfoBean.setUserName(leadsUserInfo.getRealName());
        cooperationUserInfoBean.setContacts(leadsUserInfo.getContacts());
        cooperationUserInfoBean.setTelNum(leadsUser.getTelNum());
        cooperationUserInfoBean.setUserType(leadsUser.getUserType());
        cooperationUserInfoBean.setInfo(leadsUserInfo.getPersonalInfo());
        cooperationUserInfoBean.setHeadImg(leadsUserInfo.getHeadImgUrl());
        cooperationUserInfoBean.setNickName(leadsUserInfo.getNickName());
        cooperationUserInfoBean.setLabels(leadsUserInfo.getLabels());
        return cooperationUserInfoBean;
    }

    @Override
    public List<Dict> getCooType() {
        Map<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("code_type", "cooperationType");
        columnMap.put("code_flay", DataStatus.Enable.getValue());
        final List<Dict> dicts = dictMapper.selectByMap(columnMap);
        return dicts;
    }
}
