package com.baizhi.zw.service;

import com.baizhi.zw.entity.Banner;

import java.util.HashMap;
import java.util.List;

public interface BannerService {
    //分页查询
    HashMap queryByPage(Integer page, Integer rows);
    //查询条数
    int getCount();
    //添加
    void insertOne(Banner banner);
    //修改
    String updateOne(Banner banner);
    //假删除
    void deleteOne(Banner banner);
    //真删除
    void delete(String[] ids);
    Banner queryOne(String id);
    //前台根据时间展示5条
    List<Banner> queryByTime();
}
