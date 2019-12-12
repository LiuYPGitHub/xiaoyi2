package com.xiaoyi.bis.common.enums;

/**
 * 数据类型
 *
 * @author kk
 */
public enum DataStatus {
    Enable("1"),
    Delete("2");

    private String value;

    private DataStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}