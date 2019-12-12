package com.xiaoyi.bis.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.xiaoyi.bis.blog.domain.Event;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface EventMapper extends BaseMapper<Event> {

    List<Event> getEventById(String userId, String evtTitle);

    List<Event> selectdraftById(String userId, String evtTitle);

    /**
     * 删除动态
     */
    int updateEvent(String evtId);

    /**
     * 草稿箱活动发布
     */
    int releaseEvent(Event event);

    // 回显
    Event finddEchoById(String evtId);

    Event finddEchoBy(String evtId);

    // add
    void insertEvent(Event event);

    // deit
    void updateEcho(Event event);
}
