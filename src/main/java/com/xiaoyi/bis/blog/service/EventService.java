package com.xiaoyi.bis.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.blog.domain.Event;

import java.util.Date;

public interface EventService extends IService<Event> {

    /**
     * 通过用id查找活动
     *
     * @return
     */
    PageInfo<Event> getById(String userId, String evtTitle, int pageNum, int pageSize);

    /**
     * 通过用id查找用户 草稿箱
     *
     * @return
     */
    PageInfo<Event> finddraftById(String userId, String evtTitle, int pageNum, int pageSize);

    /**
     * 删除动态
     *
     * @param evtId
     */
    void updateEvent(String evtId);

    /**
     * 删除草稿活动
     */
    void releaseEvent(Event event);

    /**
     * 回显
     *
     * @param userId
     * @return
     */
    Event finddEchoById(String userId);

    Event finddEchoBy(String userId);

    /**
     * 添加
     *
     * @param event
     */
    void addEvent(Event event);

    /**
     * 修改
     *
     * @param event
     */
    void updateEcho(Event event);
}
