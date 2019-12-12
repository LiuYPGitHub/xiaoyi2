package com.xiaoyi.bis.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.blog.dao.LeadsMapper;
import com.xiaoyi.bis.blog.domain.Leads;
import com.xiaoyi.bis.blog.service.LeadsService;
import com.xiaoyi.bis.common.dict.service.DictService;
import com.xiaoyi.bis.common.utils.SnowflakeIdGenerator;
import com.xiaoyi.bis.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LeadsServiceImpl extends ServiceImpl<LeadsMapper, Leads> implements LeadsService {

    @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    LeadsMapper leadsMapper;
    @Autowired
    DictService dictService;

    @Override
    public PageInfo<Leads> getRealName(String realName, String leParName, String leParPhone, String leChiName, String leSchool, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Leads> list = baseMapper.getRealName(realName, leParName, leParPhone, leChiName, leSchool);
        PageInfo<Leads> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

    /**
     * 删除LEADS库
     */
    @Override
    @Transactional
    public int updateLeads(String leId) {
        return baseMapper.updateLeads(leId);
    }

    /**
     * LEADS库-添加新LEADS
     *
     * @param le
     */
    @Override
    @Transactional
    public void addLeads(Leads le) {
        le.setLeId(snowflakeIdGenerator.nextId() + "");
        le.setIsDelete("1");
        le.setCreateTime(new Date());
        baseMapper.insertLeads(le);
    }

    /**
     * LEADS库-编辑-回显
     *
     * @param leId
     * @return
     */
    @Override
    @Transactional
    public Leads finddEchoById(String leId) {
        Leads leads = baseMapper.finddEchoById(leId);
        if (StringUtils.isNotEmpty(leads.getCity())) {
            Leads code = leadsMapper.selectCode(leads.getCity());
            String qu = code.getAreaName();  // 区
//       log.info("///////////" + qu);
            Leads shir = leadsMapper.selectCode(code.getAreaParentCode());
            String zhixiashi = shir.getAreaName();
//        log.info("///////////" + zhixiashi);
            Leads shi1 = leadsMapper.selectCode(shir.getAreaParentCode());
            String shi = shi1.getAreaName(); // 市
//        log.info("///////////" + shi);
            Leads sheng1 = leadsMapper.selectCode(shi1.getAreaParentCode());
            String sheng = sheng1.getAreaName(); // 省
//        log.info("///////////" + sheng);
            if (shi.equals("重庆市")) {
                leads.setSheng(shi);
                leads.setShi(zhixiashi);
                leads.setQu(qu);
                return leads;
            } else if (zhixiashi.equals("市辖区")) {
                leads.setSheng(sheng);
                leads.setShi(shi);
                leads.setQu(qu);
                return leads;
            } else {
                leads.setSheng(shi);
                leads.setShi(zhixiashi);
                leads.setQu(qu);
                return leads;
            }
        }
        return leads;
    }

    /**
     * 修改
     *
     * @param leads
     */
    @Override
    @Transactional
    public void updateleads(Leads leads) {
        leads.setIsDelete("1");
        leads.setUpdateTime(new Date());
        this.baseMapper.updateleads(leads);
    }

    /**
     *ping excel
     */
    public void addLeadsExcel(Leads leads, String createBy) {
        List listType = new ArrayList<>();
        listType.add(leads.getLeIntenType().split(StringPool.DASH));
        leads.setLeIntenType(JSON.toJSONString(listType));
        leads.setLeIntenType(leads.getLeIntenType().substring(1, leads.getLeIntenType().length() - 1));
        leads.setLeId(snowflakeIdGenerator.nextId() + StringPool.EMPTY);
        leads.setCreateBy(createBy);
        leads.setCreateTime(new Date());
        leads.setIsDelete(StringPool.ONE);
        leads.setRealName(createBy);
//        leads.setLeChiGen(dictService.getDictNo("sexPing", leads.getLeChiGen()));
//        leads.setGrade(dictService.getDictNo("grade", leads.getGrade()));
        this.baseMapper.addLeadsExcel(leads);
    }

    // 查看
    public Leads view(String leId) {
        Leads leads = baseMapper.view(leId);
        if (leads.getGrade()!= null && leads.getGrade() != "") {
            leads.setGrade(dictService.getDictCode("grade", leads.getGrade()).getCodeName());
        }
        if (StringUtils.isNotEmpty(leads.getCity())) {
            Leads code = leadsMapper.selectCode(leads.getCity());
            String qu = code.getAreaName();  // 区
//       log.info("///////////" + qu);
            Leads shir = leadsMapper.selectCode(code.getAreaParentCode());
            String zhixiashi = shir.getAreaName();
//        log.info("///////////" + zhixiashi);
            Leads shi1 = leadsMapper.selectCode(shir.getAreaParentCode());
            String shi = shi1.getAreaName(); // 市
//        log.info("///////////" + shi);
            Leads sheng1 = leadsMapper.selectCode(shi1.getAreaParentCode());
            String sheng = sheng1.getAreaName(); // 省
//        log.info("///////////" + sheng);
            if (shi.equals("重庆市")) {
                leads.setSheng(shi);
                leads.setShi(zhixiashi);
                leads.setQu(qu);
                return leads;
            } else if (zhixiashi.equals("市辖区")) {
                leads.setSheng(sheng);
                leads.setShi(shi);
                leads.setQu(qu);
                return leads;
            } else {
                leads.setSheng(shi);
                leads.setShi(zhixiashi);
                leads.setQu(qu);
                return leads;
            }
        }
        return leads;
    }

    @Override
    public void timingGradeAge() {
        List<Leads> timing = leadsMapper.timingQuote();
        timing.stream().forEach(e -> {
            if(Integer.parseInt(e.getGrade()) < 6){
                this.leadsMapper.timingGradeAge(Integer.parseInt(e.getLeChiAge()), Integer.parseInt(e.getGrade()),e.getLeId());
            }else{
                this.leadsMapper.timingAge(Integer.parseInt(e.getLeChiAge()),e.getLeId());
            }
        });
    }
}

//{
//        "city": "城市code",
//        "createBy": "创建者",
//        "leChiAge": "20",
//        "leChiGen": "0",
//        "leChiName": "张三",
//        "leIntenType": "语文",
//        "leParName": "家长姓名",
//        "leParPhone": "12565986966",
//        "leSchool": "剑桥大学",
//        "realName": "昂立教育",
//        "grade": "八年"
//}