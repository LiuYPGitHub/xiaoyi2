package com.xiaoyi.bis.common.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.common.dict.domain.Dict;

public interface DictService extends IService<Dict> {

    /**
     * 获取字典
     *
     * @param codeId
     * @return
     */
    String getCodeNo(String codeId);

    /**
     * 获取codeName
     *
     * @param codeType
     * @param codeNo
     * @return
     */
    Dict getDictCode(String codeType, String codeNo);

    /**
     * 获取codeNo
     * @return
     */
    String getDictNo(String codeType, String codeName);
}
