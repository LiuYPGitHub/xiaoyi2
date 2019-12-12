package com.xiaoyi.bis.xiaoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.xiaoyi.dao.OrgsMapper;
import com.xiaoyi.bis.xiaoyi.domain.Orgs;
import com.xiaoyi.bis.xiaoyi.service.OrgsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author CJ
 * @date 2019/12/4
 */
@Transactional
@Service
public class OrgsServiceImpl extends ServiceImpl<OrgsMapper, Orgs> implements OrgsService {

    @Autowired
    private OrgsMapper orgsMapper;

    @Override
    public Orgs getOrgsByOrgId(String orgId) {
        QueryWrapper<Orgs> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("org_id",orgId);
        List<Orgs> orgs = orgsMapper.selectList(queryWrapper);
        return orgs.size()>0?orgs.get(0):null;
    }

}
