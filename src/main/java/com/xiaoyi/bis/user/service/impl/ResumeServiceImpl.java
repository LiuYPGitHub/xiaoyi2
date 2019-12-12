package com.xiaoyi.bis.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.bis.user.dao.ResumeMapper;
import com.xiaoyi.bis.user.domain.Resume;
import com.xiaoyi.bis.user.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description：履历
 * @Author：kk
 * @Date：2019/8/29 10:31
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {

}

