package com.xiaoyi.bis.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.user.domain.Area;

public interface AreaService extends IService<Area> {

    /**
     * 获取城市
     *
     * @param areaCode
     * @param areaPCode
     * @return
     */
    String getAreaName(String areaPCode, String areaCode);

    /**
     * 获取地区Code
     *
     * @param code
     * @return
     */
    Area getAreaCode(String code);
}
