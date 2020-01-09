package com.baizhi.zw.service;

import com.baizhi.zw.entity.Guru;

import java.util.List;

public interface GuruService {
    //查所有
    List<Guru> queryAll();
    //添加上师
    void insertOne(Guru guru);
    //删除上师
    void delete(String[] id);
    //查一个
    Guru queryOne(String id);
    //修改
    String updateOne(Guru guru);
}
