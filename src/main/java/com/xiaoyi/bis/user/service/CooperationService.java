package com.xiaoyi.bis.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.common.dict.domain.Dict;
import com.xiaoyi.bis.user.domain.Cooperation;
import com.xiaoyi.bis.user.bean.CooperationBean;
import com.xiaoyi.bis.user.bean.CooperationUserInfoBean;

import java.util.List;

public interface CooperationService extends IService<Cooperation> {

    /**
     * 保存合作信息
     *
     * @param cooperationBean
     */
    void saveCooperation(CooperationBean cooperationBean);

    /**
     * 合作数据回显
     *
     * @param userId
     * @param cooperationObjId
     * @return
     */
    CooperationBean getCooperationInfo(String userId, String cooperationObjId);

    /**
     * userInfo
     *
     * @param userId
     * @return
     */
    CooperationUserInfoBean getCooperationUserInfoBean(String userId);

    /**
     * 关注类型
     *
     * @return
     */
    List<Dict> getCooType();
}
