package com.xiaoyi.bis.xiaoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.xiaoyi.domain.PolyRecordCallback;

public interface PolyRecordCallbackService extends IService<PolyRecordCallback> {

    /**
     * 保存智能录播录制视频信息
     * (新增ty_poly_record_callback回调表记录)
     * @param polyRecordCallback
     * @return
     */
    int savePolyRecordCallback(PolyRecordCallback polyRecordCallback);

}
