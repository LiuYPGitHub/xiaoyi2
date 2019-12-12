package com.xiaoyi.bis.xiaoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.bis.xiaoyi.bean.XiaoYiOrderBean;
import com.xiaoyi.bis.xiaoyi.domain.XiaoYiOrder;

/**
 * @author CJ
 * @date 2019/10/15
 */
public interface XiaoYiOrderService extends IService<XiaoYiOrder> {

    /**
     * 获取订单信息
     * @return
     */
    XiaoYiOrderBean getOrder(String orgCode);

    /**
     * 获取用户关联的订单信息
     * @param uId
     * @return
     */
    String queryOrderByUid(String uId);

}
