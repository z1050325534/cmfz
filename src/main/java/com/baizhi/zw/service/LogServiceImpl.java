package com.baizhi.zw.service;

import com.alibaba.excel.EasyExcel;
import com.baizhi.zw.dao.LogDao;
import com.baizhi.zw.entity.Log;
import com.baizhi.zw.entity.LogListener;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    LogDao logDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    //添加日志
    public void insertOne(Log log) {
        logDao.insert(log);
    }

    @Override
    //后台分页展示所有日志信息
    public Map queryByPage(Integer page, Integer rows) {
        int records = logDao.selectCount(null);
        //分页处理
        int offset = (page - 1) * rows;
        HashMap hashMap = new HashMap();
        hashMap.put("page",page);
        hashMap.put("total",records % rows == 0 ? records / rows : records / rows + 1);
        hashMap.put("records",records);
        hashMap.put("rows",logDao.selectByRowBounds(null, new RowBounds(offset, rows)));
        return hashMap;
    }

    //导出日志信息到excel
    @Override
    public void exportUser() {
        List<Log> logs = logDao.selectAll();
        String fileName = "E:\\idea3\\cmfz\\src\\main\\webapp\\upload\\ExcelLog\\" + new Date().getTime() + ".xls";
       EasyExcel.write(fileName,Log.class).sheet("日志信息").doWrite(logs);
    }

    //导入日志信息
    @Override
    public void addExportUser(MultipartFile log){
        String url = "E:\\idea3\\cmfz\\src\\main\\webapp\\upload\\ExcelLog\\"+log.getOriginalFilename();
        EasyExcel.read(url,Log.class,new LogListener()).sheet("日志信息").doRead();
    }
}
