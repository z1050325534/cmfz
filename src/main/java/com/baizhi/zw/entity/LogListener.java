package com.baizhi.zw.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.zw.dao.LogDao;
import com.baizhi.zw.util.ApplicationContentUtils;

import java.util.ArrayList;
import java.util.List;

public class LogListener extends AnalysisEventListener<Log> {

    List<Log> list = new ArrayList<>();

    @Override
    public void invoke(Log log, AnalysisContext analysisContext) {
        ApplicationContentUtils applicationContentUtils = new ApplicationContentUtils();
        LogDao logDao = (LogDao) applicationContentUtils.getBean(LogDao.class);
        logDao.insert(log);
        list.add(log);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
