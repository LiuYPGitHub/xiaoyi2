package com.xiaoyi.bis.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xiaoyi.bis.blog.domain.Leads;
import com.xiaoyi.bis.blog.domain.LeadsLeIntenType;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface LeadsService extends IService<Leads> {

    /**
     * 通过用realName查找
     *
     * @return
     */
    PageInfo<Leads> getRealName(String realName, String leParName,String leParPhone, String leChiName,String leSchool,int pageNum, int pageSize);

    /**
     * 删除LEADS库
     *
     * @param leId
     */
    int updateLeads(String leId);

    /**
     * 回显
     *
     * @param leId
     * @return
     */
    Leads finddEchoById(String leId);

    /**
     * 添加
     *
     * @param le
     */
    void addLeads(Leads le);

    /**
     * 修改
     *
     * @param leads
     */
    void updateleads(Leads leads);

    // 导入
    void addLeadsExcel(Leads leads,String createBy);

    Leads view(String leId);

    void timingGradeAge();
}
