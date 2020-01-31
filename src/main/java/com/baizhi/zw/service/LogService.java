package com.baizhi.zw.service;

import com.baizhi.zw.entity.Log;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface LogService {
    //添加日志
    void insertOne(Log log);
    //后台分页展示所有日志信息
    Map queryByPage(Integer page, Integer rows);
    //导出日志信息
    void exportUser();
    //导入日志信息
    void addExportUser(MultipartFile log) throws IOException;
}
