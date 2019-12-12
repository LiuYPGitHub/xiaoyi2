package com.xiaoyi.bis.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.user.bean.SysUserInfoBean;
import com.xiaoyi.bis.user.bean.SysUserInfoListBean;
import com.xiaoyi.bis.user.controller.common.ResPageInfo;
import com.xiaoyi.bis.user.domain.LeadsUserInfo;

public interface IndexService extends IService<LeadsUserInfo> {

    /**
     * 根据类型筛选 KOL，名师
     *
     * @param type
     * @param
     * @return
     */
    ResPageInfo<SysUserInfoListBean> getIndexList(String type, int pageNumb, int pageSize);

    /**
     * 保存,修改首页动态 唯一
     *
     * @param userId
     * @param dynaId
     * @param dynaContent
     */
    void saveOrUpdateDynamicIndex(String userId, String dynaId, String dynaContent, String dynaStatus, String dynaType);

    /**
     * 删除动态
     *
     * @param dynaId
     */
    void updateDynamicIndex(String dynaId);

    /**
     * 草稿箱中发布
     *
     * @param dynaId
     */
    void releaseDynamic(String dynaId);

    /**
     * 获取 KOL，名师详情
     *
     * @param userId
     * @return
     */
    SysUserInfoBean getSysUserInfo(String userId, String otherId);
}
