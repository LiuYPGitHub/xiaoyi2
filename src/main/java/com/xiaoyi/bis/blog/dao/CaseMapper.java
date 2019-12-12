package com.xiaoyi.bis.blog.dao;

import java.sql.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyi.bis.blog.domain.Event;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CaseMapper extends BaseMapper<Event> {

    List<Event> selectById(String userId);

    int updateEvtId(String evtId);

    void updateTaskCase();

    Event showById(String evtId);
}