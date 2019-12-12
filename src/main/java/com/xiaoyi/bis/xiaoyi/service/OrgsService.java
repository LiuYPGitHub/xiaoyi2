package com.xiaoyi.bis.xiaoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.xiaoyi.domain.Orgs;

public interface OrgsService extends IService<Orgs> {

    Orgs getOrgsByOrgId(String orgId);

}
