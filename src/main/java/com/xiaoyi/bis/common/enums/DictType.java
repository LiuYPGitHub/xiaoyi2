package com.xiaoyi.bis.common.enums;

/**
 * 操作人类别
 *
 * @author 秋山
 */
public enum DictType {
    /**
     * 其它
     */
    USER("user"),

    COOPERATION("cooperationType");

    private final String info;

    private DictType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
