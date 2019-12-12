package com.xiaoyi.bis.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.blog.domain.Event;

public interface CaseService extends IService<Event> {

    PageInfo<Event> getById(String userId, int pageNum, int pageSize);

    int updateEvtId(String evtId);

    void taskCase();

    Event showById(String userId);
}
