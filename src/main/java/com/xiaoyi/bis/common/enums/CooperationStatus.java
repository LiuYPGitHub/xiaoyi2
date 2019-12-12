package com.xiaoyi.bis.common.enums;

/**
 * 合作类型
 *
 * @author kk
 */
public enum CooperationStatus {
    Cooperation("1"),
    ToCooperation("2");

    private String value;

    private CooperationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}