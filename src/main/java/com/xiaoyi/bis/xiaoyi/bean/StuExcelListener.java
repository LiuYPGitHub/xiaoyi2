package com.xiaoyi.bis.xiaoyi.bean;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.xiaoyi.bis.xiaoyi.dao.ShareStudentMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StuExcelListener extends AnalysisEventListener<StudentBean> {

    private ShareStudentMapper shareStudentMapper;
    /**
     * 批处理阈值
     */
    private static final int BATCH_COUNT = 1;
    List<StudentBean> list = new ArrayList<>(BATCH_COUNT);

    @Override
    public void invoke(StudentBean studentBean, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(studentBean));
        list.add(studentBean);
        if (list.size() >= BATCH_COUNT) {
            list.add(studentBean);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        log.info("所有数据解析完成！");
    }


    public List<StudentBean> getDatas() {
        return list;
    }

    public void setDatas(List<StudentBean> datas) {
        this.list = datas;
    }

}