package com.xiaoyi.bis.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.common.utils.StringUtils;
import com.xiaoyi.bis.user.dao.AreaMapper;
import com.xiaoyi.bis.user.domain.Area;
import com.xiaoyi.bis.user.service.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description：选择城市
 * @Author：kk
 * @Date：2019/9/5 13:56
 */
@Slf4j
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Override
    public String getAreaName(String areaPCode, String areaCode) {
        Map<String, Object> columnMap = new HashMap<>(16);

        if (StringUtils.isNotEmpty(areaPCode)) {
            columnMap.put("area_parent_code", areaPCode);
        }
        if (StringUtils.isNotEmpty(areaCode)) {
            columnMap.put("area_code", areaPCode);
        }
        columnMap.put("area_flag", "1");
        final List<Area> areas = baseMapper.selectByMap(columnMap);
        if (CollectionUtils.isNotEmpty(areas)) {
            return areas.get(0).getAreaName();
        }
        return null;
    }

    @Override
    public Area getAreaCode(String code) {
        Map<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("area_code", code);
        columnMap.put("area_flag", "1");
        final List<Area> areas = baseMapper.selectByMap(columnMap);
        if (CollectionUtils.isNotEmpty(areas)) {
            return areas.get(0);
        }
        return null;
    }
}

