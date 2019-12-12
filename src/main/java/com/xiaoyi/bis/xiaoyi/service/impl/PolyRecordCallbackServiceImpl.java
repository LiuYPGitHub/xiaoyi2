package com.xiaoyi.bis.xiaoyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.xiaoyi.dao.PolyRecordCallbackMapper;
import com.xiaoyi.bis.xiaoyi.domain.PolyRecordCallback;
import com.xiaoyi.bis.xiaoyi.service.PolyRecordCallbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author CJ
 * @date 2019/11/6
 */
@Transactional
@Service
@Slf4j
public class PolyRecordCallbackServiceImpl extends ServiceImpl<PolyRecordCallbackMapper, PolyRecordCallback> implements PolyRecordCallbackService {

    @Autowired
    private PolyRecordCallbackMapper polyRecordCallbackMapper;

    @Override
    public int savePolyRecordCallback(PolyRecordCallback polyRecordCallback) {
        return polyRecordCallbackMapper.insert(polyRecordCallback);
    }

}
