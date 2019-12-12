package com.xiaoyi.bis.common.enums;

/**
 * 用户会话
 * 
 * @author 秋山
 */
public enum OnlineStatus
{
    /** 用户状态 */
    on_line("在线"), off_line("离线");

    private final String info;

    private OnlineStatus(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }
}
