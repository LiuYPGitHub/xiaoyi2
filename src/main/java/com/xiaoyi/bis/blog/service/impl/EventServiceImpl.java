package com.xiaoyi.bis.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.blog.dao.EventMapper;
import com.xiaoyi.bis.blog.domain.Event;
import com.xiaoyi.bis.blog.service.EventService;
import com.xiaoyi.bis.common.dict.service.DictService;
import com.xiaoyi.bis.common.utils.SnowflakeIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {

    @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    DictService dictService;
    private @Autowired
    Mapper mapper;

    @Override
    public PageInfo<Event> getById(String userId, String evtTitle, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Event> list = baseMapper.getEventById(userId, evtTitle);
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
    public PageInfo<Event> finddraftById(String userId, String evtTitle, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Event> list = baseMapper.selectdraftById(userId, evtTitle);
        List<Event> list1= list.stream().map(e->{
            //Event event = mapper.map(e,Event.class);
            e.setEvtType(dictService.getDictCode("typeOfActivity",e.getEvtType()).getCodeName());
//            e.setEvtWay(dictService.getDictCode("typeOfActivity",e.getEvtWay()).getCodeName());
            return e;
        }).collect(Collectors.toList());
        PageInfo<Event> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

    /**
     * 删除动态
     */
    @Override
    @Transactional
    public void updateEvent(String evtId) {
        this.baseMapper.updateEvent(evtId);
    }

    /**
     * 草稿箱活动发布
     *
     * @param event
     */
    @Override
    @Transactional
    public void releaseEvent(Event event) {
//        event.setEvtRelTime(DateUtils.stringToDate(String.valueOf(new Date()), DateFormat.MEDIUM));
        this.baseMapper.releaseEvent(event);
    }

    /**
     * 活动草稿箱-编辑-回显
     *
     * @param evtId
     * @return
     */
    @Override
    public Event finddEchoById(String evtId) {
        Event ev = baseMapper.finddEchoById(evtId);
//        ev.setEvtWay(StringUtils.equals(ev.getEvtWay(),"0") ? "线下":"线上");
        return ev;
    }

    /**
     * 活动草稿箱-编辑-回显
     *
     * @param evtId
     * @return
     */
    @Override
    public Event finddEchoBy(String evtId) {
        Event ev = baseMapper.finddEchoBy(evtId);
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

    /**
     * KOL活动-添加新活动
     *
     * @param event
     */
    @Override
    @Transactional
    public void addEvent(Event event) {
        event.setEvtId(snowflakeIdGenerator.nextId() + "");
        event.setCreateTime(new Date());
        event.setIsDelete("1");
        event.setEvtCheckStatus("1");
        if (event.getEvtWay().equals("1")) {
            event.setEvtCoDdress(event.getEvtAddress());
            event.setEvtAddress(null);
        }
        if (event.getEvtCoDdress() != null && event.getEvtCoDdress().substring(0, 4).equals("http")) {
        } else if (event.getEvtCoDdress() != null) {
            event.setEvtCoDdress("http://" + event.getEvtCoDdress());
        }
        baseMapper.insertEvent(event);
    }

    /**
     * 修改
     *
     * @param event
     */
    @Override
    @Transactional
    public void updateEcho(Event event) {
        event.setUpdateTime(new Date());
        this.baseMapper.updateEcho(event);
    }
}
/* 添加字段格式
{
    {
  "createBy": "张漂亮",
  "evtAddress": "https://fanyi.sogou.com/?fr=websearch_submit&pid=#auto/zh-CHS/",
  "evtContent": "活动内容",
  "evtEndTime": "2019-10-15 03:47:51",
  "evtImgUrl": "兔兔",
  "evtStartTime": "2019-10-15 03:47:51",
  "evtStatus": "0",
  "evtTitle": "活动标题",
  "evtType": "0",
  "evtWay": "0",
  "isDelete": "string",
  "userId": "10"
}
}

修改
{
         "evtTitle": "活动标题11",
         "evtImgUrl": "qwertyusxcvbnm",
         "evtType": "活动类型11",
         "evtWay": 0,
         "evtStartTime": "2019-08-30 14:11:04",
         "evtEndTime": "2019-08-30 14:11:04",
         "evtContent": "活动111111111",
         "evtStatus": 0,
         "updateBy": "小绵羊1",
	     "evtId": "289276924860174336"
}
-----evtStatus 两个状态------         "isDelete": 1,	     "updateTime": "2019-08-30 14:11:04",
{
  "evtId": "289276924860174336"
  "evtAddress": "活动地址12313123",
  "evtCheckStatus": "0",
  "evtCoDdress": "活动链接地址231",
  "evtContent": "活动内容3232",
  "evtEndTime": "2019-09-05 01:56:41",
  "evtId": 0,
  "evtImgUrl": "string",
  "evtStartTime": "2019-09-05 01:56:47",
  "evtStatus": "1",
  "evtTitle": "活动标题",
  "evtType": "活动类型",
  "evtWay": "0",
  "isDelete": "1",
  "updateBy": "更新着",
  "updateTime": "2019-09-05 01:56:47"
}

*/
