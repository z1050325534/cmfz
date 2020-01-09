package com.baizhi.zw.service;

import com.baizhi.zw.entity.Chapter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ChapterService {
    //分页展示所有音频信息
    Map queryByPage(Integer page, Integer rows,String rowId);
    //修改音频信息
    String updateOne(Chapter chapter);
    //删除音频信息
    void delete(String[] id,String rowId) throws IOException;
    //添加音频信息
    void insertOne(Chapter chapter,String albumId);
    //根据父级id查询音频信息
    List<Chapter> queryByAlbumId(String albumId);
    //删除父级id  批量删除音频信息
    void deleteAll(List<Chapter> list);
    //查一个音频信息
    Chapter queryOne(String id);
}
