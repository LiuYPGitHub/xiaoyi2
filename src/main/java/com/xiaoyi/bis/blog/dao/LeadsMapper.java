package com.xiaoyi.bis.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyi.bis.blog.domain.Event;
import com.xiaoyi.bis.blog.domain.Leads;
import com.xiaoyi.bis.blog.domain.LeadsLeIntenType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LeadsMapper extends BaseMapper<Leads> {

    // list
    List<Leads> getRealName(String realName, String leParName, String leParPhone, String leChiName, String leSchool);

    // 删除动态
    int updateLeads(String leId);

    // add
    void insertLeads(Leads le);

    // 回显
    Leads finddEchoById(String leId);

    // 編輯
    void updateleads(Leads leads);

    // 导入
    void addLeadsExcel(Leads leads);

    Leads view(String leId);

    Leads selectCode(String City);

    void timingGradeAge(int leChiAge,int grade,String leId);

    List<Leads> timingQuote();

    List<Leads> timingAge(int leChiAge,String leId);
}
