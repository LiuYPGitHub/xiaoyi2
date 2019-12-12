package com.xiaoyi.bis.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.blog.controller.bean.DynamicBean;
import com.xiaoyi.bis.blog.controller.bean.DynamicBeanUrl;
import com.xiaoyi.bis.blog.domain.Dynamic;
import com.xiaoyi.bis.user.controller.common.ResPageInfo;

public interface DynamicService extends IService<Dynamic> {

    /**
     * 通过用id查找用户
     *
     * @return
     */
    PageInfo<Dynamic> getById(String userId, String dynaContent, int pageNum, int pageSize);

    /**
     * KOL详情-最新动态页
     *
     * @param userId
     * @return
     */
    ResPageInfo<DynamicBeanUrl> LatestNews(String userId, int pageNum, int pageSize);
//    PageInfo<DynamicUrl> LatestNews(String userId, int pageNum, int pageSize);

    /**
     * 通过用id查找用户 草稿箱
     *
     * @return
     */
    PageInfo<Dynamic> finddraftById(String userId, String dynaContent, int pageNum, int pageSize);


    /**
     * 新增动态
     *
     * @param dynamicBean
     */
    String saveDynamic(DynamicBean dynamicBean);

    /**
     * 删除动态
     */
    int updateDynamic(String dynaId);

    /**
     * 删除草稿动态
     *
     * @param dynamic
     */
    int releaseDynamic(Dynamic dynamic);

    /**
     * 动态草稿箱编辑回显
     */
    DynamicBean getDynamicById(String dynaId);

    /**
     * 动态草稿箱编辑回显
     */
    @Deprecated
    Dynamic finddEchoById(String dynaId);
}
