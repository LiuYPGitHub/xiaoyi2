package com.xiaoyi.bis.xiaoyi.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author CJ
 * @date 2019/10/15
 */
@ToString
@Getter
@Setter
public class XiaoYiOrderBean implements Serializable {

    private static final long serialVersionUID = 1172179318625848988L;

    // 全平台 本月直播
    private BigDecimal thisMonthMoney_live;
    // 全平台 上月直播
    private BigDecimal lastMonthMoney_live;
    // 全平台 本月录播
    private BigDecimal thisMonthMoney_recording;
    // 全平台 上月录播
    private BigDecimal lastMonthMoney_recording;

    // 机构 本月直播
    private BigDecimal thisAllMonthRecording_live;
    // 机构 上月直播
    private BigDecimal lastAllMonthRecording_live;
    // 机构 本月录播
    private BigDecimal thisAllMonthRecording_recording;
    // 机构 上月录播
    private BigDecimal lastAllMonthRecording_recording;

    // 添翼 本月直播
    private BigDecimal thisAllTianYiMonthRecording_live;
    // 添翼 上月直播
    private BigDecimal lastAllTianYiMonthRecording_live;
    // 添翼 本月录播
    private BigDecimal thisAllTianYiMonthRecording_recording;
    // 添翼 上月录播
    private BigDecimal lastAllTianYiMonthRecording_recording;


}
