package com.xiaoyi.bis.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.blog.controller.bean.DynamicBean;
import com.xiaoyi.bis.blog.controller.bean.DynamicBeanUrl;
import com.xiaoyi.bis.blog.dao.DynamicImgMapper;
import com.xiaoyi.bis.blog.dao.DynamicMapper;
import com.xiaoyi.bis.blog.domain.Dynamic;
import com.xiaoyi.bis.blog.domain.DynamicImg;
import com.xiaoyi.bis.blog.service.DynamicService;
import com.xiaoyi.bis.common.utils.SnowflakeIdGenerator;
import com.xiaoyi.bis.common.utils.StringUtils;
import com.xiaoyi.bis.user.controller.common.ResPageInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 动态表 服务层实现
 */
@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, Dynamic> implements DynamicService {

    private @Autowired
    DynamicImgMapper dynamicImgMapper;
    private @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;
    private @Autowired
    Mapper mapper;
    private @Value("${file.urlPrefix}")
    String urlPrefix;
    private @Value("${file.imgPath}")
    String imgs;

    @Override
    public PageInfo<Dynamic> getById(String userId, String dynaContent, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Dynamic> list = baseMapper.selectById(userId, dynaContent);
        PageInfo<Dynamic> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

    /**
     * KOL详情-最新动态页
     *
     * @param userId
     * @return
     */
    @Override
    public ResPageInfo<DynamicBeanUrl> LatestNews(String userId, int pageNum, int pageSize) {
        Page<Dynamic> page = new Page<>(pageNum, pageSize);
        IPage<Dynamic> dynamics = this.baseMapper.selectPage(page, new LambdaQueryWrapper<Dynamic>().eq(Dynamic::getUserId, userId).eq(Dynamic::getDynaStatus, 0).eq(Dynamic::getIsDelete, 1).orderByDesc(Dynamic::getCreateTime));
        List<DynamicBeanUrl> dynami = dynamics.getRecords().stream().map(e -> {
            DynamicBeanUrl dynamicBean = mapper.map(dynamics, DynamicBeanUrl.class);
            dynamicBean.setDynaId(e.getDynaId());
            dynamicBean.setUserId(e.getUserId());
            dynamicBean.setDynaContent(e.getDynaContent());
            dynamicBean.setCreateTime(e.getCreateTime());
            dynamicBean.setDynaType(0);
            Map<String, Object> columnMap = new HashMap<>();
            columnMap.put("dyna_id", e.getDynaId());
            columnMap.put("is_delete", 1);
            final List<DynamicImg> dynamicImgs = dynamicImgMapper.selectByMap(columnMap);
            List<String> s = dynamicImgs.stream().map(DynamicImg::getImgUrl).collect(Collectors.toList());
            dynamicBean.setDynaImgs(s);
//            final List<DynamicImg> dynamicImgs = dynamicImgMapper.selectByMap(columnMap);
//            List<String> s= dynamicImgs.stream().map(ev->{
//                ev.setImgUrl(urlPrefix + imgs + ev.getImgUrl());
//                return ev;
//            }).map(DynamicImg::getImgUrl).collect(Collectors.toList());
//            dynamicBean.setDynaImgs(s);
            return dynamicBean;
        }).collect(Collectors.toList());

        final ResPageInfo<DynamicBeanUrl> dyUrl = new ResPageInfo<>();
        dyUrl.setContent(dynami);
        dyUrl.setTotal(dynamics.getTotal());
        dyUrl.setPages((int) dynamics.getPages());
        dyUrl.setSize((int) dynamics.getSize());
        return dyUrl;

//    @Override
//    public PageInfo<DynamicUrl> LatestNews(String userId, int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<DynamicUrl> list = baseMapper.LatestNews(userId);
//        PageInfo<DynamicUrl> appsPageInfo = new PageInfo<>(list);
//        return appsPageInfo;
    }

    @Override
    public PageInfo<Dynamic> finddraftById(String userId, String dynaContent, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Dynamic> list = baseMapper.selectdraftById(userId, dynaContent);
        PageInfo<Dynamic> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

    @Override
    public String saveDynamic(DynamicBean dynamicBean) {
//        JSONObject jsonObject = new JSONObject();
        final List<DynamicBean.DynaImgBean> dynaImgBeans = dynamicBean.getDynaImgs();

//        List list = new ArrayList();
//        for (DynamicBean.DynaImgBean dynaImgBean : dynaImgBeans) {
//            list.add(dynaImgBean.getImgState());
//        }
////        log.info("数组:"+list);
//        boolean yes = list.contains("1") || list.contains("");
////        log.info("是否存在:"+yes);
//        if(yes == true) {
//            log.info("进来了");
        Dynamic dynamic = new Dynamic();
        if (StringUtils.isNotEmpty(dynamicBean.getDynaId())) {
            dynamic.setDynaId(dynamicBean.getDynaId());
            dynamic.setDynaContent(dynamicBean.getDynaContent());
//            dynamic.setUserId(dynamicBean.getUserId());
            dynamic.setUpdateBy(dynamicBean.getUpdateBy());
            dynamic.setDynaStatus(dynamicBean.getDynaStatus());
            baseMapper.update(dynamic, new LambdaQueryWrapper<Dynamic>().eq(Dynamic::getDynaId, dynamicBean.getDynaId()));
        } else {
            dynamic.setDynaId(snowflakeIdGenerator.nextId() + StringPool.EMPTY);
            dynamic.setDynaStatus(dynamicBean.getDynaStatus());
            dynamic.setDynaCheckSta(0);
            dynamic.setDynaContent(dynamicBean.getDynaContent());
            dynamic.setUserId(dynamicBean.getUserId());
            dynamic.setDynaType(0);
            dynamic.setIsDelete(1);
            dynamic.setCreateBy(dynamicBean.getCreateBy());
            dynamic.setCreateTime(new Date());
            dynamic.setEvtRelTime(new Date());
            baseMapper.insert(dynamic);
        }

        if (CollectionUtils.isNotEmpty(dynaImgBeans)) {
            for (DynamicBean.DynaImgBean dynaImgBean : dynaImgBeans) {
                DynamicImg dynamicImg = new DynamicImg();
                if (StringUtils.equals(dynaImgBean.getImgState(), "2")) {
                    dynamicImg.setDynaImgId(dynaImgBean.getDynaImgId());
                    dynamicImg.setIsDelete(2);
                    dynamicImg.setUpdateBy(dynaImgBean.getUpdateBy());
                    dynamicImgMapper.update(dynamicImg, new LambdaQueryWrapper<DynamicImg>().eq(DynamicImg::getDynaImgId, dynaImgBean.getDynaImgId()));
                } else if (StringUtils.equals(dynaImgBean.getImgState(), "1")) {
                    continue;
                } else {
                    dynamicImg.setDynaImgId(snowflakeIdGenerator.nextId() + "");
                    dynamicImg.setDynaId(dynamic.getDynaId());
                    dynamicImg.setImgUrl(dynaImgBean.getImgUrl());
                    dynamicImg.setIsDelete(1);
                    dynamicImg.setImgSource("营销");
                    dynamicImg.setDynaImgType("KOL");
                    dynamicImg.setCreateBy(dynaImgBean.getCreateBy());
                    dynamicImg.setCreateTime(new Date());
                    dynamicImgMapper.insert(dynamicImg);
                }
            }
        }
        return dynamic.getDynaId();
//            jsonObject.put("code", 0);
//            jsonObject.put("message", "操作成功");
//            return jsonObject;
//        }else{
//            jsonObject.put("code", 500);
//            jsonObject.put("message", "操作失败");
//            return jsonObject;
//        }
    }

    /**
     * 删除动态
     */
    @Override
    @Transactional
    public int updateDynamic(String dynaId) {
        return this.baseMapper.updateDynamic(dynaId);
    }//@Param("dynaId")

    /**
     * 草稿箱发布
     */
    @Override
    @Transactional
    public int releaseDynamic(Dynamic dynamic) {
        dynamic.setCreateTime(new Date());
        return this.baseMapper.releaseDynamic(dynamic);
    }

    /**
     * 动态草稿箱编辑回显
     */
    @Override
    public DynamicBean getDynamicById(String dynaId) {
        Map<String, Object> colMap = new HashMap<>();
        colMap.put("dyna_id", dynaId);//写表中的列名
        colMap.put("is_delete", 1);
        final List<Dynamic> dynamics = baseMapper.selectByMap(colMap);
        if (CollectionUtils.isNotEmpty(dynamics)) {
            final DynamicBean dynamicBean = mapper.map(dynamics.get(0), DynamicBean.class);
            Map<String, Object> columnMap = new HashMap<>();
            columnMap.put("dyna_id", dynaId);//写表中的列名
            columnMap.put("is_delete", 1);
            final List<DynamicImg> dynamicImgs = dynamicImgMapper.selectByMap(columnMap);
            final List<DynamicBean.DynaImgBean> collect = dynamicImgs.stream().map(e -> {
                final DynamicBean.DynaImgBean dynaImgBean = mapper.map(e, DynamicBean.DynaImgBean.class);
                dynaImgBean.setImgState("1");
                return dynaImgBean;
            }).collect(Collectors.toList());
            dynamicBean.setDynaImgs(collect);
            return dynamicBean;
        }
        return null;
    }

    @Override
    public Dynamic finddEchoById(String dynaId) {
        return null;
    }
}