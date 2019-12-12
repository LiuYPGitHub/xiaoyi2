package com.xiaoyi.bis.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.blog.dao.CaseMapper;
import com.xiaoyi.bis.blog.domain.Event;
import com.xiaoyi.bis.blog.service.CaseService;
import com.xiaoyi.bis.common.dict.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Event> implements CaseService {

    @Autowired
    DictService dictService;

    @Override
    public PageInfo<Event> getById(String userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Event> list = baseMapper.selectById(userId);
        List<Event> list1= list.stream().map(e->{
            //Event event = mapper.map(e,Event.class);
            e.setEvtType(dictService.getDictCode("typeOfActivity",e.getEvtType()).getCodeName());
//            e.setEvtWay(dictService.getDictCode("typeOfActivity",e.getEvtWay()).getCodeName());
            return e;
        }).collect(Collectors.toList());
        PageInfo<Event> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

    @Override
    public int updateEvtId(String evtId) {
        return this.baseMapper.updateEvtId(evtId);
    }

    @Override
    public void taskCase() {
        this.baseMapper.updateTaskCase();
    }

    public Event showById(String evtId) {
        Event ev = baseMapper.showById(evtId);
        if(ev.getEvtType().equals("0")){
            ev.setEvtType("体验课");
        }
        if(ev.getEvtType().equals("1")){
            ev.setEvtType("讲座");
        }
        if(ev.getEvtType().equals("2")){
            ev.setEvtType("参访");
        }
        if(ev.getEvtType().equals("3")){
            ev.setEvtType("游园");
        }
        if(ev.getEvtType().equals("4")){
            ev.setEvtType("研学");
        }
        if(ev.getEvtType().equals("5")){
            ev.setEvtType("其他");
        }
        return ev;
    }
}