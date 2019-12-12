package com.xiaoyi.bis.common.enums;

/**
 * 关注
 *
 * @author kk
 */
public enum FollowStatus {
    Follow("1"),
    unFllow("2");

    private String value;

    private FollowStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}