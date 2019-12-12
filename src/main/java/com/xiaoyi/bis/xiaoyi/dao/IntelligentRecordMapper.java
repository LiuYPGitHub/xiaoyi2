package com.xiaoyi.bis.xiaoyi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyi.bis.xiaoyi.domain.IntelligentRecord;
import org.apache.ibatis.annotations.Param;

public interface IntelligentRecordMapper extends BaseMapper<IntelligentRecord> {

    /**
     * 根据用户编号获取用户关联的频道编号
     * @param userId
     * @return
     */
    String selectChannelIdByUserId(@Param(value = "userId")String userId);

    /**
     * 根据用户编号获取创建课程的手机号码
     * @param userId
     * @return
     */
    String selectCreateMobileByUserId(@Param(value = "userId")String userId);

}
