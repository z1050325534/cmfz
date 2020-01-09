package com.baizhi.zw.service;

import com.baizhi.zw.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    //后台展示所有专辑
    Map queryByPage(Integer page,Integer rows);
    //修改专辑信息
    String updateOne(Album album);
    //根据id删除专辑
    void delete(String id);
    //添加专辑信息
    void insertOne(Album album);
    //后台查一个
    Album queryOne(String id);
    //前台首页展示专辑
    List<Album> queryByRowBounds();
}
