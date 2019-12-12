package com.xiaoyi.bis.common.dict.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.common.dict.dao.DictCodeMapper;
import com.xiaoyi.bis.common.dict.domain.Dict;
import com.xiaoyi.bis.common.dict.service.DictService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description：字典
 * @Author：kk
 * @Date：2019/9/4 09:28
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictCodeMapper, Dict> implements DictService {

    @Override
    public String getCodeNo(String codeId) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("code_id", codeId);
        columnMap.put("code_flay", "1");
        final List<Dict> dicts = baseMapper.selectByMap(columnMap);
        if (CollectionUtils.isNotEmpty(dicts)) {
            return dicts.get(0).getCodeNo();
        }
        return null;
    }

    @Override
    public Dict getDictCode(String codeType, String codeNo) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("code_type", codeType);
        columnMap.put("code_no", codeNo);
        columnMap.put("code_flay", "1");
        final List<Dict> dicts = baseMapper.selectByMap(columnMap);
        if (CollectionUtils.isNotEmpty(dicts)) {
            return dicts.get(0);
        }
        return null;
    }

    @Override
    public String getDictNo(String codeType, String codeName){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("code_type", codeType);
        columnMap.put("code_name", codeName);
        columnMap.put("code_flay", "1");
        final List<Dict> dicts = baseMapper.selectByMap(columnMap);
        if (CollectionUtils.isNotEmpty(dicts)) {
            return dicts.get(0).getCodeNo();
        }
        return null;
    }
}