package com.xiaoyi.bis.xiaoyi.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyi.bis.xiaoyi.bean.OrderWhereBean;
import com.xiaoyi.bis.xiaoyi.domain.XiaoYiOrder;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author CJ
 * @date 2019/10/15
 */
public interface LiveOrderMapper extends BaseMapper<XiaoYiOrder> {

    BigDecimal liveBroadcastAllMonthThis(@Param(value = "orgCode")String orgCode);

    BigDecimal liveBroadcastAllMonthLast(@Param(value = "orgCode")String orgCode);

    BigDecimal fullMonthRecordingThis(@Param(value = "orgCode")String orgCode);

    BigDecimal fullMonthRecordingLast(@Param(value = "orgCode")String orgCode);

    BigDecimal jiThisAllMonthRecordingThis(@Param(value = "orgCode")String orgCode);

    BigDecimal jiThisAllMonthRecordingLast(@Param(value = "orgCode")String orgCode);

    BigDecimal jiLateAllMonthRecordingThis(@Param(value = "orgCode")String orgCode);

    BigDecimal jiLateAllMonthRecordingLast(@Param(value = "orgCode")String orgCode);

    BigDecimal tianThisAllMonthRecordingThis(@Param(value = "orgCode")String orgCode);

    BigDecimal tianThisAllMonthRecordingLast(@Param(value = "orgCode")String orgCode);

    BigDecimal tianLateAllMonthRecordingThis(@Param(value = "orgCode")String orgCode);

    BigDecimal tianLateAllMonthRecordingLast(@Param(value = "orgCode")String orgCode);

    /**
     * 根据机构名称获取订单信息
     * @param whereBean
     * @return
     */
    Integer selectOrderByName(OrderWhereBean whereBean);

    /**
     * 根据用户编号获取订单信息
     * @param uId
     * @return
     */
    String selectByUid(@Param(value = "uId") String uId);

    /**
     * 根据用户编号获取创建用户
     * @param uId
     * @return
     */
    String selectMobileByUid(@Param(value = "uId") String uId);

}
