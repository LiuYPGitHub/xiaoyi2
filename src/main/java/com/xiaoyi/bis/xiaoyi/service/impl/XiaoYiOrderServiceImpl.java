package com.xiaoyi.bis.xiaoyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.xiaoyi.bean.OrderWhereBean;
import com.xiaoyi.bis.xiaoyi.bean.XiaoYiOrderBean;
import com.xiaoyi.bis.xiaoyi.dao.LiveOrderMapper;
import com.xiaoyi.bis.xiaoyi.domain.XiaoYiOrder;
import com.xiaoyi.bis.xiaoyi.service.XiaoYiOrderService;
import com.xiaoyi.bis.xiaoyi.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author CJ
 * @date 2019/10/15
 */
@Transactional
@Service
public class XiaoYiOrderServiceImpl extends ServiceImpl<LiveOrderMapper, XiaoYiOrder> implements XiaoYiOrderService {

    @Autowired
    private LiveOrderMapper liveOrderMapper;

    @Override
    public XiaoYiOrderBean getOrder(String orgCode) {//String uId
        XiaoYiOrderBean xiaoYiOrderBean =new XiaoYiOrderBean();
//        OrderWhereBean thisMonth = new OrderWhereBean();
        // 全平台
//        thisMonth.setMonth("this");
        xiaoYiOrderBean.setThisMonthMoney_live(liveOrderMapper.liveBroadcastAllMonthThis(orgCode));
//        thisMonth.setMonth("last");
        xiaoYiOrderBean.setLastMonthMoney_live(liveOrderMapper.liveBroadcastAllMonthLast(orgCode));
//        thisMonth.setMonth("this");
        xiaoYiOrderBean.setThisMonthMoney_recording(liveOrderMapper.fullMonthRecordingThis(orgCode));
//        thisMonth.setMonth("last");
        xiaoYiOrderBean.setLastMonthMoney_recording(liveOrderMapper.fullMonthRecordingLast(orgCode));
        // 机构自营
//        thisMonth.setMonth("this");
        xiaoYiOrderBean.setThisAllMonthRecording_live(liveOrderMapper.jiThisAllMonthRecordingThis(orgCode));
//        thisMonth.setMonth("last");
        xiaoYiOrderBean.setLastAllMonthRecording_live(liveOrderMapper.jiThisAllMonthRecordingLast(orgCode));
//        thisMonth.setMonth("this");
        xiaoYiOrderBean.setThisAllMonthRecording_recording(liveOrderMapper.jiLateAllMonthRecordingThis(orgCode));
//        thisMonth.setMonth("last");
        xiaoYiOrderBean.setLastAllMonthRecording_recording(liveOrderMapper.jiLateAllMonthRecordingLast(orgCode));
        // 添翼申学
//        thisMonth.setMonth("this");
        xiaoYiOrderBean.setThisAllTianYiMonthRecording_live(liveOrderMapper.tianThisAllMonthRecordingThis(orgCode));
//        thisMonth.setMonth("last");
        xiaoYiOrderBean.setLastAllTianYiMonthRecording_live(liveOrderMapper.tianThisAllMonthRecordingLast(orgCode));
//        thisMonth.setMonth("this");
        xiaoYiOrderBean.setThisAllTianYiMonthRecording_recording(liveOrderMapper.tianLateAllMonthRecordingThis(orgCode));
//        thisMonth.setMonth("last");
        xiaoYiOrderBean.setLastAllTianYiMonthRecording_recording(liveOrderMapper.tianLateAllMonthRecordingLast(orgCode));
        return xiaoYiOrderBean;
    }

    @Override
    public String queryOrderByUid(String uId) {
        return liveOrderMapper.selectByUid(uId);
    }

}
