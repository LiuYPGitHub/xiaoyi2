package com.xiaoyi.bis.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyi.bis.blog.domain.Dynamic;
import com.xiaoyi.bis.blog.domain.DynamicImg;
import com.xiaoyi.bis.blog.domain.DynamicUrl;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 测试表 数据层
 */
@Mapper
public interface DynamicTeacherMapper extends BaseMapper<Dynamic> {

    /**
     * 查询测试表
     */
    List<Dynamic> selectById(String userId, String dynaContent);

    /**
     * 名师详情-最新动态页
     *
     * @param userId
     * @return
     */
    List<DynamicUrl> LatestNews(String userId);

    /**
     * 草稿箱list
     */
    List<Dynamic> selectdraftById(String userId, String dynaContent);

    /**
     * 删除动态
     */
    int updateDynamic(String dynaId);

    /**
     * 草稿箱发布
     */
    int releaseDynamic(String dynaId);

    /**
     * 草稿箱编辑回显
     */
    Dynamic getEchoById(String dynaId);
}